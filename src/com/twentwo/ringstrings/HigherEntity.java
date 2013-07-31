package com.twentwo.ringstrings;

import java.util.ArrayList;

import rsgfx.RSGFXEntity;

import android.util.Log;

import com.twentwo.ringstrings.GlobalLL;
import com.twentwo.ringstrings.Entity;

public class HigherEntity extends Entity {
	private static final long serialVersionUID = _PSNID -1;
	public static final int HE_All = 0xFF0000;
	public static final int HE_String = 0xEE0000;
	public static final int HE_Person = 0xDD0000;
	
	public static final int FILT_NOFILTER     =		0xFF000F;
	public static final int FILT_TRANSITELEMS = 	Astrology.AST_TRANSIT;
	public static final int FILT_CURRENTELEMS = 	Entity._CRNTID;
	public static final int FILT_ASTROELEMS   = 	Entity._ASTID;
	public static final int FILT_NUMEROELEMS  = 	Entity._NUMID;
	
	
	transient public static HigherEntity ALLPEOPLE = new HigherEntity("Everyone", HE_All);
	transient protected static HigherEntity crntLevel;
	transient protected static Person MySelf;
	transient protected static int showFilter = FILT_NOFILTER;
	transient protected Entity[] filteredList;
	transient protected Entity currentAstrology;
	transient protected int filteredCount;
	transient protected boolean readyingfordisplay = false;
	transient protected boolean onlycurrent = false;
	protected boolean compareTo;	
	
	public static final int _MAXRINGS = 10;
	//private static final int _MAXPERSTRING = 10;
	
	protected float gRelativeMin = 0.0f;
	protected float gRelativeMax = 0.0f;
		  
	protected float gRelationAvg;
	protected float gSizeAvg;
	
	transient protected RingString RStrings[];
	transient protected int isOrientated = 0x00;
	transient protected int crntRing;
	protected int HEntType;
	
	
	public HigherEntity(String paramString) {
		super(paramString);
		HEntType = HE_Person;
	}
	
	public HigherEntity(String paramString, int htype) {
		super(paramString);
		HEntType = htype;
		if (HEntType == HE_All)
			children = new Entity[128];
	}
	
	public void display() {
		  Log.v("Higher Entity", sName);
		  
		  Entity[] children = getChildren();
		  Log.v("Higher Entity", "Total Children: " + this.childCount);
		  for (int i = 0; i < this.childCount; i++) {
			  children[i].display();
		  }	  
	}
	
	public int getID() {
		return this.HEntType + entCount;
	}
	
	public int getType() {
		return this.HEntType;
	}

	public Tags[] aggregateTags(boolean reset) {
		if (reset)
			GlobalLL.resetTags();
		
		Tags[] thistags = getAllTags();
		for (int i = 0; i < thistags.length; i++)
			thistags[i].updCount();
		
		/* Entity[] children = getChildren();
		
		int count = (reset) ? this.getCrntStart() : this.childCount;
		if (onlycurrent)
			count = this.childCount;
		for (int i = 0; i < count; i++)
			children[i].aggregateTags(false); */
		
		if (this == HigherEntity.MySelf) {
			  Entity crntMe = this.currentAstrology;
			  Tags[] alltags = crntMe.getAllTags();
			  for (int i = 0; i < alltags.length; i++) {
				  if (alltags[i].getCount() > 0)
					  alltags[i].updCount();
			  }
		}
	
		if (reset) {
			int tagsUsed = GlobalLL.getTagsUsed();
			Tags[] aggTags = new Tags[tagsUsed];
			int tc = 0;
			for (int i = 0; i < Tags._ALLTAGS; i++) {
				if (GlobalLL.TagsLL[i].getCount() > 0)
					aggTags[tc++] = GlobalLL.TagsLL[i];			
			}
			Tags[] arrTags = (RSMath.sortTags(aggTags));
			setTags(arrTags);
			return arrTags;
		}
		return getTags();
	}
	
	/*public int getMaxTags() {
		Tags[] tags = getTags();
		if (tags == null)
			tags = aggregateTags(true);
		return (tags.length >> 2);
	} */
	
