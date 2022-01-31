package com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped

import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.IGroupedNumberSymbolID
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID

/**
 * GroupedNumberSymbols
 * List of general number symbol groupings found in this application. ie Challenges, Pinnacles, etc..
 *
 * Created by Cory Higginbottom on 5/27/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
enum class GroupedNumbers : ISymbolID, IGroupedNumberSymbolID {
    QUALITIES, CHALLENGES, PERIODS, PINNACLES, PERSONAL, KARMICLESSON, HIDDENPASSIONS, CHART, RELATIONAL
}
