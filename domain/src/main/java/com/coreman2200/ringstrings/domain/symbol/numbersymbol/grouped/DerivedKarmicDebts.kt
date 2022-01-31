package com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped

import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbolID
import java.util.*

/**
 * DerivedKarmicDebtSymbols
 * Enum for some derived number symbols of note (karmic debts)
 *
 * Created by Cory Higginbottom on 5/26/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
enum class DerivedKarmicDebts(val derivedSymbolsValue: Int) : INumberSymbolID {
    KARMICDEBT10(10), KARMICDEBT13(13),
    KARMICDEBT14(14), KARMICDEBT16(16),
    KARMICDEBT19(19), NONE(0);

    override fun value(): Int {
        return derivedSymbolsValue
    }
}
