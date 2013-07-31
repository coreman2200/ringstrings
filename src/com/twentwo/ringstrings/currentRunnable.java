package com.twentwo.ringstrings;

import rsgfx.RSGFXBase;
import android.util.Log;

public class currentRunnable implements Runnable {
	protected Person myP;
	
	public currentRunnable(Person mp) {
		myP = mp;
	}
	
	public void run() {
//		RSGFXBase.RSGFXToast("Calculating Your Current Chart Elements..");
		myP.setCrntStart(myP.getChildCount());
		if (myP.getCrntGeo() == null)
			myP.setCrntGeo(RSGoogleLoc.getCrntLocation());
		if (myP.getCrntGeo() != null)
			myP.setCrntAstrology(true);
		myP.setCrntNumerology();
		myP.cnrtChartIsSet(true);
		myP.setOrientation();
			
		//Log.d("currentRunnable", "Current Chart Complete for " + myP.getName());
	}

}
