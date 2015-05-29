package com.coreman2200.ringstrings.symbol.inputprocessor.numerology;

import com.coreman2200.ringstrings.numerology.numbersystem.AbstractNumberSystem;
import com.coreman2200.ringstrings.numerology.numbersystem.INumberSystem;
import com.coreman2200.ringstrings.numerology.numbersystem.NumberSystemType;
import com.coreman2200.ringstrings.symbol.inputprocessor.AbstractInputProcessor;
import com.coreman2200.ringstrings.symbol.numbersymbol.BaseNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.DerivedNumberSymbolImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.INumberSymbol;

import org.robolectric.util.Logger;

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
    protected INumberSystem mNumberSystem;
    int[] derivedFromValues = new int[2];

    protected void setNumberSystem(NumberSystemType type) {
        mNumberSystem = AbstractNumberSystem.createNumberSystemWithType(type);
    }

    public final INumberSymbol convertTextStringToNumberSymbol(String text) {

        int totalValue = convertTextStringToValue(text);

        resetDerivedFromValues();
        int singularized = singularizeValue(totalValue);
        //System.out.println("From: " + text + " => " + totalValue + " => " + singularized + "(reduced)");

        return produceNumberSymbolForValue(singularized);
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

    public final INumberSymbol convertValueToNumberSymbol(int value) {

        resetDerivedFromValues();
        int singularized = singularizeValue(value);
        //System.out.println("From: " + value + " => " + singularized + "(reduced)");

        return produceNumberSymbolForValue(singularized);
    }

    private final void resetDerivedFromValues() {
        this.derivedFromValues[0] = this.derivedFromValues[1] = 0;
    }

    private final void derivedFromValue(int value) {
        assert(digitsInValue(value) == 2);
        // Left/Right flip-flop..
        this.derivedFromValues[0] = getDigitOfValueInNthPlace(value, 1);
        this.derivedFromValues[1] = getDigitOfValueInNthPlace(value, 0);
    }

    public final INumberSymbol produceNumberSymbolForValue(int value) {
        if (derivedFromValues[0] == 0) // if there is no left digit..
            return produceBaseNumberSymbolForValue(value);
        else
            return produceDerivedNumberSymbolForValue(value);
    }

    protected final INumberSymbol produceBaseNumberSymbolForValue(int value) {
        return BaseNumberSymbols.getBaseNumberSymbolIDForValue(value).getBaseNumberSymbol();
    }

    protected final INumberSymbol produceDerivedNumberSymbolForValue(int value) {
        BaseNumberSymbols derivedNumber = BaseNumberSymbols.getBaseNumberSymbolIDForValue(value);
        BaseNumberSymbols root1 = BaseNumberSymbols.getBaseNumberSymbolIDForValue(derivedFromValues[0]);
        BaseNumberSymbols root2 = BaseNumberSymbols.getBaseNumberSymbolIDForValue(derivedFromValues[1]);
        assert(root1 != null);
        assert (root2 != null);
        assert (derivedFromValues[0] != 0);
        return new DerivedNumberSymbolImpl(derivedNumber, root1, root2);
    }

    protected final int singularizeValue(int value) {
        int singularized = value;

        if (!BaseNumberSymbols.isValueBaseNumberSymbol(value)) {
            singularized = addDigitsOfValue(value);
            if (digitsInValue(value) == 2)
                derivedFromValue(value);
            return singularizeValue(singularized);
        }

        return singularized;
    }

    protected final int digitsInValue(int value) {
        return String.valueOf(value).length();
    }

    protected final int addDigitsOfValue(int value) {
        int digitsInValue = digitsInValue(value);
        int addedDigits = 0;

        for (int j = 0; j < digitsInValue; j++) {
            addedDigits += getDigitOfValueInNthPlace(value, j);
        }

        return addedDigits;
    }

    protected final int getDigitOfValueInNthPlace(int value, int digit)
    {
        String str = String.valueOf(value);
        int posOfDigit = (str.length() - 1) - digit;
        if (posOfDigit < 0 || posOfDigit > str.length())
            throw new IndexOutOfBoundsException("Digit " + digit + " not found in value '" + value + "'");

        char cDigit = str.charAt(posOfDigit);

        return Integer.parseInt(String.valueOf(cDigit));
    }

    protected final String getConsonants(String text)
    {
        char[] cArrayOfText = prepareTextForProcessing(text);
        StringBuilder resultingConsonantStringBuilder = new StringBuilder();

        for (char c : cArrayOfText) {
            if (!isCharAVowel(c) || (c == 'y' && checkIsYBeingUsedAsAVowelInString(text) == false))
                resultingConsonantStringBuilder.append(c);
        }

        return resultingConsonantStringBuilder.toString();
    }

    protected final String getVowels(String text) {
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
