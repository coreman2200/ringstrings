package com.coreman2200.ringstrings.symbol.inputprocessor.numerology;

import com.coreman2200.ringstrings.numerology.numbersystem.AbstractNumberSystem;
import com.coreman2200.ringstrings.numerology.numbersystem.INumberSystem;
import com.coreman2200.ringstrings.numerology.numbersystem.NumberSystemType;
import com.coreman2200.ringstrings.symbol.inputprocessor.AbstractInputProcessor;
import com.coreman2200.ringstrings.symbol.numbersymbol.BaseNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.DerivedNumberSymbolImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.INumberSymbol;

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

public class NumberSymbolInputProcessorImpl extends AbstractInputProcessor implements INumberSymbolInputProcessor {

    private static final char[] VOWEL_LIST = {'a', 'e', 'i', 'o', 'u', 'y'};
    INumberSystem mNumberSystem;
    int[] derivedFromValues = new int[2];

    public NumberSymbolInputProcessorImpl(NumberSystemType type) {
        mNumberSystem = AbstractNumberSystem.createNumberSystemWithType(type);
    }

    public final INumberSymbol convertTextStringToNumberSymbol(String text) {

        int totalValue = convertTextStringToValue(text);

        int singularized = singularizeValue(totalValue);

        return produceNumberSymbolForValue(singularized);
    }

    public final INumberSymbol convertValueToNumberSymbol(int value) {

        int singularized = singularizeValue(value);

        return produceNumberSymbolForValue(singularized);
    }

    public final int singularizeValue(int value) {
        int singularized = value;

        if (!BaseNumberSymbols.isValueBaseNumberSymbol(value)) {
            singularized = addDigitsOfValue(value);
            return singularizeValue(singularized);
        }

        return singularized;
    }

    public final int addDigitsOfValue(int value) {
        derivedFromValues[0] = derivedFromValues[1] = 0;
        int digitsInValue = String.valueOf(value).length();
        int addedDigits = 0;

        for (int j = 0; j < digitsInValue; j++) {
            int digit = getDigitOfValueInNthPlace(value, j);
            if (digitsInValue == 2)
                updateDerivedFromValues(1 - j, digit); // 0: Left digit 1: Right digit ie. "23"..

            addedDigits += digit;
        }

        return addedDigits;
    }

    public final int getDigitOfValueInNthPlace(int value, int digit)
    {
        String str = String.valueOf(value);
        int posOfDigit = (str.length() - 1) - digit;
        if (posOfDigit < 0 || posOfDigit > str.length())
            throw new IndexOutOfBoundsException("Digit " + digit + " not found in value '" + value + "'");

        char cDigit = str.charAt(posOfDigit);

        return Integer.parseInt(String.valueOf(cDigit));
    }

    private final void updateDerivedFromValues(int index, int digit) {
        this.derivedFromValues[index] = digit;
    }

    public final String getConsonants(String text)
    {
        char[] cArrayOfText = prepareTextForProcessing(text);
        StringBuilder resultingConsonantStringBuilder = new StringBuilder();

        for (char c : cArrayOfText) {
            if (!isCharAVowel(c) || (c == 'y' && checkIsYBeingUsedAsAVowelInString(text) == false))
                resultingConsonantStringBuilder.append(c);
        }

        return resultingConsonantStringBuilder.toString();
    }

    public final String getVowels(String text) {
        final char[] cArrayOfText = prepareTextForProcessing(text);
        StringBuilder resultingVowelStringBuilder = new StringBuilder();

        for (char c : cArrayOfText) {
            if (isCharAVowel(c)) {
                if (c != 'y' || checkIsYBeingUsedAsAVowelInString(text))
                    resultingVowelStringBuilder.append(c);
            }
        }

        return resultingVowelStringBuilder.toString();
    }

    protected final INumberSymbol produceNumberSymbolForValue(int value) {

        if (derivedFromValues[0] == 0 || derivedFromValues[1] == 0) {
            return BaseNumberSymbols.getBaseNumberSymbolIDForValue(value).getBaseNumberSymbol();
        } else {
            BaseNumberSymbols derivedNumber = BaseNumberSymbols.getBaseNumberSymbolIDForValue(value);
            BaseNumberSymbols root1 = BaseNumberSymbols.getBaseNumberSymbolIDForValue(value);
            BaseNumberSymbols root2 = BaseNumberSymbols.getBaseNumberSymbolIDForValue(value);
            return new DerivedNumberSymbolImpl(derivedNumber, root1, root2);
        }
    }

    protected final int convertTextStringToValue(String text) {
        char[] charArrayOfTestName = prepareTextForProcessing(text);
        int totalValue = 0;

        for (char c : charArrayOfTestName)
        {
            int charVal = mNumberSystem.numValueForChar(c);
            totalValue += charVal;
        }

        return totalValue;
    }

    protected final char[] prepareTextForProcessing(String text) {
        String filtered = filterStringAgainstNumberSystem(text.toLowerCase());

        return filtered.toCharArray();
    }

    protected final String filterStringAgainstNumberSystem(String text) {
        StringBuilder filteredStringBuffer = new StringBuilder();

        final char[] characters = text.toCharArray();

        for (char c : characters) {
            if (mNumberSystem.numValueForChar(c) > 0)
                filteredStringBuffer.append(c);
        }

        return filteredStringBuffer.toString();
    }

    protected final boolean isCharAVowel(char c)
    {

        for (char vowel : VOWEL_LIST)
        {
            if (c == vowel)
                return true;
        }
        return false;
    }

    protected final boolean checkIsYBeingUsedAsAVowelInString(String text) {
        String lower = text.toLowerCase();
        return (lower.indexOf("y") != 0 || lower.lastIndexOf("y") == (lower.length()-1));
    }


}
