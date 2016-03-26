package com.coreman2200.domain.symbol.map.comparator;

import com.coreman2200.domain.symbol.symbolinterface.ISymbol;
import com.coreman2200.domain.symbol.strata.SymbolStrata;

import java.util.Map;

/**
 * SymbolStrataComparatorImpl
 * Compares symbols by their strata.
 *
 * Created by Cory Higginbottom on 6/7/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class SymbolStrataComparatorImpl extends SymbolComparatorImpl<ISymbol> {

    @Override
    public int compare(Map.Entry<Enum<? extends Enum<?>>, ISymbol> o1, Map.Entry<Enum<? extends Enum<?>>, ISymbol> o2) {
        try {
            SymbolStrata o1Strata = SymbolStrata.getSymbolStrataFor(o1.getKey());
            SymbolStrata o2Strata = SymbolStrata.getSymbolStrataFor(o2.getKey());
            return o2Strata.ordinal() - o1Strata.ordinal();
        } catch (NullPointerException e) {
            return -1;
        }
    }
}
