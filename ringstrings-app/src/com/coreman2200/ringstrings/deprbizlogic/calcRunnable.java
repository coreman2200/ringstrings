package com.coreman2200.ringstrings.deprbizlogic;

import android.util.Log;

import com.coreman2200.ringstrings.entities.Person;
import com.coreman2200.ringstrings.file.io.RSIO;

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
