package com.coreman2200.ringstrings;

import rsgfx.RSGFXEntity;
import rsgfx.RSGFXRing;

import android.util.Log;


public class RingString extends HigherEntity {
	private static final long serialVersionUID = Entity._PSNID+1;
	private int ringnum;
	private boolean isActive = false;
	
	public RingString(int rn) {
		super("Ring " + rn);
		ringnum = rn;
		this.entType = _PSNID;
		HEntType = HE_String;
		this.children = new Entity[16];
		
	}
	
	public RSGFXEntity getGFX() {
		if (_gfx == null)
			setGFX();
	
		return _gfx;
	}
	
	public void setGFX() {
		if (color == 0 || color == 0xFF000000) 
			color = 0xFFFFFFFF;
		_gfx = new RSGFXRing(this, null, true, true);
	}
	
	public boolean getActive() {
		return isActive;
	}
	
	public void setActive(boolean isa) {
		this.isActive = isa;
	}
	
	public int getRingNum() {
		return this.ringnum;
	}
	
	public void display() {
		  StringBuffer buf = new StringBuffer();
		  Log.v("RingString", sName);
		  //Log.v("Base Class Entity", sDesc);
		  buf.append("Tags: ");
		  
		  if (arrTags == null)
			  aggregateTags(true);
		  Tags[] mtags = getTags(9);
		  if (mtags != null) {
			  int Count = (mtags.length > _MAXTAGS) ? _MAXTAGS : mtags.length;
			  for (int i = 0; i < Count; i++) {
				  if (mtags[i] != null) {
					  buf.append(mtags[i].getName());
					  buf.append(" ");
				  } else 
					  break;  
			  }
			  Log.v("RingString", buf.toString());
			  Log.v("RingString", "Total Distinct Elements: " + this.childCount);
			  Entity[] children = getChildren();
			  
			  for (int i = 0; i < this.childCount; i++)
				  children[i].display();
				  //Log.v("RingString", sName + "  Element #" + (i+1) + ": " + children[i].getFullName());

		  }
	}
}