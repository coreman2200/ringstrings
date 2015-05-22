package com.coreman2200.ringstrings;

import android.util.Log;

//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.ListIterator;


public class RSMath
{
  
  public static final double _PI = 3.141592653589793D;
  public static final double  _PI180 = 0.017453D;
  public static final double  _180PI = 57.295780D;
  public static final int _MHOUR = 60*60*1000;
 
  public static Tags[] sortTags(Tags[] ulTags) {
	  int hindex = 0;
	  int nc = 0;
	  int nlength = ulTags.length;
	  
	  Tags[] olTags = new Tags[nlength];
	  
	  while (nc < nlength) {
		  for (int i = 0; i < nlength; i++) {
			  if (ulTags[i] != null) {
				  if (ulTags[i].getCount() > ulTags[hindex].getCount()) {
					  hindex = i;
				  }
			  }
		  }
		  olTags[nc++] = ulTags[hindex];
		  ulTags[hindex].resetTag();
	  }
	  
	  return olTags;
  }
  
  public static Entity[] sortByRelation(Entity[] ulEnts) {
	  int hindex = 0;
	  float hval = 0;
	  int nc = 0;
	  int nlength = ulEnts.length;
	  
	  Entity[] olEnts = new Entity[nlength];
	  
	  for (int i = 0; i < ulEnts.length; i++) {
		  if (ulEnts[i] != null) {
			  if (ulEnts[i].getRelation() == 0) {
				  olEnts[--nlength] = ulEnts[i];
				  ulEnts[i] = null;
			  }
		  }
	  }
	  
	  nlength = ulEnts.length;

	  while (nc < nlength) {
		  for (int i = 0; i < nlength; i++) {
			  if (ulEnts[i] != null) {
				  if (ulEnts[i].getRelation() > hval) {
					  hindex = i;
					  hval = ulEnts[hindex].getRelation();
				  }
			  }
		  }
		  if (hval != 0.0f) {
			  olEnts[nc++] = ulEnts[hindex];
			  ulEnts[hindex] = null;
			  hval = 0.0f;
		  } else 
			  break;
	  }
	  return olEnts;
  }
    
  public static int HEXtoARGB(int paramInt1, int paramInt2)
  {
    return 0xFF & paramInt1 >> 24 - paramInt2 * 8;
  }
  
  public static int aRGBtoHEX(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return paramInt4 | (paramInt1 << 24 | paramInt2 << 16 | paramInt3 << 8);
  }

  public static float MyAbs(float paramFloat)
  {
	return (paramFloat < 0.0f) ? -paramFloat : paramFloat;
  }
  
  public static float DegtoRad(float paramFloat)
  {
    return (float)(_PI180 * paramFloat);
  }
  
  public static float RadtoDeg(float paramFloat)
  {
    return (float)(paramFloat * _180PI);
  } 

  public static float getAngle(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    return (float)(180.0D * Math.atan2(paramFloat4 - paramFloat2, paramFloat3 - paramFloat1) / _PI);
  }

  public static int getDist(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt1 - paramInt3; // x1 - x2
    int j = paramInt2 - paramInt4; // y1 - y2
    return (int)Math.sqrt(i * i + j * j);
  }
  
  public static float[] Hex2Float(int hexcolor) {
	  float[] rgba = new float[4];
	  rgba[0] = (float)((hexcolor >> 16 & 0xFF) / 255.0f);
	  rgba[1] = (float)((hexcolor >> 8  & 0xFF) / 255.0f);
	  rgba[2] = (float)((hexcolor       & 0xFF) / 255.0f);
	  rgba[3] = 1.0f;
	  return rgba;
  }
  
  public static float[] RGBA2Float(int rr, int gg, int bb, int aa) {
	  float[] rgba = new float[4];
	  rgba[0] = rr / 255.0f;
	  rgba[1] = gg / 255.0f;
	  rgba[2] = bb / 255.0f;
	  rgba[3] = aa / 255.0f;
	  return rgba;
  }

  public static int sign(int paramInt)
  {
      return (paramInt < 0) ? -1 : 1;
  }

}