package com.coreman2200.ringstrings.symbol.astralsymbol.impl;

import com.coreman2200.ringstrings.symbol.AbstractSymbol;
import com.coreman2200.ringstrings.symbol.ISymbol;
import com.coreman2200.ringstrings.symbol.astralsymbol.AstralStrata;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IAstralSymbol;

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

abstract public class AstralSymbolImpl extends AbstractSymbol implements IAstralSymbol, ISymbol {
    protected Enum<? extends Enum<?>> mSymbolID;
    protected AstralStrata mAstralStrata;
    protected double mDegree;

    protected AstralSymbolImpl(Enum<? extends Enum<?>> symbolid, double degree) {
        mSymbolID = symbolid;
        mDegree = degree;
    }

    public final double getAstralSymbolDegree() {
        return  mDegree;
    }

    public AstralStrata getAstralSymbolStrata() {
        return mAstralStrata;
    }

    public Enum<? extends Enum<?>> getAstralSymbolID() {
        assert (mSymbolID != null);
        return mSymbolID;
    }

    public String name() {
        return mSymbolID.toString();
    }

    public int size() {
        return 1;
    }

    protected static double wrapDegree(double degree) {
        return (360 + degree) % 360.0;
    }
}
