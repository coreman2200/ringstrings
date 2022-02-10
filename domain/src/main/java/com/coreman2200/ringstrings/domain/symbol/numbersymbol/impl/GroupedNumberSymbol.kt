package com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl

import com.coreman2200.ringstrings.domain.symbol.CompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.SymbolModel
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.NumberStrata
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.IGroupedNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.IGroupedNumberSymbolID
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID

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
    override val id: IGroupedNumberSymbolID,
    override val value: Int = 0
) : CompositeSymbol<INumberSymbol>(
    id,
    strata = NumberStrata.GROUPEDNUMBERS
),
    IGroupedNumberSymbol {

    override fun get(id: ISymbolID): INumberSymbol? {
        return related[id] as INumberSymbol
    }

    override fun add(symbol: INumberSymbol) {
        super.add(symbol)
        symbol.groupid = id
        related[symbol.id] = symbol
    }

    override fun add(group: IGroupedNumberSymbolID, symbol: INumberSymbol) {
        val gs = GroupedNumberSymbol(group,symbol.value)
        gs.add(symbol)
        add(gs)
    }

    override fun add(map: Map<IGroupedNumberSymbolID, INumberSymbol>) {
        map.forEach {
            add(it.key, it.value)
        }
    }
}
