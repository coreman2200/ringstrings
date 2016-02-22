package com.coreman2200.ringstrings.symbol.astralsymbol.impl;

import com.coreman2200.ringstrings.symbol.astralsymbol.AstralStrata;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Houses;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IAstralSymbol;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.ICelestialBodySymbol;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IHouseSymbol;

import java.util.Collection;

/**
 * HouseSymbolImpl
 * Extends GroupedAstralSymbolsImpl to more specifically define HouseSymbols
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

public class HouseSymbolImpl extends GroupedAstralSymbolsImpl implements IHouseSymbol {

    public HouseSymbolImpl(Houses symbolid, double degree) {
        super(symbolid, degree);
    }

    public Collection<IAstralSymbol> getBodySymbolsInHouse() { return getAllSymbols(); }

    @Override
    protected void setSymbolStrata() {
        mSymbolStrata = AstralStrata.ASTRALHOUSE;
    }

    public void addAstralSymbol(Enum<? extends Enum<?>> name, IAstralSymbol symbol) {
        super.addAstralSymbol(name, symbol);
        ICelestialBodySymbol body = (ICelestialBodySymbol)symbol;
        // TODO: Should the body know the parent?
        body.setHouse(this);
    }
}
