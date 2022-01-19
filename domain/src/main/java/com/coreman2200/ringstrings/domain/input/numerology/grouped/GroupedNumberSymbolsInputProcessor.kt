package com.coreman2200.ringstrings.domain.input.numerology.grouped

import com.coreman2200.ringstrings.domain.NumerologySettings
import com.coreman2200.ringstrings.domain.input.numerology.NumberSymbolInputProcessor
import com.coreman2200.ringstrings.domain.input.entity.IProfileData
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.GroupedNumbers
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.GroupedNumberSymbol
import java.lang.RuntimeException

/**
 * GroupedNumberSymbolsInputProcessor
 * Base class for grouped number symbol input processors used to produce a chart.
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
abstract class GroupedNumberSymbolsInputProcessor(
    val profile: IProfileData,
    settings: NumerologySettings) :
    NumberSymbolInputProcessor(settings), IGroupedNumberSymbolsInputProcessor {

    abstract override fun produceGroupedNumberSymbolsForProfile(): GroupedNumberSymbol

    companion object {
        // QUALITIES, CHALLENGES, PERIODS, PINNACLES, PERSONAL, KARMICLESSON, HIDDENPASSIONS, CHART, RELATIONAL
        fun getProcessor(type: GroupedNumbers, profile: IProfileData, settings: NumerologySettings): GroupedNumberSymbolsInputProcessor {
            return when (type) {
                GroupedNumbers.QUALITIES -> QualitiesProcessor(profile,settings)
                GroupedNumbers.CHALLENGES -> ChallengesProcessor(profile, settings)
                GroupedNumbers.PERIODS -> PeriodsProcessor(profile, settings)
                GroupedNumbers.PERSONAL -> PersonalsProcessor(profile, settings)
                GroupedNumbers.PINNACLES -> PinnaclesProcessor(profile, settings)
                else -> throw RuntimeException("Improper GroupedSymbol type. Processor unavailable")
            }
        }
    }
}
