package rsgfx;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.util.FloatMath;
import android.util.Log;

import com.twentwo.ringstrings.Entity;
import com.twentwo.ringstrings.HigherEntity;
import com.twentwo.ringstrings.InputObject;
import com.twentwo.ringstrings.RSMath;
import com.twentwo.ringstrings.RingString;

public class RSGFXRing extends RSGFXEntity {
	private static final String STR_PROGNAME = "glowShader";
	public static final float RING_DIST = 6f;
	private static final int LINES_PER_RING = 45;
	private float[] ringverts;
	private int[] ringinds;
	private int vertcount;
	private FloatBuffer _rvb;
	private int ringnum;
	private HigherEntity Parent;
	private boolean isActive;
	private int pr_posHandle;
	private int pr_glowHandle;
	private int pr_colorHandle;
	private int pr_mvpMatrix;
	
	public RSGFXRing(RingString ment, float[] colors, boolean click, boolean visi) {
		super(ment,colors,false,visi);
		ringnum = ment.getRingNum();
		Parent = (HigherEntity)ment.getParent();
	    this.scale[0] = this.scale[2] = ringnum*RING_DIST;
		initVerts();
	}
	
	public void applyProgram() {
		if (myprog == null)
			myprog = new int[1];
		myprog[0] = RSGFXShaderManager.getShaderProgram(STR_PROGNAME).get_program();
		pr_posHandle = GLES20.glGetAttribLocation(myprog[0], "a_position");
		pr_colorHandle = GLES20.glGetAttribLocation(myprog[0], "a_color");	
		pr_mvpMatrix = GLES20.glGetUniformLocation(myprog[0], "u_mvpMatrix");
		pr_glowHandle =GLES20.glGetUniformLocation(myprog[0], "glowLvl"); 
		GLES20.glUseProgram(myprog[0]);
	}
	
	public void Update(float timeoffset) { 
		timeelapsed = (timeelapsed + (timeoffset/ringnum)) % 10000.0f;
		rot[0] = 360.0f * (timeelapsed/10000.0f);
		((RingString)myent).setActive((RSGFXRender.getActiveRing() == ringnum));
		updateChildren(timeoffset);
	}
	
	public void onDrawFrame(float[] mParentMatrix) { 
		if (this.visible) {
			positionEntity(mParentMatrix);
			applyProgram();
			
			_rvb.position(0);
			GLES20.glVertexAttribPointer(pr_posHandle, 3, GLES20.GL_FLOAT, false, 3 * FLOAT_SIZE_BYTES, _rvb);
			GLES20.glEnableVertexAttribArray(pr_posHandle);
			
			_cvb.position(0);
			GLES20.glVertexAttribPointer(pr_colorHandle, 4, GLES20.GL_FLOAT, false, 4 * FLOAT_SIZE_BYTES, _cvb);
			GLES20.glEnableVertexAttribArray(pr_colorHandle);
			
			GLES20.glUniformMatrix4fv(pr_mvpMatrix, 1, false, mMVPMatrix, 0);
			GLES20.glUniform1f(pr_glowHandle, ((isActive) ? 4 : 1));
			
			//GLES20.glDrawElements(GLES20.GL_LINE_LOOP, vertcount, GLES20.GL_UNSIGNED_INT, _rib);
			 GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, vertcount);
			//drawChildren(mMMatrix);	
		}
	}
	
	public void processInput(InputObject input) { 
		if (isActive) {
			Entity[] children = myent.getChildren();
			RSGFXEntity cGFX;
			for (int i = 0; i < myent.getChildCount(); i++) {
				cGFX = children[i].getGFX();
				cGFX.processInput(input);
			}
		}
	}
	
	private void initVerts() {
		vertcount = ringnum * LINES_PER_RING;
		_rvb = ByteBuffer.allocateDirect(vertcount * 3 * FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();

		//Log.d("RSGFXRing", "Ring #"+ringnum);
		float deg = (float)(2 * RSMath._PI) / (vertcount + 1);
		float[] vertice = new float[3];
		for (short i = 0; i < vertcount; i++) {
			//_rvb.put(vertice);
			vertice[0] = FloatMath.cos(deg*i);
			vertice[2] = FloatMath.sin(deg*i);
			_rvb.put(vertice);
			//Log.d("RSGFXRing", "Set Vertice " + i + " ~ x:"+ vertice[0] + " ~ y:"+vertice[1] + " ~ z:" + vertice[2]);
		}
		_rvb.position(0);
		
	}
	
	

}
