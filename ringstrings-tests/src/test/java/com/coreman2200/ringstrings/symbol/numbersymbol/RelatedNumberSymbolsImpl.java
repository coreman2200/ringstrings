package com.coreman2200.ringstrings.symbol.numbersymbol;

import com.coreman2200.ringstrings.symbol.RelatedSymbolMap;
import com.coreman2200.ringstrings.symbol.symbolcomparator.NumberSymbolValueComparatorImpl;

/**
 * RelatedNumberSymbolsImpl
 * TODO: Stubb.
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
    protected NumberSymbolValueComparatorImpl<INumberSymbol> mNumberSymbolComparator;
    protected RelatedSymbolMap<NumberStrata, IGroupedNumberSymbols> mRelatedNumberSymbolMap;

    protected RelatedNumberSymbolsImpl() {
        super(0);
        mRelatedNumberSymbolMap = new RelatedSymbolMap<>();
    }

    @Override
    protected void setNumberStrata() {
        this.numberSymbolStrata = NumberStrata.GROUPEDNUMBERS;
    }
}
