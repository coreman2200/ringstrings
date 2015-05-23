package com.coreman2200.ringstrings.file.io;

import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.location.Location;
import android.util.Log;

import com.coreman2200.ringstrings.entities.Entity;
import com.coreman2200.ringstrings.entities.Person;

public class SaveDataPSN extends SaveData {
	private static final long serialVersionUID = Entity._PSNID;
	public String FName;
	public String MName;
	public String LName;
	public double[] bpos = new double[3];
	public long btime;
	public int givenday;
	public int missingvals;
	public boolean ismyself;
	
	public SaveDataPSN(String name, int type) {
		super(name, (int)serialVersionUID);
		Elems = new SaveData[128];
	}
	
	public Entity toEnt(Entity parent) { 
		Log.d("SaveDataPSN", "Creating Entity from savedata " + EntName);
		Person me = new Person(FName, MName, LName);
		GregorianCalendar bday = new GregorianCalendar();
		bday.setTimeInMillis(btime);
		me.setBirthDate(bday, TimeZone.getTimeZone("UTC"));
		me.setGivenDay(givenday);
		Location meLoc = new Location(me.getName() + " Birth Geo");
		meLoc.setLatitude(bpos[0]);
		meLoc.setLongitude(bpos[1]);
		meLoc.setAltitude(bpos[2]);
		me.setBirthGeo(meLoc);
		me.setMySelf(ismyself);
		me.setMissingVals(missingvals);

		for (int i = 0; i < Elems.length; i++) {
			if (Elems[i] != null)
				me.addChild(Elems[i].toEnt(me));
			else 
				break;
		}
		
		return me; 
	}
}
