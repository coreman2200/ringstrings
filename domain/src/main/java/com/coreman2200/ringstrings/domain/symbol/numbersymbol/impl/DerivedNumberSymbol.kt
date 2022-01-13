package com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl

import com.coreman2200.ringstrings.domain.symbol.CompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.NumberStrata
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.BaseNumbers
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.IDerivedNumberSymbol
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
    override val leftDerived: BaseNumbers,
    override val rightDerived: BaseNumbers,
    override val value: Int = id.value()
) : CompositeSymbol<NumberSymbol>(
    id,
    strata = NumberStrata.DERIVEDNUMBER,
    size = 3,
),
    IDerivedNumberSymbol {

    init {
        add(BaseNumberSymbol(leftDerived))
        add(BaseNumberSymbol(rightDerived))
    }
}
