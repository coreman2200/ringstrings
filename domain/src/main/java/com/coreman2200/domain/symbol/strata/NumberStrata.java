package com.coreman2200.domain.symbol.strata;

import com.coreman2200.domain.symbol.numbersymbol.grouped.BaseNumberSymbols;
import com.coreman2200.domain.symbol.numbersymbol.grouped.DerivedKarmicDebtSymbols;
import com.coreman2200.domain.symbol.numbersymbol.grouped.GroupedNumberSymbols;
import com.coreman2200.domain.symbol.symbolinterface.ISymbolStrata;

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

public enum NumberStrata implements ISymbolStrata {
    BASENUMBER(BaseNumberSymbols.class),
    DERIVEDNUMBER(DerivedKarmicDebtSymbols.class),
    GROUPEDNUMBERS(GroupedNumberSymbols.class),
    CHARTEDNUMBERS(GroupedNumberSymbols.class),
    RELATIONALNUMBERMAP(null);

    final Class<? extends Enum> mGrouping;

    NumberStrata(Class<? extends Enum> grouping) {
        mGrouping = grouping;
    }


    public static final boolean isNumberSymbol(Enum<? extends Enum<?>> type) {
        return type instanceof NumberStrata;
    }

    @Override
    public final Enum[] getGrouping() {
        if (mGrouping == null) {
            return null;
        }
        return mGrouping.getEnumConstants();
    }
}