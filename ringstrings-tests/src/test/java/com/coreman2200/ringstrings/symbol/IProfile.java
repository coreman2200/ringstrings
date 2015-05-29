package com.coreman2200.ringstrings.symbol;

import com.coreman2200.ringstrings.numerology.numbersystem.NumberSystemType;

/**
 * IProfile
 * Interface for Profiles (data structures used to store raw user input/data)
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

public interface IProfile {
    String getFirstName();
    String getMiddleName();
    String getLastName();
    int getBirthDay();
    int getBirthMonth();
    int getBirthYear();
    void genProfile();
    NumberSystemType getNumberSystem();
}