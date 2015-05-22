package rsgfx;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.util.Log;

import com.coreman2200.ringstrings.Entity;
import com.coreman2200.ringstrings.InputObject;

public class RSGFXLight extends RSGFXEntity {
	private static final String STR_PROGNAME = "lightShader";
	private static final float lightsize = 45.0f;
	
	private int pr_texHandle;
	private int pr_posHandle;
	private int pr_mvpMatrix;
	private int pr_fSizeHandle;
	int crntRing;
	
	public RSGFXLight(Entity ment, float[] colors, boolean click, boolean visi) {
		super(ment,colors,click,visi);
		crntRing = ment.getStringLvl();
	}
	
	public void onDrawFrame(float[] mParentMatrix) { 
		if (this.visible) {
			positionEntity(mParentMatrix);
			//applyProgram();
			//GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, vertcount);
			//drawChildren(mMMatrix);	
		}
	}
	
	public void processInput(InputObject input) { 
		if (clickable) {
			float[] touchray = touchRay(input);
			if (touchray[8] == GL10.GL_TRUE) {
				Log.d("RSGFXEntity", "Pos ~ x:" + (touchray[4] - touchray[0])/2  + " ~ y:" + (touchray[5] - touchray[1])/2 + " ~ z:" + (touchray[6] - touchray[2])/2);
			} else {
				Log.d("RSGFXEntity", "Touch Returns Nada");
			}
		}
	}
	
	public void applyProgram() {
		myprog[0] = RSGFXShaderManager.getShaderProgram(STR_PROGNAME).get_program();
		pr_texHandle = GLES20.glGetAttribLocation(myprog[0], "TextureCoord");
		pr_posHandle = GLES20.glGetAttribLocation(myprog[0], "Position");	
		pr_mvpMatrix = GLES20.glGetUniformLocation(myprog[0], "u_mvpMatrix");
		pr_fSizeHandle = GLES20.glGetAttribLocation(myprog[0], "pointSize");
		GLES20.glUseProgram(myprog[0]);
	}

}
