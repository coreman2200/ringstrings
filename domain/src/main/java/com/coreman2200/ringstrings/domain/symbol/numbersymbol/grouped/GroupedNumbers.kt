package com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped

import com.coreman2200.ringstrings.domain.symbol.ISymbolStrata
import com.coreman2200.ringstrings.domain.symbol.SymbolStrata
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
enum class GroupedNumbers(vararg groups: Enum<*>) : ISymbolID, IGroupedNumberSymbolID {
    QUALITIES(*Qualities.values()),
    CHALLENGES(*Challenges.values()),
    PERIODS(*Periods.values()),
    PINNACLES(*Pinnacles.values()),
    PERSONAL(*Personal.values()),
    KARMICLESSON(*DerivedKarmicDebts.values()),
    HIDDENPASSIONS,
    CHART,
    RELATIONAL;

    val relevantSymbolGroup: List<Enum<*>> = listOf(*groups)

    companion object {
        private fun from(type: String?): GroupedNumbers? = values().find { it.toString() == type }

        fun symbolGroup(id: ISymbolStrata): GroupedNumbers {
            return values().find { group ->
                group.relevantSymbolGroup.any { it.toString() == id.toString() }
            } ?: RELATIONAL
        }

        fun realGroup(id: String): IGroupedNumberSymbolID {
            val top = from(id)
            if (top != null)
                return top

            val group = values().find { it ->
                it.relevantSymbolGroup.any { it.toString() == id }
            } ?: RELATIONAL
            return group.relevantSymbolGroup.find { it.toString() == id } as IGroupedNumberSymbolID
        }
    }
}
