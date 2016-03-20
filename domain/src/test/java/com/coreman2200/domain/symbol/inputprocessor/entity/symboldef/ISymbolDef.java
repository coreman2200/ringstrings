package com.coreman2200.domain.symbol.inputprocessor.entity.symboldef;

import com.coreman2200.domain.symbol.entitysymbol.Tags.TagSymbols;

import java.util.Collection;

/**
 * ISymbolDef
 * Describes interactions required for mapping symbols to relevant data/qualities/etc.
 *
 * Created by Cory Higginbottom on 11/4/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public interface ISymbolDef {
    String getName();
    String getDescription();
    Collection<TagSymbols> getQualities();
}
