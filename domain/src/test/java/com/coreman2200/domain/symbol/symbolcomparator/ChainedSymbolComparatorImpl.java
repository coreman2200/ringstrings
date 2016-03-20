package com.coreman2200.domain.symbol.symbolcomparator;

import com.coreman2200.domain.symbol.symbolinterface.ISymbol;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ChainedSymbolComparatorImpl
 * Allows for chaining of Comparators for symbols..
 *
 * Created by Cory Higginbottom on 8/24/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class ChainedSymbolComparatorImpl<T extends ISymbol> extends SymbolComparatorImpl<T> {
    final private List<SymbolComparatorImpl<T>> mComparatorList;

    public ChainedSymbolComparatorImpl(List<SymbolComparatorImpl<T>> comparatorList) {
        super();
        mComparatorList = comparatorList;
    }

    public ChainedSymbolComparatorImpl(List<SymbolComparatorImpl<T>> comparatorList, boolean exclusive) {
        super(exclusive);
        mComparatorList = comparatorList;
    }

    public ChainedSymbolComparatorImpl(SymbolComparatorImpl<T>... comparatorList) {
        super();
        mComparatorList = Arrays.asList(comparatorList);
    }

    @Override
    public int compare(Map.Entry<Enum<? extends Enum<?>>, T> o1, Map.Entry<Enum<? extends Enum<?>>, T> o2) {
        for (SymbolComparatorImpl<T> c : mComparatorList) {
            int result = c.compare(o1, o2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

}
