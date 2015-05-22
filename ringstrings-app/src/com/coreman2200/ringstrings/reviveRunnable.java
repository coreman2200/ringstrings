package com.coreman2200.ringstrings;

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
