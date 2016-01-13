package com.coreman2200.ringstrings.symbol.symbolcomparator;

import com.coreman2200.ringstrings.symbol.entitysymbol.Tags.ITagSymbol;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.INumberSymbol;

import java.util.Comparator;
import java.util.Map;

/**
 * TagSymbolCountComparatorImpl
 * Compares tag symbol count
 *
 * Created by Cory Higginbottom on 5/26/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License atx
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

 public class TagSymbolCountComparatorImpl implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
        return o2.compareTo(o1);
    }

}
