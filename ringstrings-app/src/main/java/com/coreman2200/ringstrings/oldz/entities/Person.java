package com.coreman2200.ringstrings.oldz.entities;

import com.coreman2200.ringstrings.oldz.astrology.RSAstrology;
import com.coreman2200.ringstrings.oldz.deprbizlogic.RSMath;
import com.coreman2200.ringstrings.oldz.deprbizlogic.calcRunnable;
import com.coreman2200.ringstrings.oldz.deprbizlogic.currentRunnable;
import com.coreman2200.ringstrings.oldz.io.RSIO;
import com.coreman2200.ringstrings.oldz.io.reviveRunnable;
import com.coreman2200.ringstrings.oldz.maps.RSGoogleLoc;
import com.coreman2200.ringstrings.oldz.numerology.Numerology;
import com.coreman2200.ringstrings.oldz.numerology.RSNumerology;

import android.location.Location;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Person extends HigherEntity {
	private static final long serialVersionUID = _PSNID;

	public static final int _NOFIRSTNAME = 0x01;
	public static final int _NOMIDNAME = 0x02;
	public static final int _NOLASTNAME = 0x04;
	public static final int _NOBDATE = 0x08;
	public static final int _NOBTIME = 0x10;
	public static final int _NOBIRTHGEO = 0x20;
	public static final int _NOCRNTGEO = 0x40;
	public static final int _NOCRNTTIME = 0x80;
	
	//private static Context crntContext;
	transient private static Calendar Today;
	private static boolean todayset = false;
	private Calendar Bday;
	transient private Location BirthGeo;
	transient private Location CrntGeo;
	private double[] bgeo4s = new double[3];
	private int cachedOffset = -999;
	private String FirstName;
	private String LastName;
	private String MiddleName;
	private int givenDay;
	private int missingvals;
	transient private boolean personset = false;
	private boolean myself = false;
	transient private boolean currentChartSet = false;
	transient private boolean testfullprofile = false;	
	
	
	public Person() {
		super("Unknown");
		missingvals = 0xFF;
	}
	
	public Person(String fname) {
		super(fname);
		this.children = new Entity[196];
		this.HEntType = HE_Person;
		this.entType = _PSNID;
		this.FirstName = fname;
		this.missingvals = 0xFF - _NOFIRSTNAME;
	}
	
	
	public Person(String fname, String lname) {
		super(fname + " " + lname);
		this.children = new Entity[196];
		this.HEntType = HE_Person;
		this.entType = _PSNID;
		FirstName = fname;
		LastName = lname;
		missingvals = 0xFF - (_NOFIRSTNAME | _NOLASTNAME);
	}
	
	public Person(String fname, String mname, String lname) {
		super(fname + " " + lname);
		this.children = new Entity[196];
		this.HEntType = HE_Person;
		this.entType = _PSNID;
		FirstName = fname;
		MiddleName = mname;
		LastName = lname;
		missingvals = 0xFF - (_NOFIRSTNAME | _NOMIDNAME | _NOLASTNAME);
	}
	
	public void calcPerson(boolean self) {
		if (todayset) {
			setValue(_NOCRNTTIME);
		}
		setMySelf(self);
		calcStream.execute(new calcRunnable(this));
	}
	
	public void setMySelf(boolean self) {
		this.myself = self;
		if (myself)
			MySelf = this;
	}
	
	public boolean isMissingValue(int val) {
		return ((missingvals & val) == val);
	}
	
	public void cnrtChartIsSet(boolean cset) {
		this.currentChartSet = cset;
	}
	
	public boolean getCrntChartSet() {
		return this.currentChartSet;
	}
	
	public void setValue(int val) {
		if ((missingvals & val) == val)
			missingvals -= val;
	}
	
	public void ReviveLoad() {
		setMySelf(this.myself);
		Location myBLoc = new Location(sName + " Birth Geo");
		myBLoc.setLatitude(bgeo4s[0]);
		myBLoc.setLongitude(bgeo4s[1]);
		myBLoc.setAltitude(bgeo4s[2]);
		setBirthGeo(myBLoc);
		setToday();
		//new reviveRunnable(this).run();
		calcStream.execute(new reviveRunnable(this));
	}
	
	public double[] getSavedGeo() {
		return this.bgeo4s;
	}
	
	public void setfullprofile(boolean fp) {
		this.testfullprofile = fp;
	}
	
	public void setcurrentonly(boolean co) {
		this.onlycurrent = co;
	}
	
	public boolean getcurrentonly() {
		return this.onlycurrent;
	}
	
	public Entity getCurrentAstro() {
		if (this.currentAstrology == null)
			this.currentAstrology = new Entity("Current Astrology", 64);
		return this.currentAstrology;
	}
	
	public void setCurrentAstro(Entity cAstro) {
		this.currentAstrology = cAstro;
	}
	
	public void PersonIsSet() {
		// Do something to suggest to other threads that a person has been created.
		ALLPEOPLE.addChild(this);
		personset = true;
		isDisplayReady = true;
		if (myself) {
			Log.d("Person", "The Person known as Self is Set.");
			setCurrentEntity(this);
		}
		
		Log.d("Person", sName + " is Set..");
		//setfullprofile(true);	
		//display();
	}
	
	public void calcCurrent() {
		if (!isCurrent)
			calcStream.execute(new currentRunnable(this));
	}
	
	public boolean getIsPersonSet() {
		return this.personset;
	}
	
	public String getMissingQualities() {
		if (missingvals == 0)
			return "No missing values for " + sName;
		
		StringBuffer errors = new StringBuffer();
		
		errors.append(sName + " currently has missing values affecting a full reading: ");
		int sta = errors.length();
		if (isMissingValue(_NOFIRSTNAME))
			errors.append("No First Name.. ");
		if (isMissingValue(_NOMIDNAME))
			errors.append("No Middle Name.. ");
		if (isMissingValue(_NOLASTNAME))
			errors.append("No Last Name.. ");
		if (isMissingValue(_NOBDATE))
			errors.append("No Birth Date.. ");
		if (isMissingValue(_NOBTIME))
			errors.append("No Birth Time.. ");
		if (isMissingValue(_NOBIRTHGEO))
			errors.append("No Birth Place/Location.. ");
		if (myself) {
			if (isMissingValue(_NOCRNTGEO))
				errors.append("No Current Location.. ");
			if (isMissingValue(_NOCRNTTIME))
				errors.append("No Current Time.. ");
		}
		if (errors.length() == sta)
			return "No missing values for " + FirstName;
		
		return errors.toString();
	}
	
	public void display() {
	  StringBuffer buf = new StringBuffer();
	  Log.i("Person", sName);
	  addToChart(sName);
	  addToChart(getMissingQualities());
	  addToChart(" ");
	  //Log.v("Base Class Entity", sDesc);
	  buf.append("Qualities: ");
	  addToChart(" ");
	  if (arrTags == null)
		  aggregateTags(true);
	  Tags[] mtags = getTags();
	  Log.i("Person", "Total Used Tags: " + mtags.length);
	  addToChart("Total Used Tags: " + mtags.length);
	  if (mtags != null) {
		  int Count = (mtags.length > _MAXTAGS*2) ? _MAXTAGS*2 : mtags.length;
		  for (int i = 0; i < Count; i++) {
			  if (mtags[i] != null) {
				  buf.append(mtags[i].getName());
				  buf.append(" ");
			  } else {
				  break;
			  }
		  }
		  Log.i("Person", buf.toString());
		  addToChart(buf);
	  }
	  addToChart(" ");
	  Log.i("Person", "Total Distinct Calculated Elements: " + this.childCount);
	  addToChart("Total Distinct Calculated Elements: " + this.childCount);
	  addToChart(" ");
	  if (this.testfullprofile) {
		  Entity[] children = getChildren();
		  
		  for (int i = 0; i < this.childCount; i++) {
			  //Log.d("Person", "Current Child["+i+"]: " + children[i].getFullName());
			  children[i].setChart(tofile);
			  children[i].display();
			  addToChart(" ");
		  }
		  RSIO.writeChart(sName + ((onlycurrent) ? " CURRENT@LOC" : ""), tofile);
	  }
	  clearChart();
	}
	
	public void addToChart(StringBuffer add) {
		  if (tofile == null)
			  tofile = new StringBuffer();
		  tofile.append(add);
		  tofile.append(RSIO._LB);
	}
	  
	public void addToChart(String add) {
		  if (tofile == null)
			  tofile = new StringBuffer();
		  tofile.append(add);
		  tofile.append(RSIO._LB);
	}
	
	public void setBirthAstrology(boolean dotransits)
	{
		if (isMissingValue(_NOBIRTHGEO)) {
			Log.e("Person", "Cannot Calculate Astrological Chart for " + sName + " without a Birth Location!");
			return;
		}
		
		if (isMissingValue(_NOBDATE)) {
			Log.e("Person", "Cannot calculate Astrological Chart for " + sName + " without a birthdate!");
			return;
		}
		
		if (isMissingValue(_NOBTIME))
			Log.e("Person", "Warning: Lunar Astrology will be innacurate for " + sName + " without a precise birth time!");
		
		Log.v("Person", "Setting Birth Astrology.");
		RSAstrology.initEphe(this);
		RSAstrology.setDate(this.Bday);
		RSAstrology.astrGetHouses(this.BirthGeo);
		RSAstrology.setMaxOrb(6.0D);
		RSAstrology.astrPlacePlanets(this.BirthGeo);
		if (dotransits)
			RSAstrology.astrGetTransits();
		RSAstrology.RSAstroClose();
		Log.v("Person", "Birth Astrology Set.");
	}

	public void setCrntAstrology(boolean dotransits)
	{
		if (isMissingValue(_NOCRNTGEO))
			this.setCrntGeo(RSGoogleLoc.getCrntLocation());
		RSAstrology.initEphe(this, true);
		RSAstrology.setDate(Person.Today);
		RSAstrology.astrGetHouses(this.CrntGeo);
		RSAstrology.setMaxOrb(1.0D);
		
		RSAstrology.astrPlacePlanets(this.CrntGeo);
		if (dotransits)
			RSAstrology.astrGetTransits();
		RSAstrology.RSAstroClose();
	}
	
	public void setCrntNumerology() {
		int month = 1 + this.Bday.get(Calendar.MONTH);
		int bday = this.givenDay;
		int thisyear = Person.Today.get(Calendar.YEAR);
		
		this.addChild(RSNumerology.numGetPersonalYear(bday, month, thisyear));
		this.addChild(RSNumerology.numGetPersonalMonth(bday, month, thisyear));
		this.addChild(RSNumerology.numGetPersonalDay(bday, month, thisyear));
	}

	public void setNumerology()
	{
		Log.v("Person", "Setting Numerology.");
		RSNumerology.setNumberSystem(RSNumerology._CHALDSYS);
		numFillProfile();
		Log.v("Person", "Numerology Set.");
	}
	
	public void setBirthDate(int day, int month, int year)
	{
		this.givenDay = day;
		GregorianCalendar localGregorianCalendar = new GregorianCalendar(year, month, day, 12, 0);
		this.Bday = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		this.Bday.setTimeInMillis(localGregorianCalendar.getTimeInMillis());
		setValue(_NOBDATE);
		//setValue();
	}
	
	public void setBirthTime(int hour, int minute) {
		this.Bday.set(Calendar.HOUR_OF_DAY, hour);
		this.Bday.set(Calendar.MINUTE, minute);
		setValue(_NOBTIME);
	}
	
	public void setBirthDate(GregorianCalendar tob, int rawOffset)
	{
		this.cachedOffset = rawOffset/ RSMath._MHOUR;
		TimeZone tmz = TimeZone.getDefault();
		tmz.setRawOffset(rawOffset);
		setBirthDate(tob, tmz);
	}

	public void setBirthDate(GregorianCalendar tob, TimeZone btz)
	{
		this.cachedOffset = btz.getRawOffset()/ RSMath._MHOUR;
		this.givenDay = tob.get(Calendar.DATE);
		tob.setTimeZone(btz);
		this.Bday = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		this.Bday.setTimeInMillis(tob.getTimeInMillis());
		setValue(_NOBTIME);
		setValue(_NOBDATE);
	}
	
	public Calendar getBday() {
		return this.Bday;
	}

	public void setBirthGeo(Location paramLocation)
	{
		this.BirthGeo = paramLocation;
		bgeo4s[0] = BirthGeo.getLatitude();
		bgeo4s[1] = BirthGeo.getLongitude();
		bgeo4s[2] = BirthGeo.getAltitude();

		setValue(_NOBIRTHGEO);
	}
	
	public Location getBirthGeo() {
		return this.BirthGeo;
	}
	
	public void setCrntGeo(Location paramLocation)
	{
		this.CrntGeo = paramLocation;
		setValue(_NOCRNTGEO);
	}
	
	public Location getCrntGeo() {
		return this.CrntGeo;
	}

	public void setFirstName(String paramString)
	{
		this.FirstName = paramString;
		setValue(_NOFIRSTNAME);
	}
	
	public String getFirstName() {
		return this.FirstName;
	}

	public void setLastName(String paramString)
	{
		this.LastName = paramString;
		setValue(_NOLASTNAME);
	}
	
	public String getLastName() {
		return this.LastName;
	}

	public void setMidName(String paramString)
	{
		this.MiddleName = paramString;
		setValue(_NOMIDNAME);
	}
	
	public String getFullName()
	  {
	    return this.FirstName + " "  + this.MiddleName + " " + this.LastName;
	  }

	public void setName(String paramString1, String paramString2, String paramString3)
	{
		this.FirstName = paramString1;
		this.MiddleName = paramString2;
		this.LastName = paramString3;
		setValue(_NOFIRSTNAME);
		setValue(_NOMIDNAME);
		setValue(_NOLASTNAME);
	}
	
	public int getMissingVals() {
		return this.missingvals;
	}
	
	public boolean isTZOffset() {
		return (this.cachedOffset > -100);		
	}
	
	public int getTZOffset() {
		return this.cachedOffset;
	}
	
	public void setTZOffset(int tzo) {
		this.cachedOffset = tzo;
	}
	
	public void setMissingVals(int mv) {
		this.missingvals = mv;
	}

	public static void setToday()
	{
		Person.Today = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		Person.todayset = true;
	}
	
	public static void setToday(GregorianCalendar today)
	{
		Person.Today = today;
		Person.todayset = true;
	}
	
	public static void setToday(GregorianCalendar tob, TimeZone btz)
	{
		tob.setTimeZone(btz);
		Person.Today = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		Person.Today.setTimeInMillis(tob.getTimeInMillis());
	}
	
	public static Calendar getToday() {
			return Person.Today;
	}
	
	public void setGivenDay(int gday) {
		this.givenDay = gday;
	}
	
	public boolean getIsMyself() {
		return this.myself;
	}
	
	public static Person getMySelf() {
		return MySelf;
	}
	
	public void numFillProfile()
	{
		if (isMissingValue(_NOLASTNAME))
			this.LastName = "";
		if (isMissingValue(_NOMIDNAME))
			this.MiddleName = "";
		if (isMissingValue(_NOBDATE)) {
			Log.e("Person", "Cannot calculate Numerology for " + sName + " without a birthdate!");
			return;
		}
		
		int month = 1 + this.Bday.get(Calendar.MONTH);
		int bday = this.givenDay;
		int year = this.Bday.get(Calendar.YEAR);

		this.addChild(RSNumerology.numGetLifePath(bday, month, year));
		int lifenum = ((Numerology)this.findChild(RSNumerology._LIFEPATH)).getValue();
		this.addChild(RSNumerology.numGetBirthDay(bday));
		String expName;
		if (!isMissingValue(_NOMIDNAME)) {
			expName = RSNumerology._EXPRESSION;
			this.addChild(RSNumerology.numGetExpression(this.FirstName, this.MiddleName, this.LastName));		
			this.addChild(RSNumerology.numGetSoulUrge(this.FirstName, this.MiddleName, this.LastName));
			this.addChild(RSNumerology.numGetPersonality(this.FirstName, this.MiddleName, this.LastName));
		} else {
			expName = RSNumerology._MEXPRESSION;
			this.addChild(RSNumerology.numGetmExpression(this.FirstName, this.LastName));		
			this.addChild(RSNumerology.numGetmSoulUrge(this.FirstName, this.LastName));
			this.addChild(RSNumerology.numGetmPersonality(this.FirstName, this.LastName));
		}
		int expnum = ((Numerology)this.findChild(expName)).getValue();
		this.addChild(RSNumerology.numGetBalance(this.FirstName, this.MiddleName, this.LastName));
		this.addChild(RSNumerology.numGetMaturity(lifenum, expnum));
		this.addChild(RSNumerology.numGetKarmicLessonsList(this.FirstName, this.MiddleName, this.LastName));
		int klessons = ((Numerology)this.findChild(RSNumerology._KARMICLESSONS)).childCount();
		this.addChild(RSNumerology.numGetSubconsciouSelf(klessons));
		this.addChild(RSNumerology.numGetHiddenPassions(this.FirstName, this.MiddleName, this.LastName));
		this.addChild(RSNumerology.numGetRationalThought(this.FirstName, bday));
		RSNumerology.numGetChallenges(month, bday, year, this);
		RSNumerology.numGetPinnacles(month, bday, year, this);
		RSNumerology.numGetCycles(month, bday, year, this);
	}
	
}


/*
setMySelf(self);
Log.d("Person", getMissingQualities());
setNumerology();
setBirthAstrology(true);
RSIO.saveProfile(this);
this.crntStart = childCount;
if (myself) {
	if (CrntGeo == null)
		setCrntGeo(RSGoogleLoc.getCrntLocation());
	if (CrntGeo != null)
		setCrntAstrology(true);
}
setCrntNumerology();
*/