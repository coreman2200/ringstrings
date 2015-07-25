package com.coreman2200.ringstrings.oldz.entities;

import com.coreman2200.ringstrings.oldz.deprbizlogic.GlobalLL;

public class Tags extends Entity
{
	
	private static final long serialVersionUID = _TAGID;
	public static final int _ALLTAGS = 239;
	public static int tagCount;
	
	private int Count;

  public Tags(String paramString)
  {
	super(paramString); 
	this.Count = 0;
    this.color = 0xFFFFFF;
    this.Clickable = false;
    this.nID = (_TAGID + tagCount);
    this.entType = _TAGID;
    if (tagCount < GlobalLL.TagsLL.length)
    	GlobalLL.TagsLL[tagCount++] = this;   
  }
  
  public int getCount() {
	  return this.Count;
  }
  
  public void setCount(int cc) {
	 this.Count = cc;
  }

  public void resetTag() {
	  this.Count = 0;
  }
  
  public void updCount() {
	  this.Count++;
  }
  
}