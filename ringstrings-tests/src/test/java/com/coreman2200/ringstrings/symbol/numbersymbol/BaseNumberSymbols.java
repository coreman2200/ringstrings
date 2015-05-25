package com.coreman2200.ringstrings.symbol.numbersymbol;

import com.coreman2200.ringstrings.symbol.ISymbol;
import java.util.HashMap;

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

public enum BaseNumberSymbols {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    ELEVEN(11),
    TWENTY_TWO(22),
    THIRTY_THREE(33),
    FOURTY_FOUR(44);

    static HashMap<Integer, BaseNumberSymbols> baseNumbers = new HashMap<>();
    int baseValue;
    NumberSymbolImpl numberSymbol;

    private BaseNumberSymbols(int value) {
        baseValue = value;
        mapBaseNumberSymbol();
    }

    private void mapBaseNumberSymbol() {
        numberSymbol = new NumberSymbolImpl(this);
        baseNumbers.putIfAbsent(baseValue, this);
    }

    static public BaseNumberSymbols getBaseSymbolForNumber(int value) throws NullPointerException {
        if (!baseNumbers.containsKey(value))
            throw new NullPointerException("No Base Symbol for value '" + value + "' found." );
        return baseNumbers.get(value);
    }

    public int getValue() {
        return this.baseValue;
    }

}
