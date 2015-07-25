package com.coreman2200.ringstrings.symbol.symbolcomparator;

import java.util.Comparator;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.INumberSymbol;

/**
 * NumberSymbolValueComparatorImpl
 * Compares number symbol values
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

 public class NumberSymbolValueComparatorImpl implements Comparator<INumberSymbol> {

    @Override
    public int compare(INumberSymbol o1, INumberSymbol o2) {
        return o2.getNumberSymbolValue() - o1.getNumberSymbolValue();
    }

}
