package com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces

import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.NumberSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.IChartedSymbols
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbol

/**
 * INumberSymbol
 * description
 *
 * Created by Cory Higginbottom on 5/25/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
interface INumberSymbol : ISymbol {
    val value: Int
}

interface IDerivedNumberSymbol : INumberSymbol {
    private enum class Derived {
        LEFT_DIGIT, RIGHT_DIGIT, DERIVED_VALUE
    }
    val leftDerived : INumberSymbolID
    val rightDerived : INumberSymbolID
}

interface IGroupedNumberSymbol : ISymbol {
    fun add(group : IGroupedNumberSymbolID, symbol : NumberSymbol)
    fun add(map : Map<IGroupedNumberSymbolID, NumberSymbol>)
}

interface IChartedNumberSymbols : INumberSymbol, IChartedSymbols