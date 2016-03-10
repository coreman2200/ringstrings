package com.coreman2200.ringstrings.symbol.numbersymbol;

/**
 * NumberStrata
 * Describes the different tiers for numbers, in the numerological context/sense.
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

public enum NumberStrata {
    BASENUMBER,
    DERIVEDNUMBER,
    GROUPEDNUMBERS,
    CHARTEDNUMBERS,
    RELATIONALNUMBERMAP;

    public static final boolean isNumberSymbol(Enum<? extends Enum<?>> type) {
        return type instanceof NumberStrata;
    }
}