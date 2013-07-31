package com.twentwo.ringstrings;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.twentwo.ringstrings.Entity;

public class Astrology extends Entity {
	private static final long serialVersionUID = _ASTID;
	
	public static final String _STRRETRO = "Retrograde";
	public static final int AST_CELESTBODY = 0x01;
	public static final int AST_SIGN = 0x02;
	public static final int AST_HOUSE = 0x04;
	public static final int AST_TRANSIT = 0x08;
	
	private static int astCount;
	private boolean isRetro = false;
	private int astType;
	private Astrology astHouse;
	private Astrology astSign;
	private Astrology astCelB1;
	private Astrology astCelB2;
	private float degInSign;
	private float degInHouse;
	private float degTransit;
	private int HouseNum;
	private int CelBodNum;
	private String astfullname;
	
	
	public Astrology(String paramString, int aType, boolean skippull) {
		super(paramString);
		this.astType = aType;
		this.astCelB1 = this.astCelB2 = null;
		this.astHouse = this.astSign = null;
		/*if (this.astType == AST_SIGN) 
			this.children = new Entity[16];
		if (this.astType == AST_HOUSE) 
			this.children = new Entity[16]; */
				
		this.degInHouse = this.degInSign = this.degTransit = 0.0f;		
		this.entType = _ASTID;
		this.nID = entType + astCount++;
	}
	
	public Astrology(String paramString, int aType) {
		super(paramString);
		this.astType = aType;
		this.astCelB1 = this.astCelB2 = null;
		this.astHouse = this.astSign = null;
		/*if (this.astType == AST_SIGN) 
			this.children = new Entity[16];
		if (this.astType == AST_HOUSE) 
			this.children = new Entity[16]; */
				
		this.degInHouse = this.degInSign = this.degTransit = 0.0f;		
		this.entType = _ASTID;
		this.nID = entType + astCount++;
		try {
			XMLPullGlobals.initEnt(this);
		} catch (XmlPullParserException e) {
			Log.e("Astrology", "Issue initing " + sName + ": " + e.getMessage());
		} catch (IOException e) {
			Log.e("Astrology", "Issue initing " + sName + ": " + e.getMessage());
		} 
	}
	
	public void display() {
	  StringBuffer buf = new StringBuffer();
	  
	  addToChart(getFullName());
	  
	  String Description = (astType == AST_TRANSIT) ? (astCelB1.getName() + " and " + astCelB2.getName() + " " + sDesc) : sDesc;
	  addToChart(Description);
	  if (astType == AST_CELESTBODY) {
		  if (isRetro) {		  
			  Astrology retro = new Astrology(_STRRETRO, AST_SIGN);
			  addToChart(sName +" is Retrograde:" + retro.getDesc());
		  }
		  addToChart("");
		  addToChart("In " + astHouse.getName() + ": " + astHouse.getDesc());
		  addToChart("Under Sign " + astSign.getName() + ": " + astSign.getDesc());
		  addToChart("");
	  }
	  
	  buf.append("Qualities: ");
	  
	  Tags[] mtags = aggregateTags(true);
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
	  clearChart();
	}
	
