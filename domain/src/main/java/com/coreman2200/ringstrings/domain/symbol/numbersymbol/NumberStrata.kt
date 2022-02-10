package com.coreman2200.ringstrings.domain.symbol.numbersymbol

import com.coreman2200.ringstrings.domain.SymbolData
import com.coreman2200.ringstrings.domain.symbol.Charts
import com.coreman2200.ringstrings.domain.symbol.ISymbolStrata
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.AstralStrata
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.Aspects
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.CelestialBodies
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.Houses
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.Zodiac
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl.*
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.BaseNumbers
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.DerivedKarmicDebts
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.GroupedNumbers
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.BaseNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.DerivedNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.GroupedNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.NumerologicalChart
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbolID
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbol

/**
 * NumberStrata
 * Describes the different tiers for numbers, in the numerological context/sense.
 *
 * Created by Cory Higginbottom on 5/24/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
enum class NumberStrata : ISymbolStrata {
    BASENUMBER, DERIVEDNUMBER, // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    GROUPEDNUMBERS, CHARTEDNUMBERS, RELATIONALNUMBERMAP;

    override fun produce(data: SymbolData): ISymbol {
        return when (this) {
            BASENUMBER -> BaseNumberSymbol(BaseNumbers.valueOf(data.symbolid))
            DERIVEDNUMBER -> DerivedNumberSymbol(INumberSymbolID.id(data.value.toInt()))
            GROUPEDNUMBERS -> GroupedNumberSymbol(GroupedNumbers.realGroup(data.symbolid))
            CHARTEDNUMBERS -> NumerologicalChart()
            // TODO RELATIONALNUMBERMAP
            else -> BaseNumberSymbol(BaseNumbers.valueOf(data.symbolid))

        }
    }
}
