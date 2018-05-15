package com.coreman2200.domain.model.symbols.astrals.interfaces;

/**
 * IListedNumberSymbols
 * Extends IGroupedNumberSymbols to include 'add' method appropriate for listing.
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

public interface IListedAstralSymbols extends IGroupedAstralSymbols {
    void addAstralSymbol(IAstralSymbol symbol);
}
