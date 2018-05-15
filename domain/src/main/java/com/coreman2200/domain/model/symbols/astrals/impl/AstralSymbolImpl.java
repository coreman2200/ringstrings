package com.coreman2200.domain.model.symbols.astrals.impl;

import com.coreman2200.domain.model.symbols.AbstractSymbol;
import com.coreman2200.domain.model.symbols.strata.SymbolStrata;
import com.coreman2200.domain.model.symbols.strata.AstralStrata;
import com.coreman2200.domain.model.symbols.astrals.interfaces.IAstralSymbol;

/**
 * AstralSymbolImpl
 * Base implementation for Astral Symbols
 *
 * Created by Cory Higginbottom on 6/6/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

abstract public class AstralSymbolImpl extends AbstractSymbol<IAstralSymbol> implements IAstralSymbol {
    protected double mDegree;

    protected AstralSymbolImpl(Enum<? extends Enum<?>> symbolid, double degree) {
        super(symbolid);
        mDegree = degree;

        if (symbolStrata().compareTo(SymbolStrata.RELATED_SYMBOLS) <= 0)
            addSymbolDataForKey(symbolid, this);
    }

    public final double getAstralSymbolDegree() {
        return  mDegree;
    }

    public final AstralStrata getAstralSymbolStrata() {
        return (AstralStrata)mSymbolStrata;
    }

    public Enum<? extends Enum<?>> getAstralSymbolID() {
        assert (mSymbolID != null);
        return mSymbolID;
    }

    @Override
    public void testGenerateLogs() {
        if (symbolStrata().compareTo(SymbolStrata.RELATED_SYMBOLS) <= 0) {
            System.out.print(name());
            System.out.println(" degree: " + mDegree);
        }
        super.testGenerateLogs();
    }

    protected static double wrapDegree(double degree) {
        return (360 + degree) % 360.0;
    }
}
