package com.coreman2200.ringstrings.symbol.inputprocessor;

import com.coreman2200.ringstrings.numerology.numbersystem.AbstractNumberSystem;
import com.coreman2200.ringstrings.numerology.numbersystem.INumberSystem;
import com.coreman2200.ringstrings.numerology.numbersystem.NumberSystemType;
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

    INumberSystem mNumberSystem;
    int[] derivedFromValues = new int[2];

    public NumberSymbolInputProcessorImpl(NumberSystemType type) {
        mNumberSystem = AbstractNumberSystem.createNumberSystemWithType(type);
    }

    public INumberSymbol convertTextStringToNumberSymbol(String text) {

        int totalValue = convertTextStringToValue(text);

        int singularized = singularizeValue(totalValue);

        return produceNumberSymbolForValue(singularized);
    }

    public INumberSymbol convertValueToNumberSymbol(int value) {

        int singularized = singularizeValue(value);

        return produceNumberSymbolForValue(singularized);
    }

    private INumberSymbol produceNumberSymbolForValue(int value) {

        if (derivedFromValues[0] == 0 || derivedFromValues[1] == 0) {
            return BaseNumberSymbols.getBaseNumberSymbolIDForValue(value).getBaseNumberSymbol();
        } else {
            BaseNumberSymbols derivedNumber = BaseNumberSymbols.getBaseNumberSymbolIDForValue(value);
            BaseNumberSymbols root1 = BaseNumberSymbols.getBaseNumberSymbolIDForValue(value);
            BaseNumberSymbols root2 = BaseNumberSymbols.getBaseNumberSymbolIDForValue(value);
            return new DerivedNumberSymbolImpl(derivedNumber, root1, root2);
        }
    }

    public int addDigitsOfValue(int value) {
        derivedFromValues[0] = derivedFromValues[1] = 0;
        String valueAsString = String.valueOf(value);
        int digitsInValue = valueAsString.length();
        int addedDigits = 0;

        for (int j = 0; j < digitsInValue; j++) {
            int digit = Integer.parseInt(String.valueOf(valueAsString.charAt(j)));
            if (digitsInValue == 2)
                this.derivedFromValues[j] = digit;
            addedDigits += digit;
        }

        return addedDigits;
    }

    private int convertTextStringToValue(String text) {
        char[] charArrayOfTestName = text.toLowerCase().toCharArray();
        int totalValue = 0;

        for (char c : charArrayOfTestName)
        {
            int charVal = mNumberSystem.numValueForChar(c);
            totalValue += charVal;
        }

        return totalValue;
    }

    public int singularizeValue(int value) {
        int singularized = value;

        if (!BaseNumberSymbols.isValueBaseNumberSymbol(value)) {
            singularized = addDigitsOfValue(value);
            return singularizeValue(singularized);
        }

        return singularized;
    }


}
