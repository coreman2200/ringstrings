package com.coreman2200.ringstrings.domain.input.numerology

import com.coreman2200.ringstrings.domain.NumerologySettings
import com.coreman2200.ringstrings.domain.input.numerology.grouped.GroupedNumberSymbolsInputProcessor
import com.coreman2200.ringstrings.domain.input.numerology.grouped.IGroupedNumberSymbolsInputProcessor
import com.coreman2200.ringstrings.domain.input.entity.IProfileData
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.GroupedNumbers
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.GroupedNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.NumerologicalChart

/**
 * NumerologicalChartProcessor
 * Builds a full Numerological Chart for a Profile.
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
class NumerologicalChartProcessor(
    profile: IProfileData,
    settings: NumerologySettings
) : GroupedNumberSymbolsInputProcessor(profile, settings), IGroupedNumberSymbolsInputProcessor {
    override fun produceGroupedNumberSymbolsForProfile(): NumerologicalChart {
        val chart = NumerologicalChart()
        chart.profileid = profile.id

        // Grouped Symbols that establish the full number symbol
        val groupedSymbols: Array<GroupedNumbers> = arrayOf<GroupedNumbers>(
            GroupedNumbers.QUALITIES, GroupedNumbers.CHALLENGES,
            GroupedNumbers.PERIODS, GroupedNumbers.PERSONAL,
            GroupedNumbers.PINNACLES
        )
        for (type in groupedSymbols) {
            val grouped = getGroupedNumberSymbolsForType(type)
            grouped.getAll().forEach {
                it.chartid = chart.chartid
                it.profileid = chart.profileid
            }

            chart.add(type, grouped)
        }
        return chart
    }

    private fun getGroupedNumberSymbolsForType(type: GroupedNumbers): GroupedNumberSymbol {
        return genProcessorType(type).produceGroupedNumberSymbolsForProfile()
    }

    private fun genProcessorType(type: GroupedNumbers): IGroupedNumberSymbolsInputProcessor {
        return getProcessor(type, profile, settings)
    }
}