	public Tags[] getTags() {
		if (arrTags == null)
			return aggregateTags(true);
		else
			return arrTags;
	}
	
	private void resetRelatives() {
		this.gRelationAvg = 0.0f;
		this.gRelativeMax = 0.0f;
		this.gRelativeMin = 9999.0f;
	}
	
	public static HigherEntity getCurrentEntity() {
		return crntLevel;
	}
	
	public static void setCurrentEntity(HigherEntity crnt) {
		crnt.setPrevEnt(crntLevel);
		crntLevel = crnt;
	}
	
	public static void setFilter(int filter) {
		showFilter = filter;
	}
	
	public static int getFilter() {
		return showFilter;
	}
	
	public int getRingCount() {
		if (this.RStrings != null)
			return RStrings.length;
		else 
			return 0;
	}
	
	public static void toggleFilters(int filter) { }
	
	public RSGFXEntity getGFX() {
		if ((isOrientated == 0) && !readyingfordisplay) {
			setOrientation();
		} else if (_gfx != null) {
			readyingfordisplay = false;
		} else {
			setGFX();
		}
		return _gfx;
	}
	
	public void setGFX() {
		if (color == 0 || color == 0xFF000000) 
			color = 0xFFFFFFFF;
		_gfx = new RSGFXEntity(this, RSMath.Hex2Float(color), (HEntType == HE_Person), true);
	}

	public void setOrientation() {
		if (!readyingfordisplay) {
			readyingfordisplay = true;
			dispStream.execute(new orientateRunnable(this)); 
		}
	}
	
	public boolean getIsOrientated() {
		return (isOrientated > 0);
	}
  
	public void setOrientated() {
		this.isOrientated = showFilter;
	}
  
	public int getOrientated() {
		return this.isOrientated;
	}  
	
	public Entity[] filterChildren() {
		Entity[] ents = this.getChildren();
		int elength = 0;
		int enttype;
		int rel;
		boolean  crntAloud = ((showFilter & FILT_CURRENTELEMS) == FILT_CURRENTELEMS);
		int totalcount = this.childCount;
		Entity crntAstro = null;
		if (getType() == HE_Person) {
			if (crntAloud && ((showFilter & FILT_ASTROELEMS) == FILT_ASTROELEMS)) {
				crntAstro = ((Person)this).getCurrentAstro();
				totalcount += crntAstro.getChildCount();
			}
		}
		Entity[] minlist = new Entity[totalcount];
		
		for (int i = 0; i < totalcount; i++) {
			//if (ents[i] != null)
			if (i < childCount) {
				enttype = ents[i].getType();
				rel = showFilter & enttype;
				if (rel == enttype) {				
					if (enttype == FILT_ASTROELEMS) {
						
						enttype = ((Astrology)ents[elength]).getAstType();
						rel = showFilter & enttype;
						if (rel == enttype) {
							if (!ents[i].getIsCurrent() || crntAloud)
								minlist[elength++] = ents[i];
						}
					} else {
						if (!ents[i].getIsCurrent() || crntAloud)
							minlist[elength++] = ents[i];
					}
				}
			} else {
				minlist[elength++] = crntAstro.getChild(i - childCount);
			}
			
		}
		filteredList = minlist;
		filteredCount = elength;
		return filteredList;
	}
	
	public void compareEnts() {
		GlobalLL.resetTags();
		resetRelatives();
		Tags[] htags;
		Entity[] minlist = filterChildren();
		
		if (this != MySelf)
			return;
		
		if (MySelf == null)
			MySelf = (Person)Person.ALLPEOPLE.getChild(0);
		
		HigherEntity compareto = (HEntType == HE_All) ? MySelf : this;
		
		htags = this.getAllTags();
		for (int i = 0; i < htags.length; i++)
			htags[i].updCount();
		
		Tags[] ttags;
		
		int ttlength;
		int tagsUsed = htags.length;
		float toAvgR = 0.0f;
		float ratio;

		for (int i = 0; i < filteredCount; i++) {
			if (compareto != minlist[i]) {
				  ratio = 0.0f;
				  ttags = minlist[i].getTags();
				  //if (ttags == null || HEntType != HE_All)
				  //	ttags = minlist[i].aggregateTags(true);
				  ttlength = ttags.length;
				  
				  for (int o = 0; o < ttlength; o++) {
					  if (ttags[o] == null)
						  break;
					  ratio += ttags[o].getCount();
				  }
				  
				  ratio = (ratio / ttlength);// * 100.0f;
				  
				  if (ratio > gRelativeMax)
					  gRelativeMax = ratio;
				  if (ratio < gRelativeMin)
					  gRelativeMin = ratio;
				  
				  toAvgR += ratio;
				  minlist[i].setRelation(ratio);
			}
		}
	
		gRelationAvg = toAvgR/filteredCount;
	
		GlobalLL.resetTags();
	}	

