package com.coreman2200.domain.symbol.astralsymbol.impl;

import com.coreman2200.domain.symbol.astralsymbol.AstralStrata;
import com.coreman2200.domain.symbol.astralsymbol.grouped.Zodiac;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.IAstralSymbol;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.ICelestialBodySymbol;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.IZodiacSymbol;

import java.util.Collection;

/**
 * ZodiacSymbolImpl
 * Extends GroupedAstralSymbolsImpl to more specifically define ZodiacSymbols
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

public class ZodiacSymbolImpl extends GroupedAstralSymbolsImpl implements IZodiacSymbol {

    public ZodiacSymbolImpl(Zodiac symbolid, double degree) {
        super(symbolid, degree);
    }

    public Collection<IAstralSymbol> getBodySymbolsInSign() { return getAllSymbols(); }

    @Override
    protected void setSymbolStrata() {
        mSymbolStrata = AstralStrata.ASTRALZODIAC;
    }

    public void addAstralSymbol(Enum<? extends Enum<?>> name, IAstralSymbol symbol) {
        super.addAstralSymbol(name, symbol);
        ICelestialBodySymbol body = (ICelestialBodySymbol)symbol;
        // TODO: Should the body know the parent?
        body.setSign(this);
    }

}
