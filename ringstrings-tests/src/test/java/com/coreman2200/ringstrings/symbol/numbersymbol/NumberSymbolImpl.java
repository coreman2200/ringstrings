package com.coreman2200.ringstrings.symbol.numbersymbol;

import com.coreman2200.ringstrings.symbol.AbstractSymbol;
import com.coreman2200.ringstrings.symbol.SymbolStrata;

import org.robolectric.util.Logger;

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

public class NumberSymbolImpl extends AbstractSymbol implements INumberSymbol {
    protected int symbolValue;
    protected NumberStrata numberSymbolStrata;

    protected NumberSymbolImpl(int value) {
        super();
        this.symbolValue = value;
        setNumberStrata();
        produceNumberSymbol();
    }

    protected void setNumberStrata() throws RuntimeException {
        throw new RuntimeException("Must Override.");
    }

    public NumberStrata getNumberSymbolStrata() {
        return numberSymbolStrata;
    }

    public int getNumberSymbolValue() {
        return symbolValue;
    }

    public String name() {
        return numberSymbolStrata.toString() + symbolValue;
    }

    public int size() { throw new NoClassDefFoundError("Must be overridden");}

    protected void produceNumberSymbol() {
        addSymbolDataForKey(numberSymbolStrata, symbolValue);
    }

}
