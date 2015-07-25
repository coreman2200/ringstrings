package com.coreman2200.ringstrings.symbol.astralsymbol.impl;

import com.coreman2200.ringstrings.symbol.IGroupedSymbols;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IAstralSymbol;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IGroupedAstralSymbols;

import java.util.HashMap;

/**
 * GroupedAstralSymbolsImpl
 * Implementation describes grouped astral symbols (houses, zodiacs)..
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

abstract public class GroupedAstralSymbolsImpl extends AstralSymbolImpl implements IGroupedAstralSymbols, IGroupedSymbols {
    protected HashMap<Enum<? extends Enum<?>>, IAstralSymbol> mGroupedAstralSymbols;

    public GroupedAstralSymbolsImpl(Enum<? extends Enum<?>> id, double degree) {
        super(id, degree);
        mGroupedAstralSymbols = new HashMap<>();
    }

    public void addAstralSymbol(Enum<? extends Enum<?>> name, IAstralSymbol symbol) {
        mGroupedAstralSymbols.put(name, symbol);
        System.out.println(name.name() + " in " + mSymbolID.name());
    }

    public IAstralSymbol getAstralSymbol(Enum<? extends Enum<?>> name) {
        return mGroupedAstralSymbols.get(name);
    }

    @Override
    public int size() {
        return mGroupedAstralSymbols.size();
    }
}
