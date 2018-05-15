package com.coreman2200.domain.model.symbols.astrals.interfaces;

import com.coreman2200.domain.model.symbols.astrals.impl.AspectedSymbolsImpl;
import com.coreman2200.domain.model.symbols.interfaces.IRelatedSymbols;

/**
 * IAspectSymbol
 * Interface for aspects
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

public interface IAspectSymbol extends IGroupedAstralSymbols, IRelatedSymbols {
    void setType(AspectedSymbolsImpl.AspectType type);
}
