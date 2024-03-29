package com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl

import com.coreman2200.ringstrings.domain.symbol.CompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.NumberStrata
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.BaseNumbers
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.IDerivedNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbolID

/**
 * DerivedNumberSymbolImpl
 * Derived Number Symbols are number symbols with two Base Number Symbols
 * from whence they are derived.
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
class DerivedNumberSymbol(
    override val id: INumberSymbolID,
    override val value: Int = id.value()
) : CompositeSymbol<INumberSymbol>(
    id,
    strata = NumberStrata.DERIVEDNUMBER
),
    IDerivedNumberSymbol {
    override val leftDerived: INumberSymbolID
        get() = (children.getOrNull(0)?.id ?: BaseNumbers.ZERO) as INumberSymbolID
    override val rightDerived: INumberSymbolID
        get() = (children.getOrNull(1)?.id ?: BaseNumbers.ZERO) as INumberSymbolID


    fun add(left:INumberSymbol, right:INumberSymbol) {
        clear()
        add(left)
        add(right)
    }

    override fun add(symbol: INumberSymbol) {
        super.add(symbol)
        symbol.groupid = id
        related[symbol.id] = symbol
        if (children.size == 2) {
            children[0].related[rightDerived] = children[1]
            children[1].related[leftDerived] = children[0]
        }
    }

}
