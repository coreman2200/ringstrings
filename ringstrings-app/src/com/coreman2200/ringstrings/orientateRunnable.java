package com.coreman2200.ringstrings;

import android.util.Log;

public class orientateRunnable implements Runnable {
	protected HigherEntity myP;
	
	public orientateRunnable(HigherEntity mp) {
		myP = mp;
	}
	
	public void run() {
		Log.d("orientateRunnable", "Orienting " + myP.getName() + "...");
		//myP.compareEnts();
		myP.filterChildren();
		myP.setStrings();
		myP.setOrientated();
		if (!((Person)myP).getIsPersonSet())
			((Person)myP).PersonIsSet();
		Log.d("orientateRunnable", myP.getName() + " is ready for display.");
		
	}

}
