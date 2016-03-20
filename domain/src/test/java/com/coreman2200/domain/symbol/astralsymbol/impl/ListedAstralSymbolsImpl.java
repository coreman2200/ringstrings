package com.coreman2200.domain.symbol.astralsymbol.impl;

import com.coreman2200.domain.symbol.astralsymbol.AstralStrata;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.IAstralSymbol;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.IListedAstralSymbols;

/**
 * ListedAstralSymbolsImpl
 * For Astral Symbols that are grouped as a list (aspects, transits) & w/out titled
 * placement in a map.
 *
 * Created by Cory Higginbottom on 5/28/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class ListedAstralSymbolsImpl extends GroupedAstralSymbolsImpl implements IListedAstralSymbols {
    private enum Listed { // Note: It is not possible to have anymore than 9 elems in a list.. TODO: Arbitrariness.
        L0, L1, L2, L3, L4, L5, L6, L7, L8, L9
    }

    public ListedAstralSymbolsImpl(Enum<? extends Enum<?>> symbol) {
        super(symbol, 0);
    }

    public final void addAstralSymbol(IAstralSymbol symbol) {
        assert (size() < 10);
        Listed pos = Listed.values()[size()];
        addAstralSymbol(pos, symbol);
    }

    @Override
    protected void setSymbolStrata() {
        mSymbolStrata = AstralStrata.ASTRALGROUP;
    } // TODO: Hrm..

}
