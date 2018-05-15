package com.coreman2200.domain.model.profiles.interfaces;

import com.coreman2200.domain.model.protos.LocalProfileDataBundle.Placement.Location;

import java.util.GregorianCalendar;

/**
 * IProfileDataBundle
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

public interface IProfileDataBundle {
    String getDisplayName();
    String getFirstName();
    String getMiddleName();
    String getLastName();
    GregorianCalendar getBirthDate();
    int getBirthDay();
    int getBirthMonth();
    int getBirthYear();
    Location getBirthLocation();
    Location getCurrentLocation();
    int getProfileId();
}
