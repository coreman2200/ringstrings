package com.coreman2200.ringstrings.symbol.numbersymbol.interfaces;

import com.coreman2200.ringstrings.symbol.symbolinterface.IGroupedSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.GroupedNumberSymbols;

/**
 * IGroupedNumberSymbol
 * Interface for grouped symbols.
 *
 * Created by Cory Higginbottom on 5/26/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public interface IGroupedNumberSymbols extends IGroupedSymbols, INumberSymbol {
    void addNumberSymbol(Enum<? extends Enum<?>> name, INumberSymbol symbol);
    INumberSymbol getNumberSymbol(Enum<? extends Enum<?>> name);
    IGroupedNumberSymbols getGroupedNumberSymbol(Enum<? extends Enum<?>> name);
}
