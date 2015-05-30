package com.coreman2200.ringstrings.symbol.numbersymbol.impl;

import com.coreman2200.ringstrings.symbol.IGroupedSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.GroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.INumberSymbol;
import com.coreman2200.ringstrings.symbol.numbersymbol.NumberStrata;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.IGroupedNumberSymbols;

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

public class GroupedNumberSymbolsImpl extends NumberSymbolImpl implements IGroupedSymbols, IGroupedNumberSymbols {
    protected LinkedHashMap<Enum<? extends Enum<?>>, INumberSymbol> mGroupedNumberSymbolsMap;
    protected final GroupedNumberSymbols mGroupedNumberSymbolID;

    public GroupedNumberSymbolsImpl(GroupedNumberSymbols group) {
        super(0);
        mGroupedNumberSymbolID = group;
        mGroupedNumberSymbolsMap = new LinkedHashMap<>();
    }

    public final void addNumberSymbol(Enum<? extends Enum<?>> name, INumberSymbol symbol) {
        assert (symbol != null);
        assert (name != null);
        mGroupedNumberSymbolsMap.put(name, symbol);
        System.out.println(name.toString() + " added to " + mGroupedNumberSymbolID.toString() + "! value: " + symbol.getNumberSymbolValue());
    }

    @Override
    public final int size() {
        int size = 0;

        for (INumberSymbol symbol : mGroupedNumberSymbolsMap.values())
            size += symbol.size();
        //System.out.println(mGroupedNumberSymbolID.name() + "'s size: " + size);
        return size;
    }

    @Override
    public String name() {
        return mGroupedNumberSymbolID.toString();
    }

    @Override
    public final INumberSymbol getNumberSymbol(Enum<? extends Enum<?>> name) {
        assert (name != null);
        if (!mGroupedNumberSymbolsMap.containsKey(name))
            throw new NullPointerException("Elem does not exist within grouped symbols");
        return mGroupedNumberSymbolsMap.get(name);
    }

    public IGroupedNumberSymbols getGroupedNumberSymbol(Enum<? extends Enum<?>> name) {
        return (IGroupedNumberSymbols)getNumberSymbol(name);
    }

    @Override
    public final GroupedNumberSymbols getGroupID() {
        return this.mGroupedNumberSymbolID;
    }

    @Override
    protected void produceNumberSymbol() {
        super.produceNumberSymbol();
        //System.out.println("New " + numberSymbolStrata.toString() + " produced! ");

    }

    @Override
    protected void setNumberStrata() {
        this.numberSymbolStrata = NumberStrata.GROUPEDNUMBERS;
    }
}
