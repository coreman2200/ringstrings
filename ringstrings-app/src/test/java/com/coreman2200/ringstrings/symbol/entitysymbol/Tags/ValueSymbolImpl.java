package com.coreman2200.ringstrings.symbol.entitysymbol.Tags;

import com.coreman2200.ringstrings.symbol.SymbolStrata;
import com.coreman2200.ringstrings.symbol.numbersymbol.NumberStrata;
import com.coreman2200.ringstrings.symbol.symbolinterface.ISymbol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ValueSymbolImpl
 * description
 *
 * Created by Cory Higginbottom on 11/5/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class ValueSymbolImpl implements IValueSymbol {
    private Integer mValue;

    public ValueSymbolImpl(Integer value) {
        mValue = value;
    }

    public void add(Integer val) { mValue += val; }
    public void sub(Integer val) { mValue -= val; }

    public Integer getValue() {
        return mValue;
    }

    public String name() { return Integer.toString((int)mValue);}

    public int size() { return (int)mValue; }

    public SymbolStrata symbolStrata() { return SymbolStrata.SYMBOL; }

    public Enum<? extends Enum<?>> symbolID() { return NumberStrata.BASENUMBER; }

    public final Collection<Enum<? extends Enum<?>>> symbolIDCollection() {
        Collection<Enum<? extends Enum<?>>> symbolIDs = new ArrayList<>();
        symbolIDs.add(symbolID());
        return symbolIDs;
    }

    @Override
    public Collection<TagSymbols> getQualities() {
        Collection<TagSymbols> empty = Collections.EMPTY_SET;
        return  empty;
    }

    @Override
    public int getTagCount(TagSymbols tag) {
        return mValue;
    }

    @Override
    public Enum<? extends Enum<?>> symbolType() {
        return symbolID();
    }

    @Override
    public boolean containsSymbol(ISymbol symbol) {
        return false;
    }

    public final Map<Enum<? extends Enum<?>>, IValueSymbol> produceSymbol() {
        HashMap<Enum<? extends Enum<?>>, IValueSymbol> map = new HashMap<>();
        map.put(NumberStrata.BASENUMBER, this);
        return map;
    }

    public void testGenerateLogs() {  }

}
