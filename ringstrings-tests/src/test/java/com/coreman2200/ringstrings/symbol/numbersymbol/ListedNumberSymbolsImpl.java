package com.coreman2200.ringstrings.symbol.numbersymbol;

/**
 * ListedNumberSymbolsImpl
 * For Number Symbols that are grouped as a list (karmic lessons, hidden passions..) & w/out titled
 * placement in a map.
 *
 * Created by Cory Higginbottom on 5/28/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class ListedNumberSymbolsImpl extends GroupedNumberSymbolsImpl implements IListedNumberSymbols {
    private enum Listed { // Note: It is not possible to have anymore than 9 elems in a list..
        L0, L1, L2, L3, L4, L5, L6, L7, L8, L9
    }

    public ListedNumberSymbolsImpl(GroupedNumberSymbols symbol) {
        super(symbol);
    }

    public final void addNumberSymbol(INumberSymbol symbol) {
        assert (mGroupedNumberSymbolsMap.size() < 10);
        Listed pos = Listed.values()[mGroupedNumberSymbolsMap.size()];
        addNumberSymbol(pos, symbol);
    }

}
