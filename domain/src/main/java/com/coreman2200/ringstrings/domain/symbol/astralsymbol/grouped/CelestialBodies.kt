package com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped

import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID

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
enum class CelestialBodies(val swissephValue: Int, val isRealCelestialBody: Boolean) : ISymbolID {
    SUN(0, true), MOON(1, true), MERCURY(2, true), VENUS(3, true), MARS(4, true), JUPITER(
        5,
        true
    ),
    SATURN(6, true), URANUS(7, true), NEPTUNE(8, true), PLUTO(9, true),
    NORTHNODE(
        11,
        true
    ),
    SOUTHNODE(100, false), LILITH(
        13,
        true
    ), // EARTH(14),
    CHIRON(15, true), CERES(17, true), PALLAS(18, true), JUNO(19, true), VESTA(20, true), ASCENDANT(
        98,
        false
    ),
    MIDHEAVEN(99, false);
}
