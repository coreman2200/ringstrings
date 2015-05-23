package com.coreman2200.ringstrings.file.io;

import android.util.Log;

import com.coreman2200.ringstrings.entities.HigherEntity;
import com.coreman2200.ringstrings.entities.Person;

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
