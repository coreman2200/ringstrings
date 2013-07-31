package rsgfx;

import java.util.HashMap;

//import android.R;
import android.content.Context;
import com.twentwo.ringstrings.R;

public class RSGFXShaderManager {
	private static Context mContext;
	
	private static HashMap<String, RSGFXShaderProgram> mShaderHashMap = null;
	
	public static void initShaderManager(Context c) {
		mContext = c;
		mShaderHashMap = new HashMap<String, RSGFXShaderProgram>();
	}
	
	public static void loadShaders() {
		
		mShaderHashMap.put("simpleShader", new RSGFXShaderProgram(												
				R.raw.renderglow_vert, 
				R.raw.renderglow_frag, 
				mContext, 
				false, 0));
		
		mShaderHashMap.put("lightShader", new RSGFXShaderProgram(												
												R.raw.renderlight_vert, 
												R.raw.renderlight_frag, 
												mContext, 
												true, 1));
		
		mShaderHashMap.put("glowShader", new RSGFXShaderProgram(												
												R.raw.renderglow_vert, 
												R.raw.renderglow_frag, 
												mContext, 
												false, 0));
		
		mShaderHashMap.put("gouraudShader", new RSGFXShaderProgram(												
												R.raw.gouraud_vert, 
												R.raw.gouraud_frag, 
												mContext, 
												true, 1));
	}
	
	public static RSGFXShaderProgram getShaderProgram(String name) {
		if (mShaderHashMap.containsKey(name)) {
			return mShaderHashMap.get(name);      
		} else {
			mShaderHashMap.put(name, 
				new RSGFXShaderProgram(name + ".vert", 
				name + ".frag", false, 0));
			return mShaderHashMap.get(name);
		}
	}

}
