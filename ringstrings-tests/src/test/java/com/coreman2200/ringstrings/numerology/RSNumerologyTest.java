package com.coreman2200.ringstrings.numerology;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.Logger;

import com.coreman2200.ringstrings.BuildConfig;
import com.coreman2200.ringstrings.numerology.numbersystem.NumberSystemType;
import com.coreman2200.ringstrings.symbol.IProfile;
import com.coreman2200.ringstrings.symbol.RandomizedTestProfileImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.INumberSymbolInputProcessor;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.NumberSymbolInputProcessorImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.NumerologicalChartProcessorImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.IGroupedNumberSymbolsInputProcessor;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.PinnaclesProcessorImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.QualitiesProcessorImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.DerivedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.GroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.IGroupedNumberSymbols;
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

    final IProfile mTestProfile = new RandomizedTestProfileImpl();
    final IGroupedNumberSymbolsInputProcessor mTestProcessor = new NumerologicalChartProcessorImpl();

    private boolean numCheckKarmicDebt(int val) {
        return DerivedNumberSymbols.isValueDerivedNumberSymbol(val);
    }

    @Test
    public void testQualitiesProcessor() {
        int highval = 0;
        int lowval = 0;
        double averageSize = 0;

        for (int i = 0; i < 1000; i++) {
            mTestProfile.genProfile();
            IGroupedNumberSymbols grouped = mTestProcessor.produceGroupedNumberSymbolsForProfile(mTestProfile);

            // TODO: assert (grouped.size() == 0); ??

            averageSize = (averageSize + grouped.size())/(i+1.0);

            if (grouped.size() > highval)
                highval = grouped.size();

            if (grouped.size() < lowval)
                lowval = grouped.size();
        }


        System.out.println("grouped size average: " + averageSize);
        System.out.println("grouped size low: " + lowval);
        System.out.println("grouped size high: " + highval);

    }

}
