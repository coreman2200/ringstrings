package com.coreman2200.ringstrings.symbol.numbersymbol.impl;

import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.BaseNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.NumberStrata;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.IBaseNumberSymbol;

/**
 * BaseNumberSymbolImpl
 * Base Number implementation maps to value in enum BaseNumberSymbols
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

public class BaseNumberSymbolImpl extends NumberSymbolImpl implements IBaseNumberSymbol {

    public BaseNumberSymbolImpl(BaseNumberSymbols symbol) {
        super(symbol, symbol.getNumberSymbolValue());
        this.mSymbolID = symbol;
    }

    public final BaseNumberSymbols getBaseNumberSymbolID() {
        return (BaseNumberSymbols)mSymbolID;
    }

    @Override
    protected void setSymbolStrata() {
        this.mSymbolStrata = NumberStrata.BASENUMBER;
    }

}
