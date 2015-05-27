package com.coreman2200.ringstrings.symbol.numbersymbol;

/**
 * DerivedNumberSymbols
 * Enum for some derived number symbols of note.
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

public enum DerivedNumberSymbols {
    KARMICDEBT10(10),
    KARMICDEBT13(13),
    KARMICDEBT14(14),
    KARMICDEBT16(16),
    KARMICDEBT19(19);

    private final int derivedValue;

    DerivedNumberSymbols(int value) {
        derivedValue = value;
    }

    static public boolean isValueDerivedNumberSymbol(int value) {
        for (DerivedNumberSymbols symbol : DerivedNumberSymbols.values()) {
            if (symbol.derivedValue == value)
                return true;
        }
        return false;
    }
}
