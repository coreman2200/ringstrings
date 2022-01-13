package com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl

import com.coreman2200.ringstrings.domain.symbol.CompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.SymbolModel
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.NumberStrata
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.IGroupedNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.IGroupedNumberSymbolID

/**
 * GroupedNumberSymbol
 * description
 *
 * Created by Cory Higginbottom on 1/10/22
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

open class GroupedNumberSymbol(
    override val id: IGroupedNumberSymbolID
) : CompositeSymbol<SymbolModel>(
    id,
    strata = NumberStrata.GROUPEDNUMBERS
),
    IGroupedNumberSymbol {

    override fun add(group: IGroupedNumberSymbolID, symbol: SymbolModel) {
        related[group] = symbol
        add(symbol)
    }

    override fun add(map: Map<IGroupedNumberSymbolID, SymbolModel>) {
        related.putAll(map)
        map.values.forEach { add(it) }
    }
}
