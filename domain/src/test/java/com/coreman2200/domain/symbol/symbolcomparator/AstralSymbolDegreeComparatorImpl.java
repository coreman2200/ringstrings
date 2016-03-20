package com.coreman2200.domain.symbol.symbolcomparator;

import com.coreman2200.domain.symbol.astralsymbol.interfaces.IAstralSymbol;

import java.util.Map;

/**
 * AstralSymbolDegreeComparatorImpl
 * Compares astrological symbols by their degree in chart.
 *
 * Created by Cory Higginbottom on 6/7/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class AstralSymbolDegreeComparatorImpl extends SymbolComparatorImpl<IAstralSymbol> {
    private double mOffset;

    public AstralSymbolDegreeComparatorImpl(double offset) {
        mOffset = offset;
    }

    @Override
    public int compare(Map.Entry<Enum<? extends Enum<?>>, IAstralSymbol> o1, Map.Entry<Enum<? extends Enum<?>>, IAstralSymbol> o2) {
        return Math.round((float)(o2.getValue().getAstralSymbolDegree() - o1.getValue().getAstralSymbolDegree()));
    }
}
