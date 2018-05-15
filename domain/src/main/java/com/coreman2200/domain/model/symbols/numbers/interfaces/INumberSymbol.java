package com.coreman2200.domain.model.symbols.numbers.interfaces;

import com.coreman2200.domain.model.symbols.interfaces.ISymbol;
import com.coreman2200.domain.model.symbols.strata.NumberStrata;

/**
 * INumberSymbol
 * description
 *
 * Created by Cory Higginbottom on 5/25/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public interface INumberSymbol extends ISymbol {
    NumberStrata getNumberSymbolStrata();
    int getNumberSymbolValue();

}
