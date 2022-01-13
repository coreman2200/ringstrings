package com.coreman2200.ringstrings.domain.input.numerology.grouped
import com.coreman2200.ringstrings.domain.NumerologySettings
import com.coreman2200.ringstrings.domain.swisseph.IProfileData
import com.coreman2200.ringstrings.domain.symbol.SymbolModel
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.BaseNumbers
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.GroupedNumbers
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.Qualities
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.BaseNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.GroupedNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbol
import java.util.*

/**
 * QualitiesProcessor
 * Processes the user's different numerological qualities.
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
class QualitiesProcessor(
    profile: IProfileData,
    settings: NumerologySettings
) : GroupedNumberSymbolsInputProcessor(
    profile, settings), IGroupedNumberSymbolsInputProcessor {
    private val processed: GroupedNumberSymbol = GroupedNumberSymbol(GroupedNumbers.QUALITIES)
    private fun getFirstCharacterOfString(text: String): String {
        return text.substring(0, 1)
    }

    private fun numGetBalance(): INumberSymbol {
        val firstNameFirstLetter =
            convertTextStringToValue(getFirstCharacterOfString(profile.firstName()))
        val midNameFirstLetter =
            convertTextStringToValue(getFirstCharacterOfString(profile.middleName()))
        val lastNameFirstLetter =
            convertTextStringToValue(getFirstCharacterOfString(profile.lastName()))
        return convertValueToNumberSymbol(firstNameFirstLetter + midNameFirstLetter + lastNameFirstLetter)
    }

    // TODO: ?? Not used..
    private fun numGetBirthDay(): INumberSymbol {
        return convertValueToNumberSymbol(profile.getBirthDay())
    }

    private fun numGetExpression(): INumberSymbol {
        val firstName = convertTextStringToValue(profile.firstName())
        val midName = convertTextStringToValue(profile.middleName())
        val lastName = convertTextStringToValue(profile.lastName())
        return convertValueToNumberSymbol(firstName + midName + lastName)
    }

    private fun numGetLifePath(): INumberSymbol {
        val bday = profile.getBirthDay()
        val bmonth = profile.getBirthMonth()
        val byear = profile.getBirthYear()
        return convertValueToNumberSymbol(bday + bmonth + byear)
    }

    // Life Path + Expression..
    private fun numGetMaturity(): INumberSymbol {
        val lifePath = processed.get(Qualities.LIFEPATH) as INumberSymbol
        val expression = processed.get(Qualities.EXPRESSION) as INumberSymbol
        return convertValueToNumberSymbol(lifePath.value + expression.value)
    }

    // First, Middle, Last..
    private fun numGetPersonality(): INumberSymbol {
        val first = convertTextStringToValue(getConsonants(profile.firstName()))
        val middle = convertTextStringToValue(getConsonants(profile.middleName()))
        val last = convertTextStringToValue(getConsonants(profile.lastName()))
        return convertValueToNumberSymbol(first + middle + last)
    }

    // First Name, Bday..
    private fun numGetRationalThought(): INumberSymbol {
        val i =
            convertTextStringToValue(profile.firstName()) + singularizeValue(profile.getBirthDay())
        return convertValueToNumberSymbol(i)
    }

    // First, Middle, Last name..
    private fun numGetSoulUrge(): INumberSymbol {
        val firstName = convertTextStringToValue(getVowels(profile.firstName()))
        val midName = convertTextStringToValue(getVowels(profile.middleName()))
        val lastName = convertTextStringToValue(getVowels(profile.lastName()))
        return convertValueToNumberSymbol(firstName + midName + lastName)
    }

    // Karmic Lessons Count..
    private fun numGetSubconsciousSelf(): INumberSymbol {
        val karmiclessons: GroupedNumberSymbol = processed.get(Qualities.KARMICLESSON) as GroupedNumberSymbol
        val karmiccount = karmiclessons.size
        return convertValueToNumberSymbol(9 - karmiccount)
    }

    private fun numGetHiddenPassionsFromOccurrenceMap(occurrences: Map<BaseNumbers, Int>): SymbolModel {
        val passions = getListOfMostCommonFromMap(occurrences)
        if (passions.size == 0) return BaseNumberSymbol(BaseNumbers.ZERO)
        val hpgroup = GroupedNumberSymbol(GroupedNumbers.HIDDENPASSIONS)
        for (lesson in passions) hpgroup.add(lesson)
        return hpgroup
    }

    private fun numMapOccurenceOfNumberSymbolsInName(): Map<BaseNumbers, Int> {
        val fullname = profile.firstName() + profile.middleName() + profile.lastName()
        val nameArray = prepareTextForProcessing(fullname)
        val occurrences = mutableMapOf<BaseNumbers, Int>()
        for (c in nameArray) {
            val charValue = numSystem.numValueForChar(c)
            val symbol = BaseNumbers.values()[charValue]
            occurrences[symbol]?.plus(1)
        }
        return occurrences
    }

    private fun getListOfMostCommonFromMap(occurrences: Map<BaseNumbers, Int>): ArrayList<SymbolModel> {
        val counts = occurrences.values.toTypedArray()
        Arrays.sort(counts)
        val high = counts[counts.size - 1]
        val mostcommon = ArrayList<SymbolModel>()
        for (symbol in BaseNumbers.values()) {
            if (occurrences.containsKey(symbol) && occurrences[symbol] == high) mostcommon.add(
                BaseNumberSymbol(symbol)
            )
        }
        return mostcommon
    }

    private fun numGetKarmicLessonsListFromOccurrenceMap(occurrences: Map<BaseNumbers, Int>): GroupedNumberSymbol {
        val lessons = getListOfNotOccurredFromMap(occurrences)
        val klgroup = GroupedNumberSymbol(GroupedNumbers.KARMICLESSON)
        for (lesson in lessons) klgroup.add(lesson)
        return klgroup
    }

    private fun getListOfNotOccurredFromMap(occurrences: Map<BaseNumbers, Int>): ArrayList<BaseNumberSymbol> {
        val notfound = ArrayList<BaseNumberSymbol>()
        for (symbol in BaseNumbers.values()) {
            if (symbol == BaseNumbers.ZERO) continue
            if (!occurrences.containsKey(symbol) && !checkIsSymbolMasterNumber(symbol)) notfound.add(
                BaseNumberSymbol(symbol)
            )
        }
        return notfound
    }

    protected fun checkIsSymbolMasterNumber(symbol: BaseNumbers): Boolean {
        return symbol.value() > 9
    }

    override fun produceGroupedNumberSymbolsForProfile(): GroupedNumberSymbol {
        processed.clear()
        processed.add(Qualities.LIFEPATH, numGetLifePath() as SymbolModel)
        processed.add(Qualities.BALANCE, numGetBalance() as SymbolModel)
        processed.add(Qualities.EXPRESSION, numGetExpression() as SymbolModel)
        processed.add(Qualities.MATURITY, numGetMaturity() as SymbolModel)
        processed.add(Qualities.RATIONALTHOUGHT, numGetRationalThought() as SymbolModel)
        processed.add(Qualities.SOULURGE, numGetSoulUrge() as SymbolModel)
        processed.add(Qualities.PERSONALITY, numGetPersonality() as SymbolModel)
        val occurrences = numMapOccurenceOfNumberSymbolsInName()
        processed.add(
            Qualities.HIDDENPASSIONS,
            numGetHiddenPassionsFromOccurrenceMap(occurrences)
        )
        processed.add(
            Qualities.KARMICLESSON,
            numGetKarmicLessonsListFromOccurrenceMap(occurrences)
        )
        processed.add(Qualities.SUBCONSCIOUSSELF, numGetSubconsciousSelf() as SymbolModel)
        return processed
    }
}
