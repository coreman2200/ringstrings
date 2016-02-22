package com.coreman2200.ringstrings.symbol.numbersymbol.impl;

import com.coreman2200.ringstrings.symbol.symbolinterface.IGroupedSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.GroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.INumberSymbol;
import com.coreman2200.ringstrings.symbol.numbersymbol.NumberStrata;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.IGroupedNumberSymbols;

import java.util.Collection;
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

    public GroupedNumberSymbolsImpl(GroupedNumberSymbols group) {
        super(group, 0);
    }

    public final void addNumberSymbol(Enum<? extends Enum<?>> name, INumberSymbol symbol) {
        assert (symbol != null);
        assert (name != null);
        addSymbolDataForKey(name, symbol);
    }

    @Override
    public String name() {
        return mSymbolID.toString();
    }

    @Override
    public final INumberSymbol getNumberSymbol(Enum<? extends Enum<?>> name) {
        return getSymbolDataForKey(name);
    }

    public IGroupedNumberSymbols getGroupedNumberSymbol(Enum<? extends Enum<?>> name) {
        return (IGroupedNumberSymbols)getNumberSymbol(name);
    }

    @Override
    protected void setSymbolStrata() {
        this.mSymbolStrata = NumberStrata.GROUPEDNUMBERS;
    }

}
