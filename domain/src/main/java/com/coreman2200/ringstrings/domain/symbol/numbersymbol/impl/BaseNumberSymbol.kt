package com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl

import com.coreman2200.ringstrings.domain.symbol.numbersymbol.NumberStrata
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.BaseNumbers

/**
 * BaseNumberSymbolImpl
 * Base Number implementation maps to value in enum BaseNumberSymbols
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
open class BaseNumberSymbol(
    override val id: BaseNumbers
) : NumberSymbol(
    id,
    name = id.toString(),
    strata = NumberStrata.BASENUMBER,
    value = id.value()
)
