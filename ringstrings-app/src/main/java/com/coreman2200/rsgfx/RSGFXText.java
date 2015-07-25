package com.coreman2200.rsgfx;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.coreman2200.ringstrings.oldz.entities.Entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class RSGFXText extends RSGFXEntity {
	
	private static final String STR_PROGNAME = "fontTexShader";
	private static final int VERTICES_DATA_STRIDE_BYTES = 5 * FLOAT_SIZE_BYTES;

	private String text;
	private FloatBuffer _qvb;
	private FloatBuffer _textvb;

	// vertex information - clockwise
							// x, y, z, u, v
	final float _quadv[] = { -1.0f, -1.0f, 1.0f, 0, 0,
							 -1.0f,  1.0f, 1.0f, 0, 1,
							  1.0f,  1.0f, 1.0f, 1, 1,
							  1.0f, -1.0f, 1.0f, 1, 0
						   };


	// index
	final short _quadi[] = { 0, 1, 2,
			              2, 3, 0  
						};
	private ShortBuffer _qib;
	
	private int fontSize;
	private int renTextID;
	private boolean refresh;
	private boolean wrapText;
	
	public RSGFXText(Entity ment, String ntxt, int txtSize) {
		super(ment, null, false, true);
		myprog =  new int[1];
		myprog[0] = RSGFXShaderManager.getShaderProgram(STR_PROGNAME).get_program();
		fontSize = txtSize;
		wrapText = true;
		text = ntxt;
		refresh = false;
		RenderText();
		
		_qvb = ByteBuffer.allocateDirect(_quadv.length * FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
		_qvb.put(_quadv);
		_qvb.position(0);
		// index buffer
		_qib = ByteBuffer.allocateDirect(_quadi.length * 4).order(ByteOrder.nativeOrder()).asShortBuffer();
		_qib.put(_quadi);
		_qib.position(0);
	}
	
	public RSGFXText(Entity ment, String ntxt, int txtSize, boolean upd, boolean doeswrap, boolean click) {
		super(ment, null, click, true);
		myprog =  new int[1];
		myprog[0] = RSGFXShaderManager.getShaderProgram(STR_PROGNAME).get_program();
		fontSize = txtSize;
		wrapText = doeswrap;
		text = ntxt;
		refresh = upd;
		RenderText();
		
		_qvb = ByteBuffer.allocateDirect(_quadv.length
				* FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
		_qvb.put(_quadv);
		_qvb.position(0);
		// index buffer
		_qib = ByteBuffer.allocateDirect(_quadi.length
				* 4).order(ByteOrder.nativeOrder()).asShortBuffer();
		_qib.put(_quadi);
		_qib.position(0);
	}

	public void onDrawFrame() {
		int  mPosHandler = GLES20.glGetAttribLocation(myprog[0], "a_position");
		int  mTexCoordHandler = GLES20.glGetAttribLocation(myprog[0], "a_texCoord");
		// the vertex coordinates
		_qvb.position(VERTICES_DATA_POS_OFFSET);
		GLES20.glVertexAttribPointer(mPosHandler, 3, GLES20.GL_FLOAT, false,
				VERTICES_DATA_STRIDE_BYTES, _qvb);
		GLES20.glEnableVertexAttribArray(mPosHandler);

		// Texture info
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, renTextID);
		GLES20.glUniform1i(GLES20.glGetUniformLocation(myprog[0], "s_texture"), 0);

		// texture coordinates
		_textvb.position(VERTICES_DATA_TEX_OFFSET);
		GLES20.glVertexAttribPointer(mTexCoordHandler, 2, GLES20.GL_FLOAT, false,
				VERTICES_DATA_STRIDE_BYTES, _qvb);
		GLES20.glEnableVertexAttribArray(mTexCoordHandler);
		
		// Draw with indices
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, _quadv.length, GLES20.GL_UNSIGNED_SHORT, _qib);
    }
	
	public void RenderText() {
		//int tlength = text.length();
		//int fsize = fontSize * tlength;
		Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_4444);
		// get a canvas to paint over the bitmap
		Canvas canvas = new Canvas(bitmap);
		bitmap.eraseColor(0);

		// Draw the text
		Paint textPaint = new Paint();
		textPaint.setTextSize(32);
		textPaint.setAntiAlias(true);
		textPaint.setARGB(0xff, 0xff, 0xff, 0xff);
		// draw the text centered
		canvas.drawText(text, fontSize, 112, textPaint);

		// Generate GL texture ID
		int temp[] = new int[1];
		GLES20.glGenTextures(1, temp, 0);
		renTextID = temp[0];
		
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, renTextID);
	    
	    // Set texture parameters
	    GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
	    GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
	    GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
	    GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

	    //Matrix flip = new Matrix();
	    //flip.postScale(1.0f, -1.0f);
	    
		//Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

		//Clean up
		bitmap.recycle();
	}
	
	public void setUpdateEachFrame(boolean upd) {
		this.refresh = upd;
	}
	
	public String getText() {
		return this.text;
	}
	
}
