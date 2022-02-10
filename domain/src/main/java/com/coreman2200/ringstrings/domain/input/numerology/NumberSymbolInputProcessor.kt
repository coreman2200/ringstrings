package com.coreman2200.ringstrings.domain.input.numerology

import com.coreman2200.ringstrings.domain.NumerologySettings
import com.coreman2200.ringstrings.domain.input.InputProcessor
import com.coreman2200.ringstrings.domain.input.numerology.numbersystem.INumberSystem
import com.coreman2200.ringstrings.domain.input.numerology.numbersystem.NumberSystemType
import com.coreman2200.ringstrings.domain.symbol.SymbolModel
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.BaseNumbers
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.DerivedKarmicDebts
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.BaseNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.DerivedNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.NumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbolID
import java.lang.IndexOutOfBoundsException
import java.lang.StringBuilder
import java.util.*

/**
 * NumberSymbolInputProcessor
 * Processes Input into INumberSymbol objects
 *
 * Created by Cory Higginbottom on 5/25/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
open class NumberSymbolInputProcessor(protected val settings: NumerologySettings) :
    InputProcessor(),
    INumberSymbolInputProcessor {

    protected val numSystem: INumberSystem = NumberSystemType.values()[settings.numsystem].numberSystem()
    var derivedFromValues = IntArray(2)

    override fun convertTextStringToNumberSymbol(text: String): INumberSymbol {
        val totalValue = convertTextStringToValue(text)
        resetDerivedFromValues()
        val singularized = singularizeValue(totalValue)
        // System.out.println("From: " + text + " => " + totalValue + " => " + singularized + "(reduced)");
        return produceNumberSymbolForValue(singularized)
    }

    protected fun convertTextStringToValue(text: String): Int {
        val charArrayOfTestName = prepareTextForProcessing(text)
        var totalValue = 0
        for (c in charArrayOfTestName) {
            val charVal = numSystem.numValueForChar(c)
            totalValue += charVal
        }
        return totalValue
    }

    override fun convertValueToNumberSymbol(value: Int): INumberSymbol {
        resetDerivedFromValues()
        val singularized = singularizeValue(value)
        return produceNumberSymbolForValue(singularized)
    }

    private fun resetDerivedFromValues() {
        derivedFromValues[1] = 0
        derivedFromValues[0] = derivedFromValues[1]
    }

    private fun derivedFromValue(value: Int) {
        // Left/Right flip-flop..
        derivedFromValues[0] = getDigitOfValueInNthPlace(value, 1)
        derivedFromValues[1] = getDigitOfValueInNthPlace(value, 0)
    }

    private fun produceNumberSymbolForValue(value: Int): INumberSymbol {
        // if there is no left digit.. or right is zero
        return if (derivedFromValues[0] == 0 || derivedFromValues[1] == 0)
            produceBaseNumberSymbolForValue(value) else produceDerivedNumberSymbolForValue(value)
    }

    private fun produceBaseNumberSymbolForValue(value: Int): BaseNumberSymbol {
        val id = INumberSymbolID.id(value) as BaseNumbers
        return BaseNumberSymbol(id)
    }

    private fun produceDerivedNumberSymbolForValue(value: Int): DerivedNumberSymbol {
        val derivedNumber = INumberSymbolID.id(value)
        val root1 = BaseNumberSymbol(INumberSymbolID.id(derivedFromValues[0]) as BaseNumbers)
        val root2 = BaseNumberSymbol(INumberSymbolID.id(derivedFromValues[1]) as BaseNumbers)
        val symbol = DerivedNumberSymbol(derivedNumber, value)
        symbol.add(root1,root2)
        return symbol
    }

    private fun isBaseNumber(value: Int): Boolean =
        INumberSymbolID.id(value) is BaseNumbers

    protected fun singularizeValue(value: Int): Int {
        var singularized = value
        val id = INumberSymbolID.id(value)
        if (id == BaseNumbers.ZERO || id is DerivedKarmicDebts) {
            singularized = addDigitsOfValue(value)
            if (digitsInValue(value) == 2) derivedFromValue(value)
            return singularizeValue(singularized)
        }
        return singularized
    }

    private fun digitsInValue(value: Int): Int {
        return value.toString().length
    }

    private fun addDigitsOfValue(value: Int): Int {
        val digitsInValue = digitsInValue(value)
        var addedDigits = 0
        for (j in 0 until digitsInValue) {
            addedDigits += getDigitOfValueInNthPlace(value, j)
        }
        return addedDigits
    }

    private fun getDigitOfValueInNthPlace(value: Int, digit: Int): Int {
        val str = value.toString()
        val posOfDigit = str.length - 1 - digit
        if (posOfDigit < 0 || posOfDigit > str.length) throw IndexOutOfBoundsException("Digit $digit not found in value '$value'")
        val cDigit = str[posOfDigit]
        return cDigit.toString().toInt()
    }

    protected fun getConsonants(text: String): String {
        val cArrayOfText = prepareTextForProcessing(text)
        val resultingConsonantStringBuilder = StringBuilder()
        for (c in cArrayOfText) {
            if (!isCharAVowel(c) || c == 'y' && !checkIsYBeingUsedAsAVowelInString(text)) resultingConsonantStringBuilder.append(
                c
            )
        }
        return resultingConsonantStringBuilder.toString()
    }

    protected fun getVowels(text: String): String {
        val cArrayOfText = prepareTextForProcessing(text)
        val resultingVowelStringBuilder = StringBuilder()
        for (c in cArrayOfText) {
            if (isCharAVowel(c)) {
                if (c != 'y' || checkIsYBeingUsedAsAVowelInString(text)) resultingVowelStringBuilder.append(
                    c
                )
            }
        }
        return resultingVowelStringBuilder.toString()
    }

    protected fun prepareTextForProcessing(text: String): CharArray {
        val filtered = filterStringAgainstNumberSystem(text.lowercase(Locale.getDefault()))
        return filtered.toCharArray()
    }

    private fun filterStringAgainstNumberSystem(text: String): String {
        val filteredStringBuffer = StringBuilder()
        val characters = text.toCharArray()
        for (c in characters) {
            if (numSystem.numValueForChar(c) > 0) filteredStringBuffer.append(c)
        }
        return filteredStringBuffer.toString()
    }

    protected fun isCharAVowel(c: Char): Boolean {
        for (vowel in VOWEL_LIST) {
            if (c == vowel) return true
        }
        return false
    }

    protected fun checkIsYBeingUsedAsAVowelInString(text: String): Boolean {
        val lower = text.lowercase(Locale.getDefault())
        return lower.indexOf("y") != 0 || lower.lastIndexOf("y") == lower.length - 1
    }

    companion object {
        private val VOWEL_LIST = charArrayOf('a', 'e', 'i', 'o', 'u', 'y')
    }
}
