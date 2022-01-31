package com.coreman2200.ringstrings.domain.symbol.entitysymbol.interfaces;

import com.coreman2200.ringstrings.domain.symbol.entitysymbol.grouped.TagSymbols;
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ICompositeSymbol;

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

public interface IEntitySymbol extends ICompositeSymbol {
    Collection<TagSymbols> getQualities();
    int getTagCount(TagSymbols tag);
}
