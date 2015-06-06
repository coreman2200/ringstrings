package com.coreman2200.ringstrings.swisseph;

import com.coreman2200.ringstrings.profile.IProfileTestLoc;


/**
 * ISwissEphemerisManager
 * Interface for utilizing swisseph library in RingStrings.
 *
 * Created by Cory Higginbottom on 6/5/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public interface ISwissEphemerisManager {
    void testAstrGetHousesForProfile(IProfileTestLoc profile);
    void testAstrPlaceHousesForProfile(IProfileTestLoc profile);
}
