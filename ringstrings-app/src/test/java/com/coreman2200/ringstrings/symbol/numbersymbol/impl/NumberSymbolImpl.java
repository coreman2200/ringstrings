package com.coreman2200.ringstrings.symbol.numbersymbol.impl;

import com.coreman2200.ringstrings.symbol.AbstractSymbol;
import com.coreman2200.ringstrings.symbol.SymbolStrata;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.BaseNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.INumberSymbol;
import com.coreman2200.ringstrings.symbol.numbersymbol.NumberStrata;

/**
 * NumberSymbolImpl
 * Implements ISymbol. Symbol elements relevant to Numerological aspects of the app.
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

abstract public class NumberSymbolImpl extends AbstractSymbol<INumberSymbol> implements INumberSymbol {
    protected int symbolValue;

    protected NumberSymbolImpl(Enum<? extends Enum<?>> id, int value) {
        super(id);
        this.symbolValue = value;

        if (symbolStrata().compareTo(SymbolStrata.RELATED_SYMBOLS) <= 0)
            addSymbolDataForKey(id, this);
    }

    public final NumberStrata getNumberSymbolStrata() {
        return (NumberStrata)mSymbolStrata;
    }

    public int getNumberSymbolValue() {
        return symbolValue;
    }

    public String name() {
        return Integer.toString(symbolValue);
    }

    @Override
    public void testGenerateLogs() {
        if (symbolStrata().compareTo(SymbolStrata.RELATED_SYMBOLS) <= 0) {
            System.out.print(name().toLowerCase());
            System.out.println(" value: " + symbolValue);
        }
        super.testGenerateLogs();
    }

}
