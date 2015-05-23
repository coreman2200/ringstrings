package com.coreman2200.ringstrings.file.io;

import com.coreman2200.ringstrings.entities.Person;

public class reviveRunnable implements Runnable {
	protected Person myP;
	
	public reviveRunnable(Person mp) {
		myP = mp;
	}
	
	public void run() {
		myP.ReviveTags();
		boolean myself = myP.getIsMyself();
		myP.setOrientation();
		if (myself)
			myP.calcCurrent();
	}

}
