package com.coreman2200.domain.model.symbols.strata;

import com.coreman2200.domain.model.symbols.interfaces.ISymbolStrata;

import java.util.List;
import java.util.Arrays;

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

public enum SymbolStrata implements ISymbolStrata {
    SYMBOL(NumberStrata.BASENUMBER, AstralStrata.ASTRALBODY),
    RELATED_SYMBOLS(NumberStrata.DERIVEDNUMBER,
                    AstralStrata.ASTRALASPECT),
    GROUP(NumberStrata.GROUPEDNUMBERS, AstralStrata.ASTRALGROUP, AstralStrata.ASTRALZODIAC, AstralStrata.ASTRALHOUSE ),
    CHART(NumberStrata.CHARTEDNUMBERS, AstralStrata.ASTRALCHART),
    RELATIONAL_MAP(NumberStrata.RELATIONALNUMBERMAP, AstralStrata.RELATIONALASTRALMAP),
    ENTITY(EntityStrata.TAG, EntityStrata.LIGHT, EntityStrata.RING, EntityStrata.PROFILE, EntityStrata.USER,
            EntityStrata.SOCIAL, EntityStrata.GROUPED, EntityStrata.ALL, EntityStrata.GLOBAL );

    private List<Enum> mStrataTypeList;

    SymbolStrata(Enum... stratas) {
        this.mStrataTypeList = Arrays.asList(stratas);
    }

    @Override
    public Enum[] getGrouping() {
        Enum[] enums = new Enum[mStrataTypeList.size()];
        return mStrataTypeList.toArray(enums);
    }

    static public SymbolStrata getSymbolStrataFor(Enum<? extends Enum<?>> stratatype) {
        if (stratatype.getClass().equals(SymbolStrata.class))
            return (SymbolStrata)stratatype;

        for (SymbolStrata strata : SymbolStrata.values()) {
            if (strata.mStrataTypeList.contains(stratatype)) {
                return strata;
            }
        }

        // TODO: assert (false);
        throw new NullPointerException();
     }

}
