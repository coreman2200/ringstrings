package com.coreman2200.rsgfx;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.widget.Toast;

public class RSGFXBase extends GLSurfaceView {
	private static Context mContext;
	private RSGFXRender renderer;
	
	public RSGFXBase(Context context) {
        super(context);
        mContext = context;
        setEGLContextClientVersion(2);
        renderer = new RSGFXRender(mContext);
        setRenderer(renderer);
	}
	
	public static void RSGFXToast(String msg) {
		if (mContext != null)
			Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
	}
	
	public RSGFXRender getRenderer() {
		return this.renderer;
	}
}