	public void setStrings()
	{
		Entity[] ents = filteredList;
	
		int entcount = filteredCount;
		float perstring = ((float)entcount/_MAXRINGS);
		
		if (perstring <= 2)
			perstring *= 2.0f;
		if (perstring > (int)perstring)
			perstring = (((int)perstring & 1) == 1) ? (int)perstring+1 : (int)perstring+2;
				
		int ringCount;
		float rcf =  entcount/perstring;
		ringCount = (int)rcf;
		
		if (rcf > ringCount) 
			ringCount += 1;

		RingString[] nRStrings = new RingString[ringCount];
		
		int i = 1;
		int count = 0;
		int pcount = (int)(i*perstring);
		Entity pent;
		while (pcount <= entcount) {
			pcount = (int)(i*perstring);
			nRStrings[i-1] = new RingString(i);
			nRStrings[i-1].setParent(this);
			if ((entcount - pcount) < perstring) {
				perstring = entcount - pcount;
				pcount = entcount+1;
			}
			
			//Log.d("HigherEntity", "Setting Ring #" + i + " for " + sName);
			for (int o = 0; o < perstring; o++) {
				pent = ents[count++];
				pent.setString(i);
				nRStrings[i-1].addChild(pent);
				//Log.d("HigherEntity", o+": " + pent.getFullName());
			}
			
			//this.RStrings[i-1].compareEnts();
			nRStrings[i-1].setAngles();
			i++;
		}
		RStrings = nRStrings;
	}	
	
	public void setAngles() {
		Entity[] ents = getChildren();
		int entlength = childCount();
		if (entlength == 0)
			return;
	
		int minDegree = 360/entlength;
	
		float nAngle = 0.0f;
		int count = 0;
		
		for (int i = 0; i < entlength; i++) {
			nAngle = count * minDegree;
			if ((i & 1) == 0) {
				nAngle = -nAngle;
				count++;
			}
			if (i == 0)
				nAngle = 0;
			ents[i].setAngle((int)nAngle + 180);
		}
	}

/*	public Tags[] getAllTags() {
		  ArrayList<Tags> alltags = new ArrayList<Tags>();
		  for (int i = 0; i < getTagCount(); i++) {
			  alltags.add(arrTags[i]);
				  
		  }
		  
		  int ccount = childCount;
		  
		  Tags[] ctags;
		  for (int i = 0; i < ccount; i++) {
			  //Log.e("HigherEntity", "Name: "+ children[i].getName());
			  ctags = children[i].getAllTags();
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
	}  */
	
	  public Tags[] getTags(int maxtags)
	  {	  
		
		Tags[] alltags = getAllTags();
		
		ArrayList<Tags> rettags = new ArrayList<Tags>();
		
		for (int i = 0; i < alltags.length; i++)
			alltags[i].updCount();
		
		int count = 0;
		
		for (int i = 0; i < Tags._ALLTAGS; i++) {
			if (GlobalLL.TagsLL[i].getCount() != 0) {
				if ((count++ < maxtags) || maxtags == -1)
					rettags.add(GlobalLL.TagsLL[i]);
			}
		}
		
		Tags[] retarr = rettags.toArray(new Tags[count]);
		
		return retarr;
	}
	
	public RingString[] getRings() {
		return this.RStrings;
	}
	  
	public int getCrntRing() {
		return this.crntRing;
	}
}
