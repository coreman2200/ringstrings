package com.twentwo.ringstrings;

import android.location.Location;
import android.util.Log;

import java.util.Calendar;

import swisseph.SweConst;
import swisseph.SweDate;
import swisseph.SwissEph;
//import swisseph.SwissephException;
//import swisseph.TransitCalculator;
//import swisseph.TCPlanetPlanet;

public class RSAstrology
{
  private static double MAXORB = 1.0D;
  private static final int[] SE_Planets;
  private static final String[] STR_Aspects;
  public static final String[] STR_Houses;
  public static final String[] STR_Zodiac;
  
  private static final int _MAXPLANETS = 12;
  //private static int[] adjCusp;
  private static int appliedHouseSys = (int)'T';
  private static double armc = 0.0D;
  private static double[] cusp;
  //private static double cuspBase = 0.0D;
  //private static int cuspOffset = 0;
  //private static boolean isCuspSet = false;
  private static double[] plLonSpd;
  public static Astrology[] asthouses;
  public static Astrology[] astsigns;
  private static SweDate sd;
  private static SwissEph sw;
  private static final int sweCalcFlags = SweConst.SEFLG_SWIEPH | SweConst.SEFLG_TRUEPOS | SweConst.SEFLG_SPEED;//33040;
  //private static final int sweTransFlags = 163842;
  private static Person crntPerson;
  private static boolean isCrnt = false;

  static
  {
    String[] arrayOfString1 = new String[7];
    arrayOfString1[0] = "Conjunction"; // 0
    arrayOfString1[1] = "Semi-Sextile"; // 30
    arrayOfString1[2] = "Sextile"; // 60
    arrayOfString1[3] = "Square"; // 90
    arrayOfString1[4] = "Trine";  // 120
    arrayOfString1[5] = "Quincunx"; // 150
    arrayOfString1[6] = "Opposition"; // 180
    STR_Aspects = arrayOfString1;
    String[] arrayOfString2 = new String[12];
    arrayOfString2[0] = "House I";
    arrayOfString2[1] = "House II";
    arrayOfString2[2] = "House III";
    arrayOfString2[3] = "House IV";
    arrayOfString2[4] = "House V";
    arrayOfString2[5] = "House VI";
    arrayOfString2[6] = "House VII";
    arrayOfString2[7] = "House VIII";
    arrayOfString2[8] = "House IX";
    arrayOfString2[9] = "House X";
    arrayOfString2[10] = "House XI";
    arrayOfString2[11] = "House XII";
    STR_Houses = arrayOfString2;
    String[] arrayOfString3 = new String[12];
    arrayOfString3[0] = "Aries";
    arrayOfString3[1] = "Taurus";
    arrayOfString3[2] = "Gemini";
    arrayOfString3[3] = "Cancer";
    arrayOfString3[4] = "Leo";
    arrayOfString3[5] = "Virgo";
    arrayOfString3[6] = "Libra";
    arrayOfString3[7] = "Scorpio";
    arrayOfString3[8] = "Sagittarius";
    arrayOfString3[9] = "Capricorn";
    arrayOfString3[10] = "Aquarius";
    arrayOfString3[11] = "Pisces";
    STR_Zodiac = arrayOfString3;
    int[] arrayOfInt = new int[19];
    arrayOfInt[1] = 1;
    arrayOfInt[2] = 2;
    arrayOfInt[3] = 3;
    arrayOfInt[4] = 4;
    arrayOfInt[5] = 5;
    arrayOfInt[6] = 6;
    arrayOfInt[7] = 7;
    arrayOfInt[8] = 8;
    arrayOfInt[9] = 9;
    arrayOfInt[10] = 11;
    arrayOfInt[11] = 13;
    arrayOfInt[12] = 15;
    arrayOfInt[13] = 17;
    arrayOfInt[14] = 18;
    arrayOfInt[15] = 19;
    arrayOfInt[16] = 20;
    arrayOfInt[17] = 98;
    arrayOfInt[18] = 99;
    SE_Planets = arrayOfInt;
    sw = null;
    sd = null;
    MAXORB = 7.0D;
    cusp = new double[13];
    asthouses = new Astrology[12];
    astsigns = new Astrology[12];
    //adjCusp = new int[12];
    plLonSpd = new double[SE_Planets.length];
  }

  public static void initEphe(Person p1)
  {
    sw = new SwissEph(RSIO.getEpheDir());
    //sw.swe_set_ephe_path(RSIO.getEpheDir());
    crntPerson = p1;
  }
  
  public static void initEphe(Person p1, boolean iscrnt)
  {
    isCrnt = iscrnt;
    initEphe(p1);
  }

  public static void setDate(Calendar paramCalendar)
  {
    sd = new SweDate(paramCalendar.get(1), 1 + paramCalendar.get(2), paramCalendar.get(5), paramCalendar.get(11) + paramCalendar.get(12) / 60.0D);
  }

  public static void setMaxOrb(double paramDouble)
  {
    if (paramDouble >= 0.0D)
    	MAXORB = paramDouble;
  }
  
