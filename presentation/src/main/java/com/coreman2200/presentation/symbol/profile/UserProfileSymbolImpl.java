package com.coreman2200.presentation.symbol.profile;


import com.coreman2200.domain.profiledata.IProfileDataBundle;
import com.coreman2200.presentation.symbol.EntityStrata;
import com.coreman2200.domain.symbol.symbolinterface.IChartedSymbols;

import java.util.Collection;

/**
 * UserProfileSymbolImpl
 * User Entity
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

public class UserProfileSymbolImpl extends AbstractProfileSymbol {

    public UserProfileSymbolImpl(IProfileDataBundle profile, Collection<IChartedSymbols> data) {
        super(profile, data);
    }

    @Override
    protected void setSymbolStrata() {
        mSymbolStrata = EntityStrata.PROFILE;
    }

}
