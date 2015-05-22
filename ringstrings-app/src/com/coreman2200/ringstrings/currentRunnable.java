package com.coreman2200.ringstrings;

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
