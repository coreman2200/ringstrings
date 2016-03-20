package com.coreman2200.domain.symbol.entitysymbol.Profile;


import com.coreman2200.data.entity.profiledata.IProfileDataBundle;
import com.coreman2200.domain.symbol.symbolinterface.IChartedSymbols;

import java.util.Collection;

/**
 * LocalProfileSymbolImpl
 * Local Entity
 *
 * Created by Cory Higginbottom on 6/10/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class LocalProfileSymbolImpl extends AbstractProfileSymbol {

    public LocalProfileSymbolImpl(IProfileDataBundle profile, Collection<IChartedSymbols> data) {
        super(profile, data);
    }

}
