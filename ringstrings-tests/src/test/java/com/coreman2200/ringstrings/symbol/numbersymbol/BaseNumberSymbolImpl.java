package com.coreman2200.ringstrings.symbol.numbersymbol;

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
    protected BaseNumberSymbols numberSymbolID;

    public BaseNumberSymbolImpl(BaseNumberSymbols symbol) {
        super(symbol.getBaseNumberValue());
        this.numberSymbolID = symbol;
    }

    public final BaseNumberSymbols getBaseNumberSymbolID() {
        return this.numberSymbolID;
    }

    @Override
    protected void setNumberStrata() {
        this.numberSymbolStrata = NumberStrata.BASENUMBER;
    }

}
