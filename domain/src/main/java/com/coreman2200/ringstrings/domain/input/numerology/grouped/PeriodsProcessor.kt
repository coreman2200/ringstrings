package com.coreman2200.ringstrings.domain.input.numerology.grouped

import com.coreman2200.ringstrings.domain.NumerologySettings
import com.coreman2200.ringstrings.domain.input.entity.IProfileData
import com.coreman2200.ringstrings.domain.symbol.SymbolModel
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.GroupedNumbers
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.Periods
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.GroupedNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbol
import java.util.ArrayList

/**
 * PeriodsProcessor
 * Processes the Period Cycles in a Number Chart.
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
class PeriodsProcessor(
    profile: IProfileData,
    settings: NumerologySettings
) : GroupedNumberSymbolsInputProcessor(
    profile, settings), IGroupedNumberSymbolsInputProcessor {
    private fun getPeriods(profile: IProfileData): GroupedNumberSymbol {
        val reducedMonth = singularizeValue(profile.birthMonth())
        val reducedDay = singularizeValue(profile.birthDay())
        val reducedYear = singularizeValue(profile.birthYear())
        val periods = GroupedNumberSymbol(GroupedNumbers.PERIODS)
        val periodslist: MutableList<INumberSymbol> = ArrayList<INumberSymbol>(3)
        periodslist.add(convertValueToNumberSymbol(reducedMonth))
        periodslist.add(convertValueToNumberSymbol(reducedDay))
        periodslist.add(convertValueToNumberSymbol(reducedYear))
        val iter: Iterator<INumberSymbol> = periodslist.listIterator()
        for (p in Periods.values()) {
            val symbol: INumberSymbol = iter.next()
            periods.add(p, symbol)
        }
        return periods
    }

    override fun produceGroupedNumberSymbolsForProfile(): GroupedNumberSymbol {
        return getPeriods(profile)
    }
}
