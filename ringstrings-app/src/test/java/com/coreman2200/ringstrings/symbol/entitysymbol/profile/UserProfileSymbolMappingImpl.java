package com.coreman2200.ringstrings.symbol.entitysymbol.profile;

import com.coreman2200.ringstrings.profile.IProfileTestLoc;
import com.coreman2200.ringstrings.symbol.entitysymbol.EntityStrata;

/**
 * UserProfileSymbolMappingImpl
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

public class UserProfileSymbolMappingImpl extends ProfileSymbolMappingImpl {

    public UserProfileSymbolMappingImpl(IProfileTestLoc profile) {
        super(EntityStrata.USER, profile);
    }

}
