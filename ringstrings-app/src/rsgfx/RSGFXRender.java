package rsgfx;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.ArrayBlockingQueue;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.coreman2200.ringstrings.HigherEntity;
import com.coreman2200.ringstrings.InputObject;

import android.content.Context;
import android.opengl.Matrix;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.util.Log;

public class RSGFXRender implements GLSurfaceView.Renderer {
	
	private static final int INPUT_QUEUE_SIZE = 20;
	private static final int FLOAT_SIZE_BYTES = 4;
	private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 8 * FLOAT_SIZE_BYTES;
	private static final int TRIANGLE_VERTICES_DATA_POS_OFFSET = 0;
	private static final int TRIANGLE_VERTICES_DATA_TEX_OFFSET = 6;
	private ArrayBlockingQueue<InputObject> inputQueue = new ArrayBlockingQueue<InputObject>(INPUT_QUEUE_SIZE);
	private Object inputQueueMutex = new Object();
	
	// rotation 
	public float mAngleX;
	public float mAngleY;
	
	private Context mContext;
	private static RSGFXEntity crntOnScreen;
	private static RSGFXCamera maincam;
	// Projection matrix
	private static float[] mProjMatrix = new float[16];
	
	private float[] mMVPMatrix = new float[16];
	private float[] mScaleMatrix = new float[16];   // scaling
	private float[] mRotXMatrix = new float[16];	// rotation x
	private float[] mRotYMatrix = new float[16];	// rotation x
	private float[] mMMatrix = new float[16];		// rotation
	private float[] mVMatrix = new float[16]; 		// modelview
	
	// viewport variables
	float ratio = 1.0f;
	final float near = 1.5f;
	final float far = 45.0f;
	
	private static int w, h;
	int texW = 480 * 2;
	int texH = 800 * 2;
	
	// angle rotation for light
	float angle = 0.0f;
	boolean lightRotate = false; 

	// eye pos
	//private float[] eyePos = {-5.0f, 0.0f, 0.0f};

	// scaling
	float scaleX = 1.0f;
	float scaleY = 1.0f;
	float scaleZ = 1.0f;
	
	// the full-screen quad buffers
	
	final float x = 10.0f;
	final float y = 15.0f;
	final float z = 2.0f;
	// vertex information - clockwise
							// x, y, z, nx, ny, nz, u, v
	final float _quadv[] = { -x, -y, z, 0, 0, -1, 0, 0,
							 -x,  y, z, 0, 0, -1, 0, 1,
							  x,  y, z, 0, 0, -1, 1, 1,
							  x, -y, z, 0, 0, -1, 1, 0
						   };

	private FloatBuffer _qvb;
	// index
	final int _quadi[] = { 0, 1, 2,
			              2, 3, 0  
						};
	
	private IntBuffer _qib;
	
	// RENDER TO TEXTURE VARIABLES
	int[] fb, depthRb, renderTex;
	IntBuffer texBuffer;
	
	// GAME LOOP variables
	final int TICKS_PER_SECOND = 25; 
    final int SKIP_TICKS = 1000 / TICKS_PER_SECOND; // Translates to 60fps
    final int MAX_FRAMESKIP = 10;

    float next_game_tick;// = System.currentTimeMillis();//GetTickCount();
    int loops;
    
