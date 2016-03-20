package com.coreman2200.domain.symbol.numbersymbol.grouped;

import com.coreman2200.domain.symbol.SymbolStrata;
import com.coreman2200.domain.symbol.numbersymbol.NumberStrata;
import com.coreman2200.domain.symbol.numbersymbol.impl.BaseNumberSymbolImpl;
import com.coreman2200.domain.symbol.numbersymbol.interfaces.IBaseNumberSymbol;
import com.coreman2200.domain.symbol.numbersymbol.interfaces.INumberSymbol;
import com.coreman2200.domain.symbol.symbolinterface.ISymbol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

/**
 * BaseNumberSymbol
 * Enum expresses available base(primitive) number symbols within Numerology contexts.
 *
 * Created by Cory Higginbottom on 5/24/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public enum BaseNumberSymbols implements INumberSymbol {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    ELEVEN(11),
    TWENTYTWO(22),
    THIRTYTHREE(33);
    //FOURTYFOUR(44);

    static private final Map<Integer, IBaseNumberSymbol> baseNumbers = Collections.unmodifiableMap(mapBaseNumberSymbols());
    private final int baseValue;
    private final IBaseNumberSymbol numberSymbol;
    private boolean isMasterNumber;

    BaseNumberSymbols(int value) {
        this.baseValue = value;
        this.numberSymbol = new BaseNumberSymbolImpl(this);
        setIsMasterNumber();
    }

    private void setIsMasterNumber() {
        isMasterNumber = (baseValue > 9);
    }

    public boolean getIsMasterNumber() {
        return this.isMasterNumber;
    }

    public IBaseNumberSymbol getBaseNumberSymbol() {
        return this.numberSymbol;
    }

    public int getNumberSymbolValue() {
        return this.baseValue;
    }

    public NumberStrata getNumberSymbolStrata() { return NumberStrata.BASENUMBER; }

    public int size() { return this.getBaseNumberSymbol().size(); }

    public SymbolStrata symbolStrata() { return this.numberSymbol.symbolStrata(); }

    public Enum<? extends Enum<?>> symbolID() { return this; }

    @Override
    public Enum<? extends Enum<?>> symbolType() {
        return getNumberSymbolStrata();
    }

    @Override
    public boolean containsSymbol(ISymbol symbol) {
        return this.equals(symbol);
    }

    public final Collection<Enum<? extends Enum<?>>> symbolIDCollection() {
        Collection<Enum<? extends Enum<?>>> symbolIDs = new ArrayList<>();
        symbolIDs.add(symbolID());
        return symbolIDs;
    }

    public final Map<NumberStrata, INumberSymbol> produceSymbol() {
        HashMap<NumberStrata, INumberSymbol> map = new HashMap<>();
        map.put(getNumberSymbolStrata(), this);
        return map;
    }

    public void testGenerateLogs() { this.numberSymbol.testGenerateLogs(); }

    private static Map<Integer, IBaseNumberSymbol> mapBaseNumberSymbols() {
        Map<Integer, IBaseNumberSymbol> mMap = new HashMap<>();

        for (BaseNumberSymbols symbol : BaseNumberSymbols.values())
            mMap.put(symbol.baseValue, symbol.numberSymbol);

        return mMap;
    }

    static public boolean isValueBaseNumberSymbol(int value) {
        return baseNumbers.containsKey(value);
    }

    static public BaseNumberSymbols getBaseNumberSymbolIDForValue(int value) throws NullPointerException {
        if (!baseNumbers.containsKey(value))
            throw new NullPointerException("No Base Symbol for value '" + value + "' found." );
        return baseNumbers.get(value).getBaseNumberSymbolID();
    }

}
