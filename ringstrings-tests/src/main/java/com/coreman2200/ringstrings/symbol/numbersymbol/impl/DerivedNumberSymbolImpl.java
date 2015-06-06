package com.coreman2200.ringstrings.symbol.numbersymbol.impl;

import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.BaseNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.DerivedKarmicDebtSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.NumberStrata;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.IDerivedNumberSymbol;

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

    private HashMap<Derived, Integer> mDerivedSymbols;
    private DerivedKarmicDebtSymbols mKarmicDebtSymbol = DerivedKarmicDebtSymbols.NONE;

    public DerivedNumberSymbolImpl(BaseNumberSymbols symbol, BaseNumberSymbols derived1, BaseNumberSymbols derived2) {
        super(symbol);
        this.numberSymbolID = symbol;
        this.setDerivedSymbols(derived1, derived2);
        this.checkForKarmicDebt();
    }

    private void setDerivedSymbols(BaseNumberSymbols left, BaseNumberSymbols right) {
        mDerivedSymbols = new HashMap<>();
        mDerivedSymbols.put(Derived.LEFTDIGIT, left.getNumberSymbolValue());
        mDerivedSymbols.put(Derived.RIGHTDIGIT, right.getNumberSymbolValue());
        addSymbolDataForKey(Derived.LEFTDIGIT, left.getNumberSymbolValue());
        addSymbolDataForKey(Derived.RIGHTDIGIT, right.getNumberSymbolValue());
        //System.out.println(symbolValue + "(" + derivedSymbols[0].toString() + "+" + derivedSymbols[1].toString() + ")");
    }

    private void checkForKarmicDebt() {
        int derivedDigitsValue = getDerivedSymbolsValue();
        DerivedKarmicDebtSymbols symbol = DerivedKarmicDebtSymbols.getKarmicDebtSymbolForValue(derivedDigitsValue);
        if (!symbol.equals(DerivedKarmicDebtSymbols.NONE))
            setKarmicDebtSymbol(symbol);
    }

    private void setKarmicDebtSymbol(DerivedKarmicDebtSymbols number) {
        mDerivedSymbols.put(Derived.KARMIC_DEBT, number.value());
        addSymbolDataForKey(Derived.KARMIC_DEBT, number.value());
        //System.out.println("Karmic Debt Number found: " + number.toString());
    }

    private int getDerivedSymbolsValue() {
        String leftdigit = String.valueOf(mDerivedSymbols.get(Derived.LEFTDIGIT));
        String rightdigit = String.valueOf(mDerivedSymbols.get(Derived.RIGHTDIGIT));
        return Integer.valueOf(leftdigit+rightdigit);
    }

    public boolean hasKarmicDebtValue() {
        return mKarmicDebtSymbol != DerivedKarmicDebtSymbols.NONE;
    }

    public DerivedKarmicDebtSymbols getKarmicDebt() {
        return this.mKarmicDebtSymbol;
    }

    @Override
    public int size() { return (hasKarmicDebtValue()) ? 4 : 3; }

    @Override
    public BaseNumberSymbols getLeftDigitNumberSymbol() {
        return BaseNumberSymbols.getBaseNumberSymbolIDForValue(mDerivedSymbols.get(Derived.LEFTDIGIT));
    }

    @Override
    public BaseNumberSymbols getRightDigitNumberSymbol() {
        return BaseNumberSymbols.getBaseNumberSymbolIDForValue(mDerivedSymbols.get(Derived.RIGHTDIGIT));
    }

    @Override
    protected void setNumberStrata() {
        this.numberSymbolStrata = NumberStrata.DERIVEDNUMBER;
    }


}
