package com.coreman2200.domain.symbol.astralsymbol.impl;

import com.coreman2200.domain.symbol.strata.AstralStrata;
import com.coreman2200.domain.symbol.astralsymbol.grouped.Aspects;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.IAspectSymbol;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.IAstralSymbol;

/**
 * AspectedSymbolsImpl
 * Aspects/Transits on Astro Chart.
 *
 * Created by Cory Higginbottom on 6/8/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class AspectedSymbolsImpl extends GroupedAstralSymbolsImpl implements IAspectSymbol {
    public enum AspectType {
        ASPECT,
        TRANSIT
    }

    private AspectType mType;

    public AspectedSymbolsImpl(Aspects id, IAstralSymbol s1, IAstralSymbol s2) {
        super(id, getDegreeBetweenSymbols(id, s1, s2));
        double  degree = Math.abs(s1.getAstralSymbolDegree() - s2.getAstralSymbolDegree());
        //System.out.println(s1.name() + " and " + s2.name() + " aspected in " + id.name() + "(" + degree + ")");
        addSymbolDataForKey(s1.getAstralSymbolID(), s1);
        addSymbolDataForKey(s2.getAstralSymbolID(), s2);
        mType = AspectType.ASPECT;
    }

    private static double getDegreeBetweenSymbols(Aspects aspect, IAstralSymbol s1, IAstralSymbol s2) {
        double deg1 = s1.getAstralSymbolDegree();
        double deg2 = s2.getAstralSymbolDegree();
        double deghalf = aspect.aspectDegree()/2;

        return (deg1 > deg2) ? wrapDegree(deg2 + deghalf) : wrapDegree(deg1 + deghalf);
    }

    @Override
    protected void setSymbolStrata() {
        mSymbolStrata = AstralStrata.ASTRALASPECT;
    }

    public void setType(AspectType type) {
        mType = type;
    }

}
