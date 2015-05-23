package rsgfx;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.coreman2200.ringstrings.entities.Entity;
import com.coreman2200.ringstrings.entities.HigherEntity;
import com.coreman2200.ringstrings.depruielems.InputObject;
import com.coreman2200.ringstrings.deprbizlogic.RingString;

//import android.content.Context;
import android.opengl.GLU;
import android.opengl.Matrix;

public class RSGFXEntity {
	protected static final int FLOAT_SIZE_BYTES = 4;
	protected static final int VERTICES_DATA_POS_OFFSET = 0;
	protected static final int VERTICES_DATA_TEX_OFFSET = 3;
	
	protected boolean visible;
	protected float[] rgba = new float[4];
	
	protected float[] pos = new float[3];
	protected float[] rot = new float[3];
	protected float[] scale  = { 1.0f, 1.0f, 1.0f };
	
	protected FloatBuffer _cvb;
	
	protected float[] mMVPMatrix	= new float[16];
	protected float[] mRotMatrix 	= new float[16];		// rotation
	protected float[] mMMatrix 	 	= new float[16]; 		// modelview
	protected float[] mScaleMatrix 	= new float[16];   // scaling
	
	protected int Width;
	protected int Height;
	
	protected int[] myprog;
	protected Entity myent;
	protected boolean clickable;
	protected boolean isHigherEntity;
	protected float timeelapsed = 0;
	
	
	public RSGFXEntity(Entity ment, float[] colors, boolean click, boolean visi) {
		visible = visi;
		clickable = click;
		myent = ment;
		if (myent != null)
			isHigherEntity = (myent.getType() >= HigherEntity.HE_Person);
		else
			isHigherEntity = false;
		if (colors != null)
			rgba = colors;
		else {
			rgba[0] = 1.0f; rgba[1] = 1.0f; rgba[2] = 1.0f; rgba[3] = 1.0f; 
		}
		
		_cvb = ByteBuffer.allocateDirect(4 * FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
	    _cvb.put(rgba);
	    _cvb.position(0);
		
		Matrix.setIdentityM(mMMatrix, 0);
		Matrix.setIdentityM(mScaleMatrix, 0);
		Matrix.setIdentityM(mRotMatrix, 0);
		
	}
	
	public void Update(float timeoffset) { 
		updateChildren(timeoffset);
	}
	
	public void onDrawFrame() { onDrawFrame(null); }
	
	public void onDrawFrame(float[] mParentMatrix) { 
		positionEntity(mParentMatrix);
		drawChildren(mMMatrix);	
	}
	
	protected int getRingCount() {
		if (isHigherEntity) {
			if (myent.getType() != HigherEntity.HE_String) 
				return ((HigherEntity)myent).getRingCount();
		}
		return 0;
	}
	
	protected void updateChildren(float timeoffset) {
		if (isHigherEntity) {
			if (myent.getType() != HigherEntity.HE_String) {			
				int crntRing = ((HigherEntity)myent).getCrntRing();
				RingString[] myRings = ((HigherEntity)myent).getRings();
				RSGFXRing gfxRing; 
				for (int i = 0; i < myRings.length; i++) {
					if (myRings[i] != null) {
						gfxRing = (RSGFXRing)myRings[i].getGFX();
					} else
						gfxRing = null;
					if (gfxRing != null)
						gfxRing.Update(timeoffset);
				}
			} else {
				Entity[] children = myent.getChildren();
				RSGFXLight gfxLight;
				for (int i = 0; i < myent.getChildCount(); i++) {
					gfxLight = (RSGFXLight)children[i].getGFX();
					if (gfxLight != null)
						gfxLight.Update(timeoffset);
				}
			}
		}
	}
	
	protected void drawChildren(float[] mParentMatrix) {
		if (isHigherEntity) {
			if (myent.getType() != HigherEntity.HE_String) {			
				RingString[] myRings = ((HigherEntity)myent).getRings();
				RSGFXRing gfxRing; 
				for (int i = 0; i < myRings.length; i++) {
					if (myRings[i] != null)
						gfxRing = (RSGFXRing)(myRings[i].getGFX());
					else
						gfxRing = null;
					if (gfxRing != null)
						gfxRing.onDrawFrame(mParentMatrix);
				}
			} else {
				Entity[] children = myent.getChildren();
				RSGFXLight gfxLight;
				for (int i = 0; i < myent.getChildCount(); i++) {
					gfxLight = (RSGFXLight)children[i].getGFX();
					if (gfxLight != null)
						gfxLight.onDrawFrame(mParentMatrix);
				}
			}
		}
	}
	
	public void positionEntity(float[] mParentMatrix) {
		Matrix.setIdentityM(mMMatrix, 0);
		Matrix.setIdentityM(mRotMatrix, 0);
		Matrix.setIdentityM(mScaleMatrix, 0);
		if (mParentMatrix != null)
			Matrix.multiplyMM(mMMatrix, 0, mParentMatrix, 0, mMMatrix, 0);
		Matrix.rotateM(mRotMatrix, 0, rot[0], 0.0f, 1.0f, 0.0f);
		//Matrix.rotateM(mRotMatrix, 0, rot[1], 0.0f, 1.0f, 1.0f);
		//Matrix.rotateM(mRotMatrix, 0, rot[2], 0.0f, 0.0f, 1.0f);
		Matrix.multiplyMM(mMMatrix, 0, mRotMatrix, 0, mMMatrix, 0);
		Matrix.translateM(mMMatrix, 0, pos[0], pos[1], pos[2]);
		Matrix.scaleM(mScaleMatrix, 0, scale[0], scale[1], scale[2]);
		Matrix.multiplyMM(mMMatrix, 0, mMMatrix, 0, mScaleMatrix, 0);
		
		Matrix.multiplyMM(mMVPMatrix, 0, RSGFXRender.getCamViewMatrix(), 0, mMMatrix, 0);
		Matrix.multiplyMM(mMVPMatrix, 0, RSGFXRender.getProjectionMatrix(), 0, mMVPMatrix, 0);
	}
	
	public float[] touchRay(InputObject input) {
		float[] xyzw1 = new float[4];
		float[] xyzw2 = new float[4];
		float[] touchray = new float[9];
		float[] mMVMatrix = new float[16];
		float[] campos = RSGFXRender.getCameraPos();
		float[] mProjection = RSGFXRender.getProjectionMatrix();
		Matrix.multiplyMM(mMVMatrix, 0, RSGFXRender.getCamViewMatrix(), 0, mMMatrix, 0);
		int unprojectedNear = GLU.gluUnProject(input.x, input.y, input.nearclip, mMVMatrix, 0, mProjection, 0, input.viewport, 0, xyzw1, 0);
		int unprojectedFar = GLU.gluUnProject(input.x, input.y, input.farclip, mMVMatrix, 0, mProjection, 0, input.viewport, 0, xyzw2, 0);
		if ((touchray[8] = (unprojectedNear * unprojectedFar)) == GL10.GL_TRUE) {
			touchray[0] = (xyzw1[0] / xyzw1[3]) - campos[0];
			touchray[1] = (xyzw1[1] / xyzw1[3]) - campos[1];
			touchray[2] = (xyzw1[2] / xyzw1[3]) - campos[2];
			touchray[3] = 1;
			touchray[4] = (xyzw2[0] / xyzw2[3]) - campos[0];
			touchray[5] = (xyzw2[1] / xyzw2[3]) - campos[1];
			touchray[6] = (xyzw2[2] / xyzw2[3]) - campos[2];
			touchray[7] = 1;
		}

		return touchray;
	}
	
	public void processInput(InputObject input) { }
	
	public void setRot(float p, float t, float r) {
		rot[0] = p;
		rot[1] = t;
		rot[2] = r;
	}
	
	public void setRot(float[] ptr) {
		rot = ptr;
	}
	
	public void setSize(int ww, int hh) {
		this.Width = ww;
		this.Height = hh;
	}
	
	public void setColor(float[] color) {
		rgba = color;
	}
	
	public void setColor(int rr, int bb, int gg, int aa) { 
		setColor(rr/255.0f, bb/255.0f, gg/255.0f, aa/255.0f);
	}
	
	public void setColor(float rr, float bb, float gg, float aa) { 
		rgba[0] = rr; rgba[1] = gg; rgba[2] = bb; rgba[3] = aa;
	}
	
	public int getWidth() {
		return this.Width;
	}
	
	public int getHeight() {
		return this.Height;
	}
	
	public float[] getPos() {
		return this.pos;
	}
	
	public void setPos(float x, float y, float z) {
		pos[0] = x; pos[1] = y; pos[2] = z;
	}
	
	public void setPos(float[] xyz) {
		pos = xyz;
	}
	
	public void setX(float x) {
		pos[0] = x;
	}
	
	public void setY(float y) {
		pos[1] = y;
	}
	
	public void setZ(float z) {
		pos[2] = z;
	}
	
	public void setClickable(boolean click) {
		this.clickable = click;
	}
	
	public boolean getClickable() {
		return this.clickable;
	}
	
	protected void applyProgram() {	}
}
