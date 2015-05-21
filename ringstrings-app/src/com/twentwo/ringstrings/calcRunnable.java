package com.twentwo.ringstrings;

import android.util.Log;

public class calcRunnable implements Runnable {
	protected Person myP;
	
	public calcRunnable(Person mp) {
		myP = mp;
	}
	
	public void run() {
		Log.d("calcRunnable", "Calculating charts for " + myP.getName() + "...");
		myP.setNumerology();
		myP.setBirthAstrology(true);
		RSIO.saveProfile(myP);
		myP.setOrientation();
		myP.display();
	}

}