  public static void RSAstroClose()
  {
	  crntPerson = null;
	  isCrnt = false;
	  for (int i = 0; i < 12; i++) {
		  asthouses[i] = null;
		  astsigns[i] = null;
	  }
	  sw.swe_close();
  }

  public static void astrGetHouses(Location paramLocation)
  {
    double[] arrayOfDouble = new double[10];
    sw.swe_houses(sd.getJulDay(), 0, paramLocation.getLatitude(), paramLocation.getLongitude(), appliedHouseSys, cusp, arrayOfDouble);
    armc = arrayOfDouble[2];
    for (int i = 0; i < 12; i++) {
    	
    	asthouses[i] = new Astrology(STR_Houses[i], Astrology.AST_HOUSE);
    	asthouses[i].setDegreeInHouse((float)cusp[i+1]);
  	  	astsigns[i] = new Astrology(STR_Zodiac[i], Astrology.AST_SIGN);
    }
    //if (i == 0)
    //  isCuspSet = true;
    //cuspOffset = (int)(cusp[4] / 30);
  }

  public static void astrPlacePlanets(Location paramLocation)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    double[] arrayOfDouble1 = new double[SE_Planets.length];
    double[] arrayOfDouble2 = new double[6];
    double[] arrayOfDouble3 = new double[6];
    double jDay = sd.getJulDay();
    sw.swe_set_topo(paramLocation.getLongitude(), paramLocation.getLatitude(), paramLocation.getAltitude());
    sw.swe_calc_ut(jDay, -1, sweCalcFlags, arrayOfDouble3, localStringBuffer);
    if (localStringBuffer.length() > 0)
    	Log.e("Astrology", localStringBuffer.toString());
    String str;
    Astrology localEntity;
    float degInHouse, degInSign;
    double cuspstart;
    
    for (int i = 0; i < SE_Planets.length-2; i++) {
    	sw.swe_calc_ut(jDay, SE_Planets[i], sweCalcFlags, arrayOfDouble2, localStringBuffer);
    	if (localStringBuffer.length() > 0)
        	Log.e("Astrology", localStringBuffer.toString());
    	str = getCelBodyName(SE_Planets[i]);
        plLonSpd[i] = arrayOfDouble2[3];
        localEntity = new Astrology(str, Astrology.AST_CELESTBODY);
        arrayOfDouble1[i] = sw.swe_house_pos(armc, paramLocation.getLatitude(), arrayOfDouble3[0], appliedHouseSys, arrayOfDouble2, localStringBuffer);
        //if (i < _MAXPLANETS) {
        cuspstart = (int)(arrayOfDouble2[0]/30);
       	degInSign = (float)arrayOfDouble2[0] - (float)(cuspstart*30);
       	degInHouse = (float)(arrayOfDouble2[0] - cusp[((int)arrayOfDouble1[i])]);
       	//Log.v("Astrology", getCelBodyName(SE_Planets[i]) + " ~ House: " + (int)arrayOfDouble1[i] + " PosLong: " + arrayOfDouble2[0] + " Speed: " + plLonSpd[i]);
        localEntity.setDegreeInHouse(degInHouse);
        localEntity.setDegreeInSign(degInSign);
        //Log.v("Astrology", getCelBodyName(SE_Planets[i]) + " ~ Degree In House("+(int)arrayOfDouble1[i]+"): " + degInHouse + "; Degree In Sign("+STR_Zodiac[(int)(arrayOfDouble2[0]/30)]+"): " + degInSign);
        if (i != 10 && plLonSpd[i] < 0)
        	localEntity.setRetro(true);
        
        localEntity.setCelestBody(asthouses[((int)arrayOfDouble1[i])-1], astsigns[(int)(arrayOfDouble2[0]/30)]);
        localEntity.setHouseNum((int)arrayOfDouble1[i]);
       	localEntity.setIsCurrent(isCrnt);
       	if (isCrnt)
       		crntPerson.getCurrentAstro().addChild(localEntity);
       	else
       		crntPerson.addChild(localEntity);
    }
    if (!isCrnt) {
	    Astrology asc = new Astrology("Ascendant", Astrology.AST_CELESTBODY);
	    asc.setCelestBody(asthouses[0], astsigns[(int)(cusp[1]/30)]);
	    asc.setHouseNum(1);
	    Astrology mc = new Astrology("Midheaven", Astrology.AST_CELESTBODY);
	    mc.setCelestBody(asthouses[9], astsigns[(int)(cusp[10]/30)]);
	    mc.setHouseNum(10);
	    crntPerson.addChild(asc);
	    crntPerson.addChild(mc);
    }
  }

  public static void astrSetAspect(int paramInt1, int paramInt2, int paramInt3, double degree)
  {
      String str1 = getCelBodyName(paramInt2);
      String str2 = getCelBodyName(paramInt3);
      int _id = Entity._ASTID;
      Entity main = (isCrnt) ? crntPerson.getCurrentAstro() : crntPerson;
      Astrology cb1 = (Astrology)main.findChild(str1, _id);
      if (cb1 == null) {
    	  Log.e("Astrology", str1 + " was not found within parent " + crntPerson.getName() + ", creating it, now.");
    	  cb1 = new Astrology(str1, Astrology.AST_CELESTBODY);
      }
      Astrology cb2 = (Astrology)main.findChild(str2, _id);
      if (cb2 == null) {
    	  Log.e("Astrology", str2 + " was not found within parent " + crntPerson.getName() + ", creating it, now.");
    	  cb2 = new Astrology(str2, Astrology.AST_CELESTBODY);
      }
      Astrology aspect = new Astrology(STR_Aspects[paramInt1], Astrology.AST_TRANSIT);
      aspect.setTransit(cb1, cb2, (float)degree);
      aspect.setIsCurrent(isCrnt);
      main.addChild(aspect);
  }
  
  public static void astrGetAspects(int paramInt1, int paramInt2) {
	  Astrology pl1 = (Astrology)crntPerson.findChild(getCelBodyName(SE_Planets[paramInt1]));
	  Astrology pl2 = (Astrology)crntPerson.findChild(getCelBodyName(SE_Planets[paramInt2]));
	  double p1Deg = pl1.getDegreeInHouse() + cusp[pl1.getHouseNum()];
	  double p2Deg = pl2.getDegreeInHouse() + cusp[pl2.getHouseNum()];
	  double diff = (p1Deg > p2Deg) ? (p1Deg - p2Deg) : (p2Deg - p1Deg);
	  if (diff > 200.0)
		  diff -= 180.0D;
	  double maxminus = (MAXORB/30);
	  double casp = diff/30;
	  int closestasp = (int)casp;
	  if ((int)(maxminus+casp) > closestasp)
		  closestasp++;
	  if (Math.abs(diff - (closestasp*30)) < MAXORB) {
		  astrSetAspect(closestasp, SE_Planets[paramInt1], SE_Planets[paramInt2], diff);
	  }
  }
  
  public static void astrGetTransits()
  {
	  int length = SE_Planets.length;
	  if (isCrnt)
		  length -= 2;
    for (int i = 0; i < _MAXPLANETS; i++) {
    	//Log.v("Astrology", "Finding Transits for " + getCelBodyName(SE_Planets[i]));
	    for (int j = i+1; j < length; j++)
	    {
	      astrGetAspects(i, j);
	    }
    }
    Log.v("Astrology", "Transits complete.");
  }
    
  private static String getCelBodyName(int paramInt)
  {
    String str;
    if (paramInt == 13) 
    	str = "Lilith";
    else if (paramInt == 10 || paramInt == 11)
    	str = "North Node";
    else if (paramInt == 98) 
    	str = "Ascendant";
    else if (paramInt == 99)
    	str = "Midheaven";
    else
    	str = sw.swe_get_planet_name(paramInt);

    return str;
  }
}

