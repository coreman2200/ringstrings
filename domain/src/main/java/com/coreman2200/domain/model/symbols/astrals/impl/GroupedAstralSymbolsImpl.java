package com.coreman2200.domain.model.symbols.astrals.impl;

import com.coreman2200.domain.model.symbols.astrals.interfaces.IAstralSymbol;
import com.coreman2200.domain.model.symbols.astrals.interfaces.IGroupedAstralSymbols;

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

abstract public class GroupedAstralSymbolsImpl extends AstralSymbolImpl implements IGroupedAstralSymbols {

    public GroupedAstralSymbolsImpl(Enum<? extends Enum<?>> id, double degree) {
        super(id, degree);
    }

    public IAstralSymbol getAstralSymbol(Enum<? extends Enum<?>> name) {
        return getSymbolDataForKey(name);
    }

    public void addAstralSymbol(Enum<? extends Enum<?>> name, IAstralSymbol symbol) {
        addSymbolDataForKey(name, symbol);
    }
}
