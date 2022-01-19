package com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl

import com.coreman2200.ringstrings.domain.symbol.Charts
import com.coreman2200.ringstrings.domain.symbol.SymbolModel
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.IGroupedNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberChartSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbol

/**
 * NumerologicalChartImpl
 * Describes entire numerological chart as groups of numerological symbols.
 *
 * Created by Cory Higginbottom on 5/28/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
class NumerologicalChart() : GroupedNumberSymbol(id = Charts.NUMEROLOGICAL), INumberChartSymbol {
    override fun add(symbol: INumberSymbol) {
        super.add(symbol)
        if (symbol is IGroupedNumberSymbol) { related[symbol.id] = symbol }
    }

    override fun add(symbols: Collection<INumberSymbol>) {
        super.add(symbols)
        val groups = symbols.filter { it is IGroupedNumberSymbol }
            .map { it.id to it }.toMap()
        related.putAll(groups)
    }
}
