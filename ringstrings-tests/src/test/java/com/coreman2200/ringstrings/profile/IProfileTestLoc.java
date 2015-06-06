package com.coreman2200.ringstrings.profile;

import com.coreman2200.ringstrings.numerology.numbersystem.NumberSystemType;

import org.robolectric.shadows.ShadowLocation;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * IProfileTestLoc
 * Test extension of IProfile to handle android.location.Location methods
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

public interface IProfileTestLoc extends IProfile {
    ShadowLocation getBirthLocation();
    ShadowLocation getCurrentLocation();

}
