package com.coreman2200.ringstrings.symbol.symbolcomparator;

import com.coreman2200.ringstrings.symbol.entitysymbol.Tags.IValueSymbol;
import com.coreman2200.ringstrings.symbol.symbolinterface.ISymbol;

import java.util.Map;

/**
 * SymbolSizeComparatorImpl
 * Compares symbols by their size.
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

public class ValueSymbolComparatorImpl extends SymbolComparatorImpl<IValueSymbol> {

    @Override
    public int compare(Map.Entry<Enum<? extends Enum<?>>, IValueSymbol> o1, Map.Entry<Enum<? extends Enum<?>>, IValueSymbol> o2) {
        int res = o2.getValue().getValue().compareTo(o1.getValue().getValue());
        return res != 0 ? res : 1;
    }
}