/*  private static int wrapVal(int paramInt1, int paramInt2, int paramInt3)
  {
	  int i = paramInt2 % (paramInt3+1);
	  if (i < paramInt1)
		  i += paramInt3;

    return i;
  }
*/


/* public static void astrGetAspects(int paramInt1, int paramInt2)
 {
	  
	  TransitCalculator ourtransit = new TCPlanetPlanet(sw, SE_Planets[paramInt1], SE_Planets[paramInt2], sweTransFlags, 0.0D);
	  double jDay = sd.getJulDay();
	  double tDay1 = 0.0;
	  double tDay2 = 0.0;
	  double deg = 999.0;
	  double tdeg;
	  boolean back = false;
	  double days = (MAXORB / Math.abs(plLonSpd[paramInt2]));
	  if (days < 0.5)
		  days += 0.5;
	  int lindex = 0;
	  
	  for (int i = 0; i < 7; i++) {
		  ourtransit.setOffset(30*i);
		  try {
			  tDay1 = sw.getTransitUT(ourtransit, jDay, false, jDay + days);
		  }
		  catch (SwissephException swerr) {
			  tDay1 = -1.0;
		  }
		  if (tDay1 < 0) {
			  try {
				  tDay2 = sw.getTransitUT(ourtransit, jDay, true, jDay - days);
			  }
			  catch (SwissephException swerr) {
				  tDay1 = -1.0;
			  }
		  }
		  
		  if (tDay1 > 0) {
			  tdeg = ((tDay1 - jDay)/days);
			  if (tdeg < deg) {
				  deg = tdeg;
				  lindex = i;
				  back = false;
			  }
		  }
			  
		  if (tDay2 > 0) {
			  tdeg = ((jDay - tDay2)/days);
			  if (tdeg < deg) {
				  deg = tdeg;
				  lindex = i;
				  back = true;
			  }
		  }
	  }
	  if (deg <= days) {
		  deg *= (back) ? -MAXORB : MAXORB; 
		  deg += (lindex*30);
		  astrSetAspect(lindex, SE_Planets[paramInt1], SE_Planets[paramInt2], deg);
		  return;
	  }
 } */