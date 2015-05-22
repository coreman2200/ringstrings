package com.coreman2200.ringstrings;

import android.util.Log;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rsgfx.RSGFXEntity;
import rsgfx.RSGFXLight;

//import com.coreman2200.NDKGraphics.NDKEntity;
//import com.coreman2200.NDKGraphics.NDKLights;


public class Entity implements Serializable
{
	public static final int _ENTID = 0x000000;
	public static final int _NUMID = 0x220000;
	public static final int _ASTID = 0x440000;
	public static final int _PSNID = 0x660000;
	public static final int _CRNTID = 0x880000;
	public static final int _TAGID = 0xFF0000;
	
	private static final long serialVersionUID = _ENTID;
	
	protected static final int _MAXTAGS = 16;
	
	protected static ExecutorService calcStream = Executors.newSingleThreadExecutor();
	protected static ExecutorService dispStream = Executors.newSingleThreadExecutor();
	protected static int entCount;
	private static Entity topEnt;
	private Entity parent;
	protected Entity children[];
	protected Tags arrTags[];
	
	protected int tagCount;
	private int strLevel;
	private Entity prevEnt;
	//private int resIcon;
	// private NDKLights myGraphics;
	protected String sDesc;
	protected String sName;
	protected int color;
	protected boolean Visible;
	protected int childCount;
	protected int numerCount;
	protected int astroCount;
	transient protected int Angle;
	protected int nID;
	protected int entType;
	protected float relation;
	transient protected int crntStart;
	protected boolean Clickable = true;
	protected boolean isNew = true;
	transient protected boolean isCurrent = false;
	transient protected boolean isDisplayReady = false;
	transient protected RSGFXEntity _gfx;
	transient protected StringBuffer tofile;
  
	public Entity(String paramString, int childcount)
	  {
	    //this.arrTags = new Tags[32];
	    this.sName = paramString;
	    this.relation = 0;
	    this.color = 0;
	    this.strLevel = 0;
	    this.childCount = 0;
	    this.tagCount = 0;
	    this.entType = _ENTID;
	    this.nID = (_ENTID + entCount);
	    Entity.entCount++;
	    children = new Entity[childcount];
	  }

  public Entity(String paramString)
  {
    //this.arrTags = new Tags[32];
    this.sName = paramString;
    this.relation = 0;
    this.color = 0;
    this.strLevel = 0;
    this.childCount = 0;
    this.tagCount = 0;
    this.entType = _ENTID;
    this.nID = (_ENTID + entCount);
    Entity.entCount++;
  }
  
  public void display() {
	  StringBuffer buf = new StringBuffer();
	  Log.v("Base Class Entity", sName);
	  Log.v("Base Class Entity", sDesc);
	  buf.append("Tags: ");
	  int Count = (arrTags.length > _MAXTAGS) ? _MAXTAGS : arrTags.length;
	  for (int i = 0; i < Count; i++) {
		  if (arrTags[i] != null) {
			  buf.append(arrTags[i].getName());
			  buf.append(" ");
		  } else {
			  break;
		  }
	  }
	  Log.v("Base Class Entity", buf.toString());
  }

  public void addChild(Entity paramEntity)
  {
	  if (paramEntity.getType() == _TAGID) 
		  return;
	  
	  paramEntity.setParent(this);
	  if (childCount < 128) {
		  this.children[childCount++] = paramEntity;
		  int type = paramEntity.getType();
		  if (type == _ASTID) {
			  if (((Astrology)paramEntity).getAstType() == Astrology.AST_CELESTBODY)
				  ((Astrology)paramEntity).setCelNumber(astroCount);
			  astroCount++;
		  }
		  if (type == _NUMID)
			  numerCount++;
	  }
	  //Log.v("Entity", children[childCount-1].getName() + " added to " + sName);
  }
  
  public void setParent(Entity paramEntity)
  {
    this.parent = paramEntity;
  }
  
  public int getMaxTags() {
	  return _MAXTAGS;
  }
  
  public Tags[] aggregateTags(boolean reset) {
	  if (reset)
		  GlobalLL.resetTags();
	  
	  for (int i = 0; i < arrTags.length; i++) {
		  if (arrTags[i] != null)
			  arrTags[i].updCount();
	  }
	  
	  if (this.childCount > 0) {
		  for (int i = 0; i < this.childCount; i++)
			  this.children[i].aggregateTags(false);
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
		  
		  return RSMath.sortTags(aggTags);
	  }
	  return arrTags;
  }
  
  public void resetTags() {
	  this.arrTags = null;
	  this.tagCount = 0;
  }
  
  public Entity findChild(String cName) {
	  return findChild(cName, 0);
  }
  
  public Entity findChild(String cName, int type) {
	  int init = 0;
	  int end = this.childCount;
	  if (type == _ASTID) 
		  init = numerCount;
	  if (type == _NUMID)
		  end = astroCount;
	  if (type == _CRNTID)
		  init = crntStart;
	  for (int i = init; i < end; i++) {
		 if (this.children[i].getName().contentEquals(cName))
			 return this.children[i];
	  }
	  return null;
  }
  
  public Entity getChild(int index) {
	  return this.children[index];
  }
  
  public Entity getChild(int index, int type) {
	  int init = (type == _ASTID) ? this.numerCount : 0;
	  return this.children[init + index];
  }
  
  public int getChildCount() {
	  return this.childCount;
  }
  
  public Entity[] getChildren()
  {
    return this.children;
  }
  
  public int childCount() {
	  return this.childCount;
  }

  public int getColor()
  {
    return this.color;
  }

  public int getAngle()
  {
    return this.Angle;
  }

  public String getDesc()
  {
    return this.sDesc;
  }

  public int getID()
  {
    return this.nID;
  }

  public String getName()
  {
    return this.sName;
  }
  
  public String getFullName()
  {
    return this.sName;
  }

  public Entity getParent()
  {
    return this.parent;
  }

  public Entity getPrevEnt()
  {
    return this.prevEnt;
  }

  public boolean getVisible()
  {
    return this.Visible;
  }

  public int getStringLvl()
  {
    return this.strLevel;
  }
  
  public Tags[] getArrTags() {
	  return this.arrTags;
  }
  
