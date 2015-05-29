package com.coreman2200.ringstrings.symbol;

import com.coreman2200.ringstrings.numerology.numbersystem.AbstractNumberSystem;
import com.coreman2200.ringstrings.numerology.numbersystem.INumberSystem;
import com.coreman2200.ringstrings.numerology.numbersystem.NumberSystemType;

/**
 * TestProfileImpl
 * description
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
    private final int[] testDate = {12,23,1986};
    private final NumberSystemType testNumberSystemType = NumberSystemType.PYTHAGOREAN;

    public String getFirstName() { return testName[0]; }
    public String getMiddleName(){ return testName[1]; }
    public String getLastName()  { return testName[2]; }
    public int getBirthDay() { return testDate[0]; }
    public int getBirthMonth() { return testDate[1]; }
    public int getBirthYear() { return testDate[2]; }
    public NumberSystemType getNumberSystem() { return testNumberSystemType; }

    @Override
    public void genProfile() { return; }
}
