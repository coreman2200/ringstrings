package com.coreman2200.ringstrings.domain.swisseph

import com.coreman2200.ringstrings.domain.input.entity.IProfileData
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl.CelestialBodySymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl.HouseSymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl.ZodiacSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID

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
interface ISwissEphemerisManager {
    fun produceNatalAstralMappingsForProfile(profile: IProfileData)
    fun produceCurrentAstralMappingsForProfile(profile: IProfileData)
    fun producedZodiacMap(): Map<ISymbolID, ZodiacSymbol>
    fun producedHouseMap(): Map<ISymbolID, HouseSymbol>
    fun producedCelestialBodyMap(): Map<ISymbolID, CelestialBodySymbol>
    val cuspOffset: Double
}
