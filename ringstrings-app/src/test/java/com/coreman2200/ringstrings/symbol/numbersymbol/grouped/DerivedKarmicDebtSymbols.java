package com.coreman2200.ringstrings.symbol.numbersymbol.grouped;

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

public enum DerivedKarmicDebtSymbols {
    KARMICDEBT10(10),
    KARMICDEBT13(13),
    KARMICDEBT14(14),
    KARMICDEBT16(16),
    KARMICDEBT19(19),
    NONE(0);

    private final int derivedValue;

    DerivedKarmicDebtSymbols(int value) {
        derivedValue = value;
    }

    public int value() {
        return derivedValue;
    }

    static public DerivedKarmicDebtSymbols getKarmicDebtSymbolForValue(int value) {
        for (DerivedKarmicDebtSymbols symbol : DerivedKarmicDebtSymbols.values()) {
            if (symbol.derivedValue == value)
                return symbol;
        }
        return NONE;
    }
}
