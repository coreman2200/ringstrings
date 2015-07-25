package com.coreman2200.ringstrings.oldz.numerology;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.coreman2200.ringstrings.oldz.deprbizlogic.GlobalLL;
import com.coreman2200.ringstrings.oldz.entities.Entity;
import com.coreman2200.ringstrings.oldz.entities.Tags;
import com.coreman2200.ringstrings.oldz.io.XMLPullGlobals;
import com.coreman2200.ringstrings.oldz.deprbizlogic.RSMath;

public class Numerology extends Entity {
	private static final long serialVersionUID = Entity._NUMID;
	
	public static final int NUM_NUMBER  = 0x01;
	public static final int NUM_QUALITY = 0x02;
	
	private boolean isMasterNum;
	private boolean isKarmicDebt;
	private static int numCount;
	private int numType;
	private int numValue;
	private Entity baseNum1;
	private Entity baseNum2;
	//private String numfullname;
	

	public Numerology(String paramString, int nType, boolean skippull) {
		super(paramString);
		this.numType = nType;
		if (numType == NUM_NUMBER) {
			this.numValue = Integer.parseInt(paramString);
			this.isMasterNum = ((numValue % 11) == 0);
			baseNum1 = null;
			baseNum2 = null;
		} else {
			this.children = new Entity[8];
		}
		this.entType = _NUMID;
		this.nID = entType + numCount;
		Numerology.numCount++;
	}
	
