package com.coreman2200.domain.model.symbols.numbers.grouped;

import com.coreman2200.domain.model.symbols.strata.SymbolStrata;
import com.coreman2200.domain.model.symbols.strata.NumberStrata;
import com.coreman2200.domain.model.symbols.numbers.interfaces.IDerivedNumberSymbol;
import com.coreman2200.domain.model.symbols.numbers.interfaces.INumberSymbol;
import com.coreman2200.domain.model.symbols.interfaces.ISymbol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * DerivedKarmicDebtSymbols
 * Enum for some derived number symbols of note (karmic debts)
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

public enum DerivedKarmicDebtSymbols implements IDerivedNumberSymbol {
    KARMICDEBT10(10),
    KARMICDEBT13(13),
    KARMICDEBT14(14),
    KARMICDEBT16(16),
    KARMICDEBT19(19),
    NONE(0);

    private static final Map<Integer, DerivedKarmicDebtSymbols> mMappedSymbols = Collections.unmodifiableMap(initializeMapping());
    private final int derivedValue;

    DerivedKarmicDebtSymbols(int value) {
        derivedValue = value;
    }

    private static Map<Integer, DerivedKarmicDebtSymbols> initializeMapping() {
        Map<Integer, DerivedKarmicDebtSymbols> mMap = new HashMap<>();

        for (DerivedKarmicDebtSymbols symbol : DerivedKarmicDebtSymbols.values()) {
            mMap.put(symbol.derivedValue, symbol);
        }

        return mMap;
    }

    public int value() {
        return derivedValue;
    }

    static public DerivedKarmicDebtSymbols getKarmicDebtSymbolForValue(int value) {
        DerivedKarmicDebtSymbols symbol = mMappedSymbols.get(value);
        return (symbol == null) ? NONE : symbol;
    }

    public int getNumberSymbolValue() {
        return this.derivedValue;
    }

    public NumberStrata getNumberSymbolStrata() { return NumberStrata.DERIVEDNUMBER; }

    public int size() { return 3; }

    public SymbolStrata symbolStrata() { return SymbolStrata.RELATED_SYMBOLS; }

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

    @Override
    public int getDerivedSymbolsValue() {
        return derivedValue;
    }

    @Override
    public DerivedKarmicDebtSymbols getKarmicDebt() {
        return this;
    }

    @Override
    public INumberSymbol getLeftDigitNumberSymbol() {
        int digit = Integer.valueOf(String.valueOf(derivedValue).substring(0,0));
        return BaseNumberSymbols.getBaseNumberSymbolIDForValue(digit);
    }

    @Override
    public INumberSymbol getRightDigitNumberSymbol() {
        int digit = Integer.valueOf(String.valueOf(derivedValue).substring(1,1));
        return BaseNumberSymbols.getBaseNumberSymbolIDForValue(digit);
    }

    @Override
    public void testGenerateLogs() { }
}
