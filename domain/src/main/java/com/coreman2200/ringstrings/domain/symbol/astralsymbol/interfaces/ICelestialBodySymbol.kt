package com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces

/**
 * ICelestialBodySymbol
 * Specifies methods for all celestial body symbols.
 *
 * Created by Cory Higginbottom on 6/6/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
interface ICelestialBodySymbol : IAstralSymbol {
    fun setRetrograde(retrograde: Boolean)
    fun isRetrograde(): Boolean

    fun setHouse(house: IHouseSymbol)
    fun getHouse(): IHouseSymbol

    fun setZodiacSign(sign: IZodiacSymbol)
    fun getZodiacSign(): IZodiacSymbol

    // Aspects getAspectWithBody(CelestialBodies body);
    // Aspects[] getAllAspects();
}