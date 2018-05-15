package com.coreman2200.domain.model.symbols.maps.comparator;

import com.coreman2200.domain.model.symbols.interfaces.ISymbol;

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

public class SymbolSizeComparatorImpl extends SymbolComparatorImpl<ISymbol> {

    @Override
    public int compare(Map.Entry<Enum<? extends Enum<?>>, ISymbol> o1, Map.Entry<Enum<? extends Enum<?>>, ISymbol> o2) {
           return o2.getValue().size() - o1.getValue().size();
    }
}
