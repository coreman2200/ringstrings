package com.coreman2200.ringstrings.symbol.numbersymbol;

import java.util.LinkedHashMap;

/**
 * GroupedNumberSymbolsImpl
 * Larger Number Symbol structure that maintain a list of number symbols.
 *
 * Created by Cory Higginbottom on 5/26/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class GroupedNumberSymbolsImpl extends NumberSymbolImpl implements IGroupedNumberSymbols {
    LinkedHashMap<NumberStrata, INumberSymbol> mGroupedNumberSymbolsList;

    protected GroupedNumberSymbolsImpl() { // TODO: How to describe as a value a group
        super(BaseNumberSymbols.getBaseNumberSymbolIDForValue(0).getBaseNumberValue());
        mGroupedNumberSymbolsList = new LinkedHashMap<>();
    }

    @Override
    protected void setNumberStrata() {
        this.numberSymbolStrata = NumberStrata.GROUPEDNUMBERS;
    }
}
