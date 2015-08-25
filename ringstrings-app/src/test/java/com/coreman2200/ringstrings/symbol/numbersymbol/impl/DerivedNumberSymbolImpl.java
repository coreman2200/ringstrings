package com.coreman2200.ringstrings.symbol.numbersymbol.impl;

import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.BaseNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.DerivedKarmicDebtSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.NumberStrata;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.IDerivedNumberSymbol;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.INumberSymbol;

import java.util.HashMap;

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
        LEFTDIGIT,
        RIGHTDIGIT,
        KARMIC_DEBT
    }

    //private HashMap<Derived, Integer> mDerivedSymbols;
    private DerivedKarmicDebtSymbols mKarmicDebtSymbol = DerivedKarmicDebtSymbols.NONE;

    public DerivedNumberSymbolImpl(BaseNumberSymbols symbol, BaseNumberSymbols derived1, BaseNumberSymbols derived2) {
        super(symbol);
        this.mSymbolID = symbol;
        this.setDerivedSymbols(derived1, derived2);
        this.checkForKarmicDebt();
    }

    private void setDerivedSymbols(BaseNumberSymbols left, BaseNumberSymbols right) {
        addSymbolDataForKey(Derived.LEFTDIGIT, left);
        addSymbolDataForKey(Derived.RIGHTDIGIT, right);
        //System.out.println(symbolValue + "(" + derivedSymbols[0].toString() + "+" + derivedSymbols[1].toString() + ")");
    }

    private void checkForKarmicDebt() {
        int derivedDigitsValue = getDerivedSymbolsValue();
        DerivedKarmicDebtSymbols symbol = DerivedKarmicDebtSymbols.getKarmicDebtSymbolForValue(derivedDigitsValue);
        if (!symbol.equals(DerivedKarmicDebtSymbols.NONE))
            setKarmicDebtSymbol(symbol);
    }

    private void setKarmicDebtSymbol(DerivedKarmicDebtSymbols number) {
        mKarmicDebtSymbol = number;
        // TODO: Give BaseNumberSymbols treatment
        //addSymbolDataForKey(Derived.KARMIC_DEBT, BaseNumberSymbols.getBaseNumberSymbolIDForValue(number.value()) );
        //System.out.println("Karmic Debt Number found: " + number.toString());
    }

    private int getDerivedSymbolsValue() {
        String leftdigit = String.valueOf(getSymbolDataForKey(Derived.LEFTDIGIT).getNumberSymbolValue());
        String rightdigit = String.valueOf(getSymbolDataForKey(Derived.RIGHTDIGIT).getNumberSymbolValue());
        return Integer.valueOf(leftdigit+rightdigit);
    }

    public boolean hasKarmicDebtValue() {
        return mKarmicDebtSymbol != DerivedKarmicDebtSymbols.NONE;
    }

    public DerivedKarmicDebtSymbols getKarmicDebt() {
        return this.mKarmicDebtSymbol;
    }

    @Override
    public INumberSymbol getLeftDigitNumberSymbol() {
        return getSymbolDataForKey(Derived.LEFTDIGIT);
    }

    @Override
    public INumberSymbol getRightDigitNumberSymbol() {
        return getSymbolDataForKey(Derived.RIGHTDIGIT);
    }

    @Override
    protected void setSymbolStrata() {
        this.mSymbolStrata = NumberStrata.DERIVEDNUMBER;
    }


}
