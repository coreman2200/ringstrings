package com.coreman2200.ringstrings.profile;

import com.coreman2200.ringstrings.numerology.numbersystem.NumberSystemType;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

public final class TestProfileImpl implements IProfile {

    private final String[] testName = {"Cory", "Michael", "Higginbottom"};
    private static Calendar mTestDate = new GregorianCalendar(1986, 11, 23, 17, 36);
    private final NumberSystemType testNumberSystemType = NumberSystemType.PYTHAGOREAN;

    public String getFirstName() { return testName[0]; }
    public String getMiddleName(){ return testName[1]; }
    public String getLastName()  { return testName[2]; }
    public int getBirthDay() { return mTestDate.get(Calendar.DAY_OF_MONTH); }
    public int getBirthMonth() { return mTestDate.get(Calendar.MONTH)+1; }
    public int getBirthYear() { return mTestDate.get(Calendar.YEAR); }
    public int getBirthHour() { return mTestDate.get(Calendar.HOUR_OF_DAY); }
    public int getBirthMinute() { return mTestDate.get(Calendar.MINUTE); }
    public NumberSystemType getNumberSystem() { return testNumberSystemType; }

    @Override
    public void genProfile() {
        System.out.println("Name: " + getFirstName() + " " + getMiddleName() + " " + getLastName());
        System.out.println("Birth: " + mTestDate.getTime());
        System.out.println("=================================TestGeneratedUser");
    }
}
