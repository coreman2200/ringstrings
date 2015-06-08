package com.coreman2200.ringstrings.numerology;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import com.coreman2200.ringstrings.BuildConfig;
import com.coreman2200.ringstrings.profile.IProfile;
import com.coreman2200.ringstrings.profile.IProfileTestLoc;
import com.coreman2200.ringstrings.profile.RandomizedTestProfileImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.NumerologicalChartProcessorImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.IGroupedNumberSymbolsInputProcessor;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.IGroupedNumberSymbols;

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

    final IProfileTestLoc mTestProfile = new RandomizedTestProfileImpl();
    final IGroupedNumberSymbolsInputProcessor mTestProcessor = new NumerologicalChartProcessorImpl(mTestProfile);

    @Test
    public void testRandomNumerologyChartProcessor() {
        int highval = 0;
        int lowval = 1000;
        int testCount = 1000;
        double averageSize = 0;

        long loopstart = System.currentTimeMillis();

        for (int i = 0; i < testCount; i++) {
            mTestProfile.genProfile();
            IGroupedNumberSymbols grouped = mTestProcessor.produceGroupedNumberSymbolsForProfile();

            int gsize = grouped.size();
            averageSize += gsize;

            if (gsize > highval)
                highval = grouped.size();

            if (grouped.size() < lowval)
                lowval = grouped.size();
        }
        long elapsedtime = (System.currentTimeMillis() - loopstart)/1000;
        averageSize /= testCount;

        System.out.println("grouped size average: " + averageSize);
        System.out.println("grouped size low: " + lowval);
        System.out.println("grouped size high: " + highval);
        System.out.println("Time to process " + testCount + " charts ~ " + elapsedtime + " seconds.");

    }

}
