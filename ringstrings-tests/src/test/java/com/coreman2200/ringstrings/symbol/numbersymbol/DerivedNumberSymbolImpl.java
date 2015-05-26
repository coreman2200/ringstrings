package com.coreman2200.ringstrings.symbol.numbersymbol;

/**
 * DerivedNumberSymbolImpl
 * Derived Number Symbols are number symbols with two Base Number Symbols
 * from whence they are derived.
 *
 * Created by Cory Higginbottom on 5/25/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class DerivedNumberSymbolImpl extends BaseNumberSymbolImpl implements IDerivedNumberSymbol {
    private enum Derived {
        LEFT,
        RIGHT
    }

    private final BaseNumberSymbols[] derivedSymbols;

    public DerivedNumberSymbolImpl(BaseNumberSymbols symbol, BaseNumberSymbols derived1, BaseNumberSymbols derived2) {
        super(symbol);
        this.numberSymbolID = symbol;
        this.derivedSymbols = new BaseNumberSymbols[] {derived1, derived2};
        this.setDerivedSymbols();
    }

    private void setDerivedSymbols() {
        addSymbolDataForKey(Derived.LEFT, this.derivedSymbols[0]);
        addSymbolDataForKey(Derived.RIGHT, this.derivedSymbols[1]);
    }

    @Override
    public BaseNumberSymbols getFirstDerivedValue() {
        return this.derivedSymbols[0];
    }

    @Override
    public BaseNumberSymbols getSecondDerivedValue() {
        return this.derivedSymbols[1];
    }

    @Override
    protected void setNumberStrata() {
        this.numberSymbolStrata = NumberStrata.DERIVEDNUMBER;
    }
}
