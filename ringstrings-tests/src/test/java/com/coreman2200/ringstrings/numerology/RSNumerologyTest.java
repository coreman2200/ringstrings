package com.coreman2200.ringstrings.numerology;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.Logger;

import com.coreman2200.ringstrings.BuildConfig;
import com.coreman2200.ringstrings.numerology.numbersystem.AbstractNumberSystem;
import com.coreman2200.ringstrings.numerology.numbersystem.INumberSystem;
import com.coreman2200.ringstrings.numerology.numbersystem.NumberSystemType;
import com.coreman2200.ringstrings.symbol.numbersymbol.BaseNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.INumberSymbol;

//import static org.assertj.android.api.Assertions.assertThat;

/**
 * RSNumerologyTest
 * Testing space for current RSNumerology class functions..
 *
 * Created by Cory Higginbottom on 5/23/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class RSNumerologyTest {
    final String mTestName = "Cory";
    INumberSystem mNumberSystem;
    NumberSystemType mNumberSystemType= NumberSystemType.PYTHAGOREAN;

    @Before
    public void setup() {
        mNumberSystem = AbstractNumberSystem.createNumberSystemWithType(mNumberSystemType);
    }

    public int numSingularizeValue(int value) {
        int singularized = 0;

        if (BaseNumberSymbols.isValueBaseNumberSymbol(value)) {
            return value;
        } else {
            singularized = addDigitsOfValue(value);
            return numSingularizeValue(singularized);
        }
    }

    private int addDigitsOfValue(int value) {
        String valueAsString = String.valueOf(value);
        int addedDigits = 0;

        for (int j = 0; j < valueAsString.length(); j++) {
            addedDigits += Integer.parseInt(String.valueOf(valueAsString.charAt(j)));
        }

        return addedDigits;
    }

    private INumberSymbol convertTextStringToNumberSymbol(String text) {
        char[] charArrayOfTestName = text.toLowerCase().toCharArray();
        int totalValue = 0;

        for (char c : charArrayOfTestName)
        {
            int charVal = mNumberSystem.numValueForChar(c);
            assert(charVal != 0);
            totalValue += charVal;
            Logger.info("Char '%c' returns %i, total value %i", c, charVal, totalValue);
        }

        
        int singularized = numSingularizeValue(totalValue);

        return BaseNumberSymbols.getBaseNumberSymbolIDForValue(singularized).getBaseNumberSymbol();
    }

    @Test
    public void testAddingDigitsProducesCorrectSum() {
        int val1 = 1000;
        int exp1 = 1;
        int val2 = 1234;
        int exp2 = 1+2+3+4;
        int val3 = 1004;
        int exp3 = 1+0+0+4;
        int val4 = 22;
        int exp4 = 2+2;

        assert(addDigitsOfValue(val1) == exp1);
        assert(addDigitsOfValue(val2) == exp2);
        assert(addDigitsOfValue(val3) == exp3);
        assert(addDigitsOfValue(val4) == exp4);

    }

    // Poorly named method to test RSNumerology counterpart vs new constructs..
    @Test
    public void testChaldeanizeProducesAccurateValuesPerNumberSystem()
    {
        INumberSymbol symbol = convertTextStringToNumberSymbol(mTestName);

        if (mNumberSystemType.equals(NumberSystemType.PYTHAGOREAN)) {
            assert (symbol.getNumberSymbolValue() == 7);
        } else {
            assert (symbol.getNumberSymbolValue() == 4);
        }
        //return singularized;
    }

    @Test
    public void testNumSingularizeReducesToBaseNumberSymbolValue() {
        assert(numSingularizeValue(4) == 4);
        assert(numSingularizeValue(9) == 9);
        assert(numSingularizeValue(11) == 11);
        assert(numSingularizeValue(33) == 33);
        assert(numSingularizeValue(22) == 22);
        assert(numSingularizeValue(25) == 7);
        assert(numSingularizeValue(63) == 9);
    }

}
