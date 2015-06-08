package com.coreman2200.ringstrings.profile;

import com.coreman2200.ringstrings.numerology.numbersystem.NumberSystemType;

import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowLocation;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * TestProfileImpl
 * Test Profile based on my birth statistics.
 *
 * Created by Cory Higginbottom on 5/27/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public final class TestProfileImpl implements IProfile, IProfileTestLoc {

    private final String[] testName = {"Cory", "Michael", "Higginbottom"};
    private final NumberSystemType testNumberSystemType = NumberSystemType.PYTHAGOREAN;
    private final double mMaxOrb = 2.0;

    private static GregorianCalendar mTestDate = new GregorianCalendar(1986, 11, 23, 17, 36);
    private static ShadowLocation mBirthplace = new ShadowLocation();
    private static ShadowLocation mCurrentLoc = new ShadowLocation();

    public TestProfileImpl() {
        setBirthplace();
        setCurrentLoc();
        setBirthplaceTimeOffset();
    }

    private void setBirthplace() {
        mBirthplace.setLatitude(42.21);
        mBirthplace.setLongitude(-71.03);
        mBirthplace.setAltitude(9.48);
    }

    private void setCurrentLoc() {
        mCurrentLoc.setLatitude(37.7749295);
        mCurrentLoc.setLongitude(-122.4194155);
        mCurrentLoc.setAltitude(16);
    }

    private void setBirthplaceTimeOffset() {
        mTestDate.setTimeZone(TimeZone.getTimeZone("GMT-5"));
    }

    public String getFirstName() { return testName[0]; }
    public String getMiddleName(){ return testName[1]; }
    public String getLastName()  { return testName[2]; }
    public int getBirthDay() { return mTestDate.get(Calendar.DAY_OF_MONTH); }
    public int getBirthMonth() { return mTestDate.get(Calendar.MONTH)+1; }
    public int getBirthYear() { return mTestDate.get(Calendar.YEAR); }

    public NumberSystemType getNumberSystem() { return testNumberSystemType; }

    public ShadowLocation getBirthLocation() {
        return mBirthplace;
    }

    public ShadowLocation getCurrentLocation() {
        return mCurrentLoc;
    }

    public double getMaxOrb() {
        return mMaxOrb;
    }

    @Override
    public GregorianCalendar getBirthDate() {
        return mTestDate;
    }

    @Override
    public void genProfile() {
        //System.out.println("Name: " + getFirstName() + " " + getMiddleName() + " " + getLastName());
        //System.out.println("Birth: " + mTestDate.getTime());
        //System.out.println("=================================TestGeneratedUser");
    }
}
