package com.coreman2200.ringstrings.symbol.entitysymbol.Profile;

import com.coreman2200.ringstrings.rsprovider.dao.ISymbolDAO;
import com.coreman2200.ringstrings.symbol.entitysymbol.Lights.ILightSymbol;
import com.coreman2200.ringstrings.symbol.entitysymbol.Tags.TagSymbols;
import com.coreman2200.ringstrings.symbol.symbolinterface.IEntitySymbol;
import com.coreman2200.ringstrings.symbol.symbolinterface.ISymbol;

import java.util.Collection;
import java.util.Map;

/**
 * IProfileSymbol
 * Symbol representing the full profile symbol map. Can be stored to db.
 *
 * Created by Cory Higginbottom on 11/6/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public interface IProfileSymbol extends ILightSymbol {
    boolean saveProfile();
    ILightSymbol produceLightSymbolFor(ISymbol symbol);
}
