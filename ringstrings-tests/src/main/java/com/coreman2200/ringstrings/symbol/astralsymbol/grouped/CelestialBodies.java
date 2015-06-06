package com.coreman2200.ringstrings.symbol.astralsymbol.grouped;

/**
 * CelestialBodies
 * Symbols for considered Celestial Bodies
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

public enum CelestialBodies {
    SUN(0),
    MOON(1),
    MERCURY(2),
    VENUS(3),
    MARS(4),
    JUPITER(5),
    SATURN(6),
    URANUS(7),
    NEPTUNE(8),
    PLUTO(9),
    NORTHNODE(11),
    LILITH(13),
    EARTH(14),
    CHIRON(15),
    CERES(17),
    PALLAS(18),
    JUNO(19),
    VESTA(20),
    ASCENDANT(98),
    MIDHEAVEN(99);

    int mSwissephValue;

    CelestialBodies(int val) {
        this.mSwissephValue = val;
    }

    public int getSwissephValue() {
        return this.mSwissephValue;
    }
}
