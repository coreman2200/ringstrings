package com.coreman2200.domain.symbol.symbolcomparator;

import com.coreman2200.domain.symbol.astralsymbol.AstralStrata;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.IAstralSymbol;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.ICelestialBodySymbol;

import java.util.Map;

/**
 * AstralHouseComparatorImpl
 * Produces list of astral symbols ordered by house
 *
 * Created by Cory Higginbottom on 6/7/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class AstralHouseComparatorImpl extends SymbolComparatorImpl<IAstralSymbol> {

    @Override
    public int compare(Map.Entry<Enum<? extends Enum<?>>, IAstralSymbol> o1, Map.Entry<Enum<? extends Enum<?>>, IAstralSymbol> o2) {
        IAstralSymbol o1symbol = o1.getValue();
        AstralStrata o1strata = o1symbol.getAstralSymbolStrata();
        IAstralSymbol o2symbol = o2.getValue();
        AstralStrata o2strata = o2symbol.getAstralSymbolStrata();
        if (o1strata.compareTo(o2strata) == 0 && o1strata.compareTo(AstralStrata.ASTRALBODY) == 0) {
            int ord1 = ((ICelestialBodySymbol)o1symbol).getHouse().getAstralSymbolID().ordinal();
            int ord2 = ((ICelestialBodySymbol)o1symbol).getHouse().getAstralSymbolID().ordinal();
            return ord2 - ord1;
        } else
            return 0;
    }
}
