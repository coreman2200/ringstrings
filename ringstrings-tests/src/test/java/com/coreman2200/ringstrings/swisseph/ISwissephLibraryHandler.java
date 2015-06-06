package com.coreman2200.ringstrings.swisseph;

import java.util.Calendar;

/**
 * ISwissephLibraryHandler
 * Interface describes how to interact with Swisseph for some subset of its functionalities.
 *
 * Created by Cory Higginbottom on 5/29/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public interface ISwissephLibraryHandler {
    void initSwisseph();
    void setHouseSystem();
    void setDate(Calendar cal);
    void getHouses();
    void getCelestialBodyPos(int swissephval);
    void getAspects();
    void getZodiac();
}
