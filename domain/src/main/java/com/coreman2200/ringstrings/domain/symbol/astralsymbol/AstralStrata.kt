package com.coreman2200.ringstrings.domain.symbol.astralsymbol

import com.coreman2200.ringstrings.domain.SymbolData
import com.coreman2200.ringstrings.domain.symbol.*
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.Aspects
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.CelestialBodies
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.Houses
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.Zodiac
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl.*
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralGroupSymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID

/**
 * AstralStrata
 * Defining strata for astrological symbols
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
enum class AstralStrata : ISymbolStrata {
    ASTRALBODY,
    ASTRALASPECT, // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    ASTRALHOUSE,
    ASTRALZODIAC, // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    ASTRALCHART,
    RELATIONALASTRALMAP;

    override fun produce(data: SymbolData): ISymbol {
        return when (this) {
            ASTRALBODY -> CelestialBodySymbol(CelestialBodies.valueOf(data.symbolid), data.value)
            ASTRALASPECT -> AspectedSymbols(Aspects.valueOf(data.symbolid), )
            ASTRALHOUSE -> HouseSymbol(Houses.valueOf(data.symbolid), data.value)
            ASTRALZODIAC -> ZodiacSymbol(Zodiac.valueOf(data.symbolid), data.value)
            ASTRALCHART -> AstrologicalChart(Charts.valueOf(data.symbolid), data.value)
            // TODO RELATIONALASTRALMAP
            else -> CelestialBodySymbol(CelestialBodies.valueOf(data.symbolid), data.value)

        }
    }


}
