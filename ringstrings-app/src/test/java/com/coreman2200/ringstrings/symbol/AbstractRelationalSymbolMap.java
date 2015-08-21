package com.coreman2200.ringstrings.symbol;

import com.coreman2200.ringstrings.symbol.symbolcomparator.SymbolComparatorImpl;
import com.coreman2200.ringstrings.symbol.symbolcomparator.SymbolSizeComparatorImpl;

import java.util.Collection;
import java.util.LinkedList;

/**
 * AbstractRelationalSymbolMap
 * Abstract for relational maps of chart symbols
 *
 * Created by Cory Higginbottom on 6/10/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

abstract public class AbstractRelationalSymbolMap<T> extends AbstractSymbol implements IRelationalSymbolMap {
    private RelatedSymbolMap<T> mChartedSymbolMaps;

    protected AbstractRelationalSymbolMap() {
        super();
        mChartedSymbolMaps = new RelatedSymbolMap<>(new SymbolSizeComparatorImpl());
    }

    protected void addSymbolMap(Enum<? extends Enum<?>> key, T symbolmap) {
        mChartedSymbolMaps.put(key, symbolmap);
    }

    protected T getSymbolMap(Enum<? extends Enum<?>> key) {
        return mChartedSymbolMaps.get(key);
    }

    protected void setSymbolComparator(SymbolComparatorImpl<T> comparator) {
        mChartedSymbolMaps.setCurrentComparator(comparator);
    }

    protected Collection<T> getSortedSymbols(RelatedSymbolMap.SortOrder order) {
        return mChartedSymbolMaps.getSortedSymbols(order);
    }
}
