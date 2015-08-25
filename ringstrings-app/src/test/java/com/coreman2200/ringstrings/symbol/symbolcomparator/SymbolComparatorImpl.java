package com.coreman2200.ringstrings.symbol.symbolcomparator;

import com.coreman2200.ringstrings.symbol.SymbolStrata;
import com.coreman2200.ringstrings.symbol.symbolinterface.ISymbol;

import java.util.Comparator;
import java.util.Map;

/**
 * SymbolComparatorImpl
 * Abstract for comparing symbols symbols.
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

abstract public class SymbolComparatorImpl<T extends ISymbol> implements Comparator<Map.Entry<Enum<? extends Enum<?>>, T>> {
    private boolean mExclusiveCompare;

    public SymbolComparatorImpl() { mExclusiveCompare = false; }

    public SymbolComparatorImpl(boolean exclusive) { mExclusiveCompare = exclusive; }

}
