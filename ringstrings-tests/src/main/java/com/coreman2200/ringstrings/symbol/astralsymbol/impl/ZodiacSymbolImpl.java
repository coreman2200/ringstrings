package com.coreman2200.ringstrings.symbol.astralsymbol.impl;

import com.coreman2200.ringstrings.symbol.astralsymbol.AstralStrata;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Zodiac;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IAstralSymbol;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IZodiacSymbol;

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
        mAstralStrata = AstralStrata.ASTRALZODIAC;
    }

    public Collection<IAstralSymbol> getBodySymbolsInSign() { return mGroupedAstralSymbols.values(); }

}
