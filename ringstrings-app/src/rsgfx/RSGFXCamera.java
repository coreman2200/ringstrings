package rsgfx;

import android.opengl.Matrix;
import android.util.Log;

public class RSGFXCamera extends RSGFXEntity {
	// Position the eye behind the origin.
 
    // We are looking toward the distance
    private float lookX = 0.0f;
    private float lookY = 0.0f;
    private float lookZ = -1.0f;
 
    // Set our up vector. This is where our head would be pointing were we holding the camera.
    private final float upX = 0.0f;
    private final float upY = 1.0f;
    private final float upZ = 0.0f;
    
    private float near;
    private float timeround = 0;
    
    private float currentRing = 1;
    private float oldcamz;
	
	public RSGFXCamera(float _nn) {
		super(null, null, false, false);
		pos[0] = -0.5f;
		pos[1] = 4;
		pos[2] = 3;
		near = _nn;
	}
	
	public void Update(float timeoffset) { 
		if (timeoffset != oldcamz) {
			if ((int)timeoffset != ((int)currentRing)*RSGFXRing.RING_DIST )
				Log.d("RSGFXCamera", "Current Active Ring: " + (int)(-timeoffset / RSGFXRing.RING_DIST));
			currentRing = timeoffset/RSGFXRing.RING_DIST;
			oldcamz = timeoffset;
		}
		float diff = currentRing - (int)currentRing; 
		//Log.d("RSGFXCamera", "Current Active Ring: " +currentRing + " ~ diff: " + diff);
		float dsign = Math.signum(diff);
		float nextRing = Math.min(-1, Math.max(currentRing + dsign, RSGFXRender.getMaxDepth() / RSGFXRing.RING_DIST));
		//Log.d("RSGFXCamera", "Active Ring: " +currentRing + " ~ diff: " + diff + " ~ nextRing: " + nextRing);
		
		if (Math.abs(diff) < 0.45f) 
			currentRing = currentRing - (0.1f*diff);
		else
			currentRing = nextRing + (0.1f*diff);
			
		if (Math.abs(diff) > 0 && Math.abs(diff) <= 0.01f)
			currentRing = currentRing + diff;
		
		if (((int)currentRing)*RSGFXRing.RING_DIST != (int)timeoffset)
			RSGFXRender.setActiveRing(currentRing*RSGFXRing.RING_DIST);
		
		setZ(4+(currentRing*RSGFXRing.RING_DIST));
		setView();
		
	}
	
	public void setView() {
		Matrix.setLookAtM(mMMatrix, 0, pos[0], pos[1], pos[2], lookX, lookY, lookZ, upX, upY, upZ);
	}
	
	public float[] getCamViewMatrix() {
		return mMMatrix;
	}
	
	public void incZ(float z) {
		pos[2] -= z;
		if (pos[2] > 3)
			pos[2] = 3;
		if (pos[2] < RSGFXRender.getMaxDepth()+4)
			pos[2] = RSGFXRender.getMaxDepth()+4;
		lookZ = pos[2] - 5;
	}
	
	public void setZ(float z) {
		pos[2] = z;
		if (pos[2] > 3)
			pos[2] = 3;
		if (pos[2] < RSGFXRender.getMaxDepth()+4)
			pos[2] = RSGFXRender.getMaxDepth()+4;
		lookZ = pos[2] - RSGFXRing.RING_DIST;
	}
}
