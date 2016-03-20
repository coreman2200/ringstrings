package com.coreman2200.domain.symbol.astralsymbol;

import com.coreman2200.domain.symbol.astralsymbol.grouped.Aspects;
import com.coreman2200.domain.symbol.astralsymbol.grouped.AstralCharts;
import com.coreman2200.domain.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.domain.symbol.astralsymbol.grouped.Houses;
import com.coreman2200.domain.symbol.astralsymbol.grouped.Zodiac;
import com.coreman2200.domain.symbol.symbolinterface.ISymbolStrata;

/**
 * AstralStrata
 * Defining strata for astrological symbols
 *
 * Created by Cory Higginbottom on 5/29/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public enum AstralStrata implements ISymbolStrata {
    ASTRALBODY(CelestialBodies.class),
    ASTRALHOUSE(Houses.class),
    ASTRALZODIAC(Zodiac.class),
    ASTRALASPECT(Aspects.class),
    ASTRALGROUP(null),
    ASTRALCHART(AstralCharts.class),
    RELATIONALASTRALMAP(null);

    final Class<? extends Enum> mGrouping;

    AstralStrata(Class<? extends Enum> grouping) {
        mGrouping = grouping;
    }

    public static final boolean isAstralSymbol(Enum<? extends Enum<?>> type) {
        return type instanceof AstralStrata;
    }

    public final Enum[] getGrouping() {
        if (mGrouping == null) {
            return null;
        }
        return mGrouping.getEnumConstants();
    }
}
