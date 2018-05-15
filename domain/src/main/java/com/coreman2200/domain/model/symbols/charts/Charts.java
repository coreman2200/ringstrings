package com.coreman2200.domain.model.symbols.charts;

import com.coreman2200.domain.model.symbols.strata.AstralStrata;
import com.coreman2200.domain.model.symbols.strata.EntityStrata;
import com.coreman2200.domain.model.symbols.strata.NumberStrata;
import com.coreman2200.domain.model.symbols.interfaces.ISymbolStrata;

/**
 * Charts
 * Describes Astrological and Numerological as (currently?) available chart types
 *
 * Created by Cory Higginbottom on 5/28/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public enum Charts implements ISymbolStrata {
    ASTRAL_NATAL(AstralStrata.class),
    ASTRAL_CURRENT(AstralStrata.class), // TODO: Current to include Numerological symbols? Redef..
    NUMEROLOGICAL(NumberStrata.class),
    NO_CHART(EntityStrata.class);

    final Class<? extends Enum> mGrouping;

    Charts(Class<? extends Enum> grouping) {
        mGrouping = grouping;
    }

    public final Enum[] getGrouping() {
        if (mGrouping == null) {
            return null;
        }
        return mGrouping.getEnumConstants();
    }
}
