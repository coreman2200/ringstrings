package com.coreman2200.ringstrings.symbol;

import com.coreman2200.ringstrings.symbol.astralsymbol.AstralStrata;
import com.coreman2200.ringstrings.symbol.numbersymbol.NumberStrata;

import org.omg.CosNaming.NamingContextPackage.NotFoundHelper;

import java.util.EnumMap;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

/**
 * SymbolStrata
 * Describes generic symbol stratas, as to enable cross-symbol type comparisons of scale..
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

public enum SymbolStrata {
    SYMBOL(NumberStrata.BASENUMBER, AstralStrata.ASTRALBODY),
    RELATED_SYMBOLS(NumberStrata.DERIVEDNUMBER, NumberStrata.GROUPEDNUMBERS,
                    AstralStrata.ASTRALZODIAC, AstralStrata.ASTRALHOUSE,
                    AstralStrata.ASTRALASPECT),
    CHART(NumberStrata.CHARTEDNUMBERS, AstralStrata.ASTRALCHART),
    RELATIONAL_MAP(NumberStrata.RELATIONALNUMBERMAP, AstralStrata.RELATIONALASTRALMAP);

    private List<Enum> mStrataTypeList;

    SymbolStrata(Enum... stratas) {
        this.mStrataTypeList = Arrays.asList(stratas);
    }

    public static SymbolStrata getSymbolStrataFor(Enum<? extends Enum<?>> stratatype) {
        for (SymbolStrata strata : SymbolStrata.values()) {
            if (strata.mStrataTypeList.contains(stratatype)) {
                //System.out.println(stratatype.toString() + "'s strata attained: " + strata.toString());
                return strata;
            }
        }

        throw new NullPointerException();
     }

}
