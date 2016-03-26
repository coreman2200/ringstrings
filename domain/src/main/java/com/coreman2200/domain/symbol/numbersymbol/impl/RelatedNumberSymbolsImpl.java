package com.coreman2200.domain.symbol.numbersymbol.impl;

import com.coreman2200.domain.symbol.map.RelatedSymbolMap;
import com.coreman2200.domain.symbol.numbersymbol.grouped.GroupedNumberSymbols;
import com.coreman2200.domain.symbol.numbersymbol.interfaces.INumberSymbol;
import com.coreman2200.domain.symbol.numbersymbol.interfaces.IRelatedNumberSymbols;
import com.coreman2200.domain.symbol.numbersymbol.NumberStrata;

/**
 * RelatedNumberSymbolsImpl
 * Related Number Symbols are lists of numbers compared by comparator.
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

public class RelatedNumberSymbolsImpl extends NumberSymbolImpl implements IRelatedNumberSymbols {
    protected RelatedSymbolMap<INumberSymbol> mRelatedNumberSymbolMap;

    public RelatedNumberSymbolsImpl() {
        super(GroupedNumberSymbols.RELATIONAL, 0);
        mRelatedNumberSymbolMap = new RelatedSymbolMap<>();
    }

    @Override
    protected void setSymbolStrata() {
        this.mSymbolStrata = NumberStrata.RELATIONALNUMBERMAP;
    }
}
