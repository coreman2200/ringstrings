package com.coreman2200.ringstrings.symbol.symbolinterface;

import com.coreman2200.ringstrings.symbol.entitysymbol.EntityStrata;
import com.coreman2200.ringstrings.symbol.entitysymbol.Tags.TagSymbols;

import java.util.Collection;

/**
 * IEntitySymbol
 * Interface for Entity Symbols.
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

public interface IEntitySymbol extends IRelatedSymbolMapping {
    Collection<TagSymbols> getQualities();
    int getTagCount(TagSymbols tag);
}