	public Numerology(String paramString, int nType) {
		super(paramString);
		this.numType = nType;
		if (numType == NUM_NUMBER) {
			this.numValue = Integer.parseInt(paramString);
			this.isMasterNum = ((numValue % 11) == 0);
			baseNum1 = null;
			baseNum2 = null;
		} else {
			this.children = new Entity[8];
		}
		this.entType = _NUMID;
		this.nID = entType + numCount;
		Numerology.numCount++;
		try {
			XMLPullGlobals.initEnt(this);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			Log.e("Numerology", "Error Pulling from XML (XPP Exception): " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("Numerology", "Error Pulling from XML (IO Exception): " + e.getMessage());
		} 
	}
	
	//public static final int NUM_NUMBER  = 0x01;
	//public static final int NUM_QUALITY = 0x02;
	
	public void display() {
	  StringBuffer buf = new StringBuffer();
	  //Log.v("Numerology", sName);
	  if (numType == NUM_QUALITY)
		  addToChart(this.getFullName());
	  if (baseNum1 != null && baseNum2 != null) {
		  //Log.v("Numerology", "Base Numbers: " + baseNum1.getName() + " ~x~ " + baseNum2.getName() + ((this.isKarmicDebt) ? " (Karmic Debt Number: "+baseNum1.getName() + baseNum2.getName() + ")" : ""));
		  addToChart("Base Numbers: " + baseNum1.getName() + " ~x~ " + baseNum2.getName() + ((this.isKarmicDebt) ? " (Karmic Debt Number: "+getKarmicDebt()+ ")" : ""));
	  } else if (this.isKarmicDebt) {
		  addToChart("(Karmic Number: "+ getKarmicDebt() +")");
		  
	  }
	  addToChart(sDesc);
	  if (this.isKarmicDebt) {
		  addToChart("**" + (new Numerology(getKarmicDebt(), NUM_NUMBER).getDesc()) + "**");
		  addToChart("");
	  }
	 
	  if (numType == NUM_QUALITY) {
		  Entity[] children = getChildren();
		  for (int i = 0; i < childCount; i++) {
			  children[i].setChart(tofile);
			  addToChart("");
			  children[i].display();
			  //addToChart(children[i].getDesc());
		  }
	  	  
		  buf.append("Qualities: ");
		  //aggregateTags(numType == NUM_QUALITY);
		  Tags[] mtags = getTags();
		  if (mtags != null) {
			  int Count = (mtags.length > _MAXTAGS) ? _MAXTAGS : mtags.length;
			  for (int i = 0; i < Count; i++) {
				  if (mtags[i] != null) {
					  buf.append(mtags[i].getName());
					  buf.append(" ");
				  } else {
					  break;
				  }
			  }
		  }
		  addToChart(buf);
	  }
	  
	  
	  clearChart();
	  
	}
	
	public Tags[] aggregateTags(boolean reset) {
		  if (reset)
			  GlobalLL.resetTags();
		  
		  Tags[] arrTags = getTags();
		  
		  if (numType == NUM_NUMBER || reset) {
			  for (int i = 0; i < arrTags.length; i++) {
				  if (arrTags[i] != null)
					  arrTags[i].updCount();
			  }
		  }
		  
		  if (numType == NUM_NUMBER) {
			  if (this.baseNum1 != null && this.baseNum2 != null) {
				  this.baseNum1.aggregateTags(false);
				  this.baseNum2.aggregateTags(false);
			  }
		  } 
		  
		  if (numType == NUM_QUALITY) {
			  if (this.childCount > 0) {
				  Entity[] children = getChildren();
				  for (int i = 0; i < this.childCount; i++)
					  children[i].aggregateTags(false);
			  }
		  } 
		  
		  if (isKarmicDebt) {			  
			  Numerology kd = new Numerology(getKarmicDebt(),NUM_NUMBER);
			  kd.aggregateTags(false);
		  }
		  
		  if (reset) {
			  int tagsUsed = GlobalLL.getTagsUsed();
			  Tags[] aggTags = new Tags[tagsUsed];
			  int tc = 0;
			  for (int i = 0; i < Tags._ALLTAGS; i++) {
				  if (GlobalLL.TagsLL[i].getCount() > 0) {
					  aggTags[tc++] = GlobalLL.TagsLL[i];
				  }
			  }
			  
			  return (RSMath.sortTags(aggTags));
		  }
		  return arrTags;
	  }

	public void setBaseNumbers(Entity num1, Entity num2) {
		this.baseNum1 = num1;
		this.baseNum2 = num2;
	}
	
	public int getValue() {
		if (numType == NUM_NUMBER) {
			return this.numValue;
		} else {
			Entity[] children = this.getChildren();
			if (this.childCount == 1)
				return ((Numerology)children[0]).getValue();
		}	
		return 0;
	}
	
	public String getFullName() {
		if (numType == NUM_QUALITY) {
			StringBuffer buf = new StringBuffer();
			buf.append(sName + ":  ");
			
			for (int i = 0; i < this.childCount; i++) {
				if (this.children[i].getChildCount() >  0)
					buf.append(" " + this.children[i].getChildren()[0].getName() + " ");
				else
					buf.append(" " + this.children[i].getName() + " ");
			}
			return buf.toString();
		}
		else
			return sName;
	}
	
	public void setKarmicDebt(boolean isKD) {
		this.isKarmicDebt = isKD;
	}
	
	public boolean isKarmicDebt() {
		return this.isKarmicDebt;
	}
	
	public String getKarmicDebt() {
		if (isKarmicDebt) {
			  if (this.baseNum1 != null && this.baseNum2 != null)
				  return baseNum1.getName() + baseNum2.getName();
			  else
				  return "10";
		  } else {
			  return "0";
		  }
	}
	
	public boolean isMasterNumber() {
		return this.isMasterNum;
	}
	
	public Tags[] getAllTags() {

		  ArrayList<Tags> alltags = new ArrayList<Tags>();
		  for (int i = 0; i < getTagCount(); i++) {
			  alltags.add(arrTags[i]);  
		  }
		  
		  Tags[] ctags;
		  if (numType == NUM_NUMBER) {
			  if (baseNum1 != null) { 
				  ctags = this.baseNum1.getAllTags();
				  if (ctags != null) {
					  for (int o = 0; o < ctags.length; o++) {
						  if (ctags[o] != null)
							  alltags.add(ctags[o]);
					  }
				  }
				  ctags = this.baseNum2.getAllTags();
				  if (ctags != null) {
					  for (int o = 0; o < ctags.length; o++) {
						  if (ctags[o] != null)
							  alltags.add(ctags[o]);
					  }
				  }
			  }
			  
			  if (isKarmicDebt) {			  
				  String strkd = getKarmicDebt();
				  Numerology kd = new Numerology(strkd,NUM_NUMBER);
				  ctags = kd.getAllTags();
				  if (ctags != null) {
					  for (int o = 0; o < ctags.length; o++) {
						  if (ctags[o] != null)
							  alltags.add(ctags[o]);
					  }
				  }
			  }
		  }
			  
		  if (numType == NUM_QUALITY) {
			  for (int i = 0; i < childCount; i++) {
				  ctags = children[i].getAllTags();
				  if (ctags != null) {
					  for (int o = 0; o < ctags.length; o++) {
						  if (ctags[o] != null)
							  alltags.add(ctags[o]);	  
					  }
				  }
			  }
		  }
		  
		  int count = alltags.size();
		  Tags[] retval = (Tags[])alltags.toArray(new Tags[count]); 
		  return retval;
	}
	
	public void ReviveTags() {
		  if (this.arrTags != null) {
			for (int i = 0; i < this.arrTags.length; i++) 
				if (arrTags[i] != null)
					arrTags[i] = GlobalLL.getTagByID(arrTags[i].getID());
		  }
		  
		  for (int i = 0; i < childCount; i++) {
			  children[i].ReviveTags();
		  }
		  
		  if (baseNum1 != null)
			  baseNum1.ReviveTags();
		  
		  if (baseNum2 != null)
			  baseNum2.ReviveTags();
	  }

	
}