  public Tags[] getAllTags() {
	  if (this.entType == _TAGID) 
			return null;
	  ArrayList<Tags> alltags = new ArrayList<Tags>();
	  for (int i = 0; i < getTagCount(); i++) {
		  alltags.add(arrTags[i]);
			  
	  }
	  
	  
	  Tags[] ctags;
	  for (int i = 0; i < childCount; i++) {
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
  }

  public Tags[] getTags(int maxtags)
  {	  
	if (this.entType == _TAGID) 
		return null;
	
	Tags[] alltags = getAllTags();
	
	ArrayList<Tags> rettags = new ArrayList<Tags>();
	
	int[] ocount = new int[Tags._ALLTAGS];
	
	for (int i = 0; i < Tags._ALLTAGS; i++)
		ocount[i] = GlobalLL.TagsLL[i].getCount();
	
	for (int i = 0; i < alltags.length; i++)
		alltags[i].updCount();
	
	int count = 0;
	
	for (int i = 0; i < Tags._ALLTAGS; i++) {
		if (GlobalLL.TagsLL[i].getCount() != ocount[i]) {
			if ((count++ < maxtags) || maxtags == -1)
				rettags.add(GlobalLL.TagsLL[i]);
			GlobalLL.TagsLL[i].setCount(ocount[i]);
		}
	}
	
	Tags[] retarr = rettags.toArray(new Tags[count]);
	
	return retarr;
  }
  
  public Tags[] getTags(Tags[] alltags)
  {	  	
	ArrayList<Tags> rettags = new ArrayList<Tags>();
	
	int[] ocount = new int[Tags._ALLTAGS];
	
	for (int i = 0; i < Tags._ALLTAGS; i++)
		ocount[i] = GlobalLL.TagsLL[i].getCount();
	
	for (int i = 0; i < alltags.length; i++)
		alltags[i].updCount();
	
	int count = 0;
	
	for (int i = 0; i < Tags._ALLTAGS; i++) {
		if (GlobalLL.TagsLL[i].getCount() != ocount[i]) {
				rettags.add(GlobalLL.TagsLL[i]);
			GlobalLL.TagsLL[i].setCount(ocount[i]);
		}
	}
	
	Tags[] retarr = rettags.toArray(new Tags[count]);
	
	return retarr;
  }
  
  public Tags[] getTags()
  {
	if (this.entType == _TAGID) 
		return null;

	return getTags(-1);    
  }
  
  public int getTagCount() {
	  return this.tagCount;
  }
  
  
  public void setTags(Tags[] tags) {
	  this.arrTags = tags;
	  if (this.arrTags == null)
		  return;
	  int i = 0;
	  while (i < arrTags.length && arrTags[i] != null)
		  i++;
	  this.tagCount = i;
  }
  
  public void setTags(Tags[] tags, int setCount) {
	  this.arrTags = tags;
	  this.tagCount = setCount;
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
  }
  
  
  public int countTags() {
	  int tCount = tagCount;
	  for (int i = 0; i < childCount; i++) {
		  tCount += this.children[i].countTags();
	  }
	  
	  return tCount;
	  
  }
 
  public Entity getTopEnt()
  {
    return Entity.topEnt;
  }

  public int getType() {
	  return this.entType;
  }
  

  public boolean getClickable()
  {
    return this.Clickable;
  }
  
  public boolean getSeen() {
	  return this.isNew;
  }
  
  public float getRelation() {
	  return this.relation;
  }
  
  public void setRelation(float ratio) {
	  this.relation = ratio;
  }  
  
  public void setSeen(boolean isn) {
	  this.isNew = isn;
  }

  public void setClickable(boolean click)
  {
    this.Clickable = click;
  }
  
  public void setColor(int paramInt)
  {
    this.color = paramInt;
  }

  public void setAngle(int paramFloat)
  {
    this.Angle = paramFloat;
  }

  public void setDesc(String paramString)
  {
    this.sDesc = paramString;
  }

  public void setPrevEnt(Entity paramEntity)
  {
    this.prevEnt = paramEntity;
  }

  public void setString(int paramInt)
  {
    this.strLevel = paramInt;
  }
  
  public void setCrntStart(int start) {
	  this.crntStart = start;
  }
  
  public int getCrntStart() {
	  return this.crntStart;
  }

  public void setIsCurrent(boolean isc) {
		this.isCurrent = isc;
		//if (this.isCurrent)
		//	this.entType = _CRNTID;
  }
	
  public boolean getIsCurrent() {
		return this.isCurrent;
  }
	
  public RSGFXEntity getGFX() {
		if (_gfx == null)
			setGfx();
		return this._gfx;
  }
  
  public void setGfx() {
	  if (color == 0 || color == 0xFF000000) 
			color = 0xFFFFFFFF;
	  _gfx = new RSGFXLight(this,RSMath.Hex2Float(color),true,true);
	  _gfx.setRot(this.Angle, 0, 0);
  }
	
  public void addToChart(StringBuffer add) {
	  if (tofile == null)
		  return;
	  tofile.append(add);
	  tofile.append(RSIO._LB);
  }
  
  public void addToChart(String add) {
	  if (tofile == null)
		  return;
	  tofile.append(add);
	  tofile.append(RSIO._LB);
  }
  
  public void setChart(StringBuffer _tf) {
	  tofile = _tf;
  }
  
  public void clearChart() {
	  tofile = null;
  }
  
}