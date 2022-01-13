package com.coreman2200.ringstrings.domain.input.numerology.grouped

import com.coreman2200.ringstrings.domain.NumerologySettings
import com.coreman2200.ringstrings.domain.swisseph.IProfileData
import com.coreman2200.ringstrings.domain.symbol.SymbolModel
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.GroupedNumbers
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.Pinnacles
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.GroupedNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbol
import java.util.ArrayList

/**
 * PinnaclesProcessor
 * Processes input into Pinnacles Number Symbols Grouping.
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
class PinnaclesProcessor(
    profile: IProfileData,
    settings: NumerologySettings
) : GroupedNumberSymbolsInputProcessor(
    profile, settings),
    IGroupedNumberSymbolsInputProcessor {
    private fun getPinnacles(profile: IProfileData): GroupedNumberSymbol {
        val reducedMonth = singularizeValue(profile.getBirthMonth())
        val reducedDay = singularizeValue(profile.getBirthDay())
        val reducedYear = singularizeValue(profile.getBirthYear())
        val pinnacles = GroupedNumberSymbol(GroupedNumbers.PINNACLES)
        val pinnaclelist: MutableList<INumberSymbol> = ArrayList(4)
        pinnaclelist.add(convertValueToNumberSymbol(reducedMonth + reducedDay))
        pinnaclelist.add(convertValueToNumberSymbol(reducedDay + reducedYear))
        val p1 = pinnaclelist[0].value
        val p2 = pinnaclelist[1].value
        pinnaclelist.add(convertValueToNumberSymbol(p1 + p2))
        pinnaclelist.add(convertValueToNumberSymbol(reducedMonth + reducedYear))
        val iter: Iterator<INumberSymbol> = pinnaclelist.listIterator()
        for (p in Pinnacles.values()) {
            val symbol = iter.next()
            pinnacles.add(p, symbol as SymbolModel)
        }
        return pinnacles
    }

    override fun produceGroupedNumberSymbolsForProfile(): GroupedNumberSymbol {
        return getPinnacles(profile)
    }
}
