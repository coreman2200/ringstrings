package com.coreman2200.ringstrings.domain.input.numerology.grouped

import com.coreman2200.ringstrings.domain.input.numerology.INumberSymbolInputProcessor
import com.coreman2200.ringstrings.domain.swisseph.IProfileData
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.GroupedNumberSymbol

/**
 * IGroupedNumberSymbolsInputProcessor
 * Interface for processing Grouped Number Symbols.
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
interface IGroupedNumberSymbolsInputProcessor : INumberSymbolInputProcessor {
    fun produceGroupedNumberSymbolsForProfile(): GroupedNumberSymbol
}
