package com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces

import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.BaseNumbers
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.DerivedKarmicDebts
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID

/**
 * INumberSymbolID
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

interface INumberSymbolID : ISymbolID {
    fun value(): Int

    companion object {
        private val karmicDebts = DerivedKarmicDebts.values().associateBy { it.value() }
        private val baseNumbers = BaseNumbers.values().associateBy { it.value() }
        fun id(value: Int): INumberSymbolID = karmicDebts[value] ?: baseNumbers[value] ?: BaseNumbers.ZERO
    }
}

interface IGroupedNumberSymbolID : ISymbolID