	public Tags[] aggregateTags(boolean reset) {
		  if (reset)
			  GlobalLL.resetTags();
		  
		  Tags[] arrTags = getAllTags();
		  if (arrTags != null) {
			  for (int i = 0; i < arrTags.length; i++) {
				  if (arrTags[i] != null)
					  arrTags[i].updCount();
			  }
		  }
		  
		  if (astType == AST_CELESTBODY) { 
			  this.astHouse.aggregateTags(false);
			  this.astSign.aggregateTags(false);
		  } 
		  
		  if (astType == AST_TRANSIT) {
			  this.astCelB1.aggregateTags(false);
			  this.astCelB2.aggregateTags(false);
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
	
	public String getFullName()
	{
		//if (this.astfullname == null) {
			if (astType == AST_TRANSIT)
				this.astfullname = ((this.isCurrent) ? "Current Astrology: " : "") + astCelB1.getName() + ((astCelB1.getRetro()) ? "(R) " : " ") + "(" + astCelB1.getSign().getName() + " in " + astCelB1.getHouse().getName() + ") " + sName + " " + astCelB2.getName() + ((astCelB2.getRetro()) ? "(R)" : "") + "(" + astCelB2.getSign().getName() + " in " + astCelB2.getHouse().getName() + ")";
			else if (astType == AST_CELESTBODY)
				this.astfullname = ((this.isCurrent) ? "Current Astrology: " : "") + sName + ((this.isRetro) ? "(r)":"") +" in " + astHouse.getName() +"(" + astSign.getName() + ")";
			else 
				this.astfullname = ((this.isCurrent) ? "Current Astrology: " : "") + sName;
		//}
	    return this.astfullname;
	}
	
	public void setSign(Astrology sign) {
		this.astSign = sign;
	}
	
	public void setHouse(Astrology house) {
		this.astHouse = house;
	}
	
	public void setDegreeInHouse(float dInHouse) {
		this.degInHouse = dInHouse;
	}
	
	public void setDegreeInSign(float dInSign) {
		this.degInSign = dInSign;
	}
	
	public void setCelestBody(Astrology mHouse, Astrology mSign) {
		this.astHouse = mHouse;
		this.astSign = mSign;
	
		this.astfullname = sName + ((this.isRetro) ? "(r)":"") +" in " + astHouse.getName() +"(" + astSign.getName() + ")";
	}
	
	public void setTransit(Astrology CB1, Astrology CB2, float dTrans) {
		this.astCelB1 = CB1;
		this.astCelB2 = CB2;
		this.degTransit = dTrans;
		this.astfullname = astCelB1.getName() + " " + sName + " " + astCelB2.getName();
	}
	
	public void setRetro(boolean isretro) {
		if (astType == AST_CELESTBODY)
			this.isRetro = isretro;
	}
	
	public void setHouseNum(int hs) {
		this.HouseNum = hs;
	}
	
	public int getCelNumber() {
		return this.CelBodNum;
	}
	
	public void setCelNumber(int num) {
		this.CelBodNum = num;
	}
	
	public Astrology getHouse() {
		return this.astHouse;
	}
	
	public Astrology getSign() {
		return this.astSign;
	}
	
	public boolean getRetro() {
		return this.isRetro;
	}
	
	public int getAstType() {
		return this.astType;
	}
	
	public float getDegreeInHouse() {
		return this.degInHouse;
	}
	
	public float getDegreeInSign() {
		return this.degInSign;
	}

	public float getDegreeOfTransit() {
		return this.degTransit;
	}
	
	public int getHouseNum() {
		return HouseNum;
	}
	
	
	public Tags[] getAllTags() {

		  ArrayList<Tags> alltags = new ArrayList<Tags>();
		  for (int i = 0; i < getTagCount(); i++) {
			  alltags.add(arrTags[i]);
				  
		  }
		  
		  Tags[] ctags;
		  if (astType == AST_CELESTBODY) {
			  ctags = this.astHouse.getAllTags();
			  if (ctags != null) {
				  for (int o = 0; o < ctags.length; o++) {
					  if (ctags[o] != null)
						  alltags.add(ctags[o]);
				  }
			  }
			  ctags = this.astSign.getAllTags();
			  if (ctags != null) {
				  for (int o = 0; o < ctags.length; o++) {
					  if (ctags[o] != null)
						  alltags.add(ctags[o]);
				  }
			  }
			  
			  if (isRetro) {			  
				  Astrology retro = new Astrology(_STRRETRO, AST_SIGN);
				  ctags = retro.getAllTags();
				  if (ctags != null) {
					  for (int o = 0; o < ctags.length; o++) {
						  if (ctags[o] != null)
							  alltags.add(ctags[o]);
					  }
				  }
			  }
		  }
			  
		  if (astType == AST_TRANSIT) {
			  ctags = this.astCelB1.getAllTags();
			  if (ctags != null) {
				  for (int o = 0; o < ctags.length; o++) {
					  if (ctags[o] != null)
						  alltags.add(ctags[o]);
				  }
			  }
			  ctags = this.astCelB2.getAllTags();
			  if (ctags != null) {
				  for (int o = 0; o < ctags.length; o++) {
					  if (ctags[o] != null)
						  alltags.add(ctags[o]);
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
		  
		  if (astHouse != null)
			  astHouse.ReviveTags();
		  
		  if (astSign != null)
			  astSign.ReviveTags();
		  
		  if (astCelB1 != null)
			  astCelB1.ReviveTags();
		  
		  if (astCelB2 != null)
			  astCelB2.ReviveTags();
	  }
	
	

}
