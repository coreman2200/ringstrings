package com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped

import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbolID

/**
 * BaseNumberSymbol
 * Enum expresses available base(primitive) number symbols within Numerology contexts.
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
enum class BaseNumbers(
    private val baseValue: Int
) : INumberSymbolID {
    ZERO(0), ONE(1), TWO(2), THREE(3),
    FOUR(4), FIVE(5), SIX(6), SEVEN(7),
    EIGHT(8), NINE(9),
    ELEVEN(11), TWENTYTWO(22), THIRTYTHREE(33);

    override fun isMasterNumber(): Boolean = baseValue > 9
    override fun value(): Int {
        return baseValue
    }
}
