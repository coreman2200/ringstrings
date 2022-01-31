package com.coreman2200.ringstrings.domain.input.numerology.grouped
import com.coreman2200.ringstrings.domain.NumerologySettings
import com.coreman2200.ringstrings.domain.input.entity.IProfileData
import com.coreman2200.ringstrings.domain.symbol.SymbolModel
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.GroupedNumbers
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.Personal
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.GroupedNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbol
import java.util.ArrayList

/**
 * PersonalsProcessor
 * Processes the user's personal day, month, year..
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
class PersonalsProcessor(
    profile: IProfileData,
    settings: NumerologySettings
) : GroupedNumberSymbolsInputProcessor(
    profile, settings),
    IGroupedNumberSymbolsInputProcessor {
    private fun getPersonals(profile: IProfileData): GroupedNumberSymbol {
        val personals = GroupedNumberSymbol(GroupedNumbers.PERSONAL)
        val personallist: MutableList<INumberSymbol> = ArrayList(3)
        personallist.add(convertValueToNumberSymbol(numGetPersonalDay(profile)))
        personallist.add(convertValueToNumberSymbol(numGetPersonalMonth(profile)))
        personallist.add(convertValueToNumberSymbol(numGetPersonalYear(profile)))
        val iter: Iterator<INumberSymbol> = personallist.listIterator()
        for (p in Personal.values()) {
            val symbol = iter.next()
            personals.add(p, symbol)
        }
        return personals
    }

    private fun numGetPersonalDay(profile: IProfileData): Int {
        return singularizeValue(numGetPersonalMonth(profile) + singularizeValue(profile.birthMonth()))
    }

    private fun numGetPersonalMonth(profile: IProfileData): Int {
        return singularizeValue(numGetPersonalYear(profile) + singularizeValue(profile.birthDay()))
    }

    private fun numGetPersonalYear(profile: IProfileData): Int {
        val month = singularizeValue(profile.birthMonth())
        val day = singularizeValue(profile.birthDay())
        val year = singularizeValue(profile.birthYear())
        return singularizeValue(month + day + year)
    }

    override fun produceGroupedNumberSymbolsForProfile(): GroupedNumberSymbol {
        return getPersonals(profile)
    }
}
