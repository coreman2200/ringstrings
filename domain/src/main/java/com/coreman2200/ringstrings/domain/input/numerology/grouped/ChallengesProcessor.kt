package com.coreman2200.ringstrings.domain.input.numerology.grouped

import com.coreman2200.ringstrings.domain.NumerologySettings
import com.coreman2200.ringstrings.domain.swisseph.IProfileData
import com.coreman2200.ringstrings.domain.symbol.SymbolModel
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.Challenges
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.GroupedNumbers
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.GroupedNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbol
import java.util.ArrayList
import kotlin.math.abs

/**
 * ChallengesProcessor
 * Processes the Challenges in a Numerological chart.
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
class ChallengesProcessor(
    profile: IProfileData,
    settings: NumerologySettings
) : GroupedNumberSymbolsInputProcessor(
    profile, settings), IGroupedNumberSymbolsInputProcessor {
    override fun produceGroupedNumberSymbolsForProfile(): GroupedNumberSymbol {
        return getChallenges(profile)
    }

    private fun getChallenges(profile: IProfileData): GroupedNumberSymbol {
        val reducedMonth = singularizeValue(profile.getBirthMonth())
        val reducedDay = singularizeValue(profile.getBirthDay())
        val reducedYear = singularizeValue(profile.getBirthYear())
        val challenges = GroupedNumberSymbol(GroupedNumbers.CHALLENGES)
        val challengelist: MutableList<INumberSymbol> = ArrayList<INumberSymbol>(4)
        challengelist.add(convertValueToNumberSymbol(abs(reducedDay - reducedMonth)))
        challengelist.add(convertValueToNumberSymbol(abs(reducedYear - reducedDay)))
        val p1: Int = challengelist[0].value
        val p2: Int = challengelist[1].value
        challengelist.add(convertValueToNumberSymbol(abs(p2 - p1)))
        challengelist.add(convertValueToNumberSymbol(abs(reducedYear - reducedMonth)))
        val iter: Iterator<INumberSymbol> = challengelist.listIterator()
        for (c in Challenges.values()) {
            val symbol: INumberSymbol = iter.next()
            challenges.add(c, symbol as SymbolModel)
        }
        return challenges
    }
}