    private static float camZtoRing = -1;

	
	public RSGFXRender(Context context) {
		mContext = context;
		RSGFXShaderManager.initShaderManager(mContext);
		maincam = new RSGFXCamera(near);
		if (HigherEntity.getCurrentEntity() != null)
			crntOnScreen = HigherEntity.getCurrentEntity().getGFX();
		else 
			crntOnScreen = null;
	}

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.1f, 0.1f, 0.25f, 1.0f);
        RSGFXShaderManager.loadShaders();
        next_game_tick = SystemClock.elapsedRealtime();
        // No culling of back faces
        GLES20.glDisable(GLES20.GL_CULL_FACE);
 
        // No depth testing
        //GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        setupRender();
        // Enable blending
        // GLES20.glEnable(GLES20.GL_BLEND);
        // GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE);
        
        // Setup quad 
 		// Generate your vertex, normal and index buffers
 		// vertex buffer
 		_qvb = ByteBuffer.allocateDirect(_quadv.length
 				* FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
 		_qvb.put(_quadv);
 		_qvb.position(0);

 		// index buffer
 		_qib = ByteBuffer.allocateDirect(_quadi.length
 				* 4).order(ByteOrder.nativeOrder()).asIntBuffer();
 		_qib.put(_quadi);
 		_qib.position(0);
    }
    
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
		w = width;
		h = height;
		ratio = (float) width / height;
		Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, near, far);
    }

    public void onDrawFrame(GL10 unused) {
    	if (HigherEntity.getCurrentEntity() != null) {
			crntOnScreen = HigherEntity.getCurrentEntity().getGFX();
    	}
		else  {
			crntOnScreen = null;
			next_game_tick = SystemClock.elapsedRealtime();
		}
        
        
        if (crntOnScreen != null) {
	 		loops = 0;
	 		long elTime = SystemClock.elapsedRealtime();
	 		float timeoffset; 
	        while( elTime > next_game_tick && loops < MAX_FRAMESKIP) {	
	        	timeoffset = elTime - next_game_tick;
	        	maincam.Update(camZtoRing);
	        	crntOnScreen.Update(timeoffset);
	        	processInput();
	            next_game_tick += SKIP_TICKS;
	            loops++;
	        }
	 		
	        Render();  
        }
     		
    }
    
    public void Render() { 
    	GLES20.glViewport(0, 0, this.texW, this.texH);
    	GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fb[0]);
		
		// specify texture as color attachment
		GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, renderTex[0], 0);
		//GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT, GLES20.GL_TEXTURE_2D, renderTex[0], 0);
		
		// attach render buffer as depth buffer
		GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT, GLES20.GL_RENDERBUFFER, depthRb[0]);
		//GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_RENDERBUFFER, depthRb[0]);
		
		// check status
		int status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER);
		if (status != GLES20.GL_FRAMEBUFFER_COMPLETE)
			return;

		// Clear the texture (buffer) and then render as usual...
		GLES20.glClearColor(0.07f, 0.07f, 0.15f, 1.0f);
		GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
		
		crntOnScreen.onDrawFrame();
		
		/********* NOW JUST TRY TO RENDER THE TEXTURE ************/

		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
		
		// Same thing, only different texture is bound now
		
		GLES20.glClearColor(0.07f, 0.07f, 0.15f, 1.0f);
		GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
		
		// RENDER A FULL-SCREEN QUAD
		
		// use gouraud shader to render it?
		int _program = RSGFXShaderManager.getShaderProgram("gouraudShader").get_program();
		
		// Start using the shader
		GLES20.glUseProgram(_program);
		checkGlError("glUseProgram");

		// set the viewport
		GLES20.glViewport(0, 0, w, h);
		//Matrix.orthoM(mProjMatrix, 0, -ratio, ratio, -1, 1, 0.5f, 10);
		
		// scaling
		Matrix.setIdentityM(mScaleMatrix, 0);
		//Matrix.scaleM(mScaleMatrix, 0, scaleX, scaleY, scaleZ);

		// Rotation along x
		Matrix.setRotateM(mRotXMatrix, 0, 0, -1.0f, 0.0f, 0.0f);
		Matrix.setRotateM(mRotYMatrix, 0, 0, 0.0f, 1.0f, 0.0f);

		// Set the ModelViewProjectionMatrix
		float[] tempMatrix = new float[16]; 
		Matrix.multiplyMM(tempMatrix, 0, mRotYMatrix, 0, mRotXMatrix, 0);
		Matrix.multiplyMM(mMMatrix, 0, mScaleMatrix, 0, tempMatrix, 0);
		Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mMMatrix, 0);
		Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
		
		
		// send to the shader
		GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(_program, "uMVPMatrix"), 1, false, mMVPMatrix, 0);

		// the vertex coordinates
		_qvb.position(TRIANGLE_VERTICES_DATA_POS_OFFSET);
		GLES20.glVertexAttribPointer(GLES20.glGetAttribLocation(_program, "a_Position"), 3, GLES20.GL_FLOAT, false,
				TRIANGLE_VERTICES_DATA_STRIDE_BYTES, _qvb);
		GLES20.glEnableVertexAttribArray(GLES20.glGetAttribLocation(_program, "a_Position"));

		// bind the framebuffer texture
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, renderTex[0]);
		GLES20.glUniform1i(GLES20.glGetUniformLocation(_program, "texture1"), 0);

		// texture coordinates
		_qvb.position(TRIANGLE_VERTICES_DATA_TEX_OFFSET);
		GLES20.glVertexAttribPointer(GLES20.glGetAttribLocation(_program, "textureCoord"), 2, GLES20.GL_FLOAT, false,
				TRIANGLE_VERTICES_DATA_STRIDE_BYTES, _qvb);
		GLES20.glEnableVertexAttribArray(GLES20.glGetAttribLocation(_program, "textureCoord"));//GLES20.glEnableVertexAttribArray(shader.maTextureHandle);

		// Draw with indices
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, _quadi.length, GLES20.GL_UNSIGNED_INT, _qib); // NOTE: On some devices GL_UNSIGNED_SHORT works 
		checkGlError("glDrawElements");
		
		
    }
    
    private void setupRender() {
		fb = new int[1];
		depthRb = new int[1];
		renderTex = new int[1];
		
		// generate
		GLES20.glGenFramebuffers(1, fb, 0);
		GLES20.glGenRenderbuffers(1, depthRb, 0);
		GLES20.glGenTextures(1, renderTex, 0);
		
		// generate color texture
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, renderTex[0]);

		// parameters
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
				GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
				GLES20.GL_LINEAR);

		// create it 
		// create an empty intbuffer first?
		int[] buf = new int[texW * texH];
		texBuffer = ByteBuffer.allocateDirect(buf.length
				* FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asIntBuffer();;
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, texW, texH, 0, GLES20.GL_RGB, GLES20.GL_UNSIGNED_SHORT_5_6_5, texBuffer);
		
		// create render buffer and bind 16-bit depth buffer
		GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, depthRb[0]);
		GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT16, texW, texH);		

	}
    
    public static float[] getProjectionMatrix() {
    	return mProjMatrix;
    }
    
    public static float[] getCamViewMatrix() {
    	return maincam.getCamViewMatrix();
    }
    
    public static float[] getCameraPos() {
    	return maincam.getPos();
    }
    
    public static int getScreenWidth() {
    	return w;
    }
    
    public static int getScreenHeight() {
    	return h;
    }
    
    public void feedInput(InputObject input) {
    	synchronized(inputQueueMutex) {
    		try {
    			inputQueue.put(input);
    		} catch (InterruptedException e) {
    			Log.e("RSGFXRender", e.getMessage(), e);
    		}
    	}
    }
    
    private void processKeyEvent(InputObject input) { }
    
    public static float getMaxDepth() {
     	if (crntOnScreen != null)
    		return -crntOnScreen.getRingCount() * RSGFXRing.RING_DIST;
    	else
    		return 0;
    }
    
    public static int[] getViewport() {
    	int[] vp = { 0, 0, w, h };
    	return vp;    	
    }
    
    public static int getActiveRing() {
    	return (int)(-camZtoRing/RSGFXRing.RING_DIST);
    }
    
    public static void setActiveRing(float camz) {
    	Log.d("RSGFXRender", "Current Active Ring: " + (int)(-camz / RSGFXRing.RING_DIST));
    	camZtoRing = camz;
    }

    private void processInput() {
    	synchronized(inputQueueMutex) {
	    	ArrayBlockingQueue<InputObject> inputQueue = this.inputQueue;
	    	while (!inputQueue.isEmpty()) {
		    	try {
			    	InputObject input = inputQueue.take();
			    	if (input.eventType == InputObject.EVENT_TYPE_KEY) {
			    		processKeyEvent(input);
			    	} else if (input.eventType == InputObject.EVENT_TYPE_TOUCH) {
			    		
			    		if (input.action == InputObject.ACTION_TOUCH_UP) {
			    			input.y = (h - input.y);
				    		input.nearclip = near;
				    		input.farclip = far;
				    		input.viewport = getViewport();
				    		//Log.d("RSGFXRender", "Motion Event Detected ~ x:" + input.x + " ~ y:" + input.y);
				    		//HigherEntity.getCurrentEntity().getGraphics().processInput(input);
				    		if (crntOnScreen != null) 
				    			crntOnScreen.processInput(input);
			    		} else if (input.action == InputObject.ACTION_TOUCH_MOVE) {
			    			boolean isYDir = (Math.abs(input.disty) - Math.abs(input.distx)) > 0;
			    			if (isYDir && !input.isSwipe) {
			    				float MaxDepth = getMaxDepth();
			    				float inny = (input.disty/h) * (MaxDepth/15); 
			    				camZtoRing += inny;
			    				if (camZtoRing > -1)
			    					camZtoRing = -1;
			    				if (camZtoRing < MaxDepth)
			    					camZtoRing = MaxDepth;
			    					
			    			}
			    		}
			    		//processMotionEvent(input);
			    	}
			    	input.returnToPool();
		    	} catch (InterruptedException e) {
		    		Log.e("RSGFXRender", e.getMessage(), e);
		    	}
	    	}
    	}
    }
    
 // debugging opengl
 	private void checkGlError(String op) {
 		int error;
 		while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
 			Log.e("RSGFXRender", op + ": glError " + error);
 			throw new RuntimeException(op + ": glError " + error);
 		}
 	}
    
}