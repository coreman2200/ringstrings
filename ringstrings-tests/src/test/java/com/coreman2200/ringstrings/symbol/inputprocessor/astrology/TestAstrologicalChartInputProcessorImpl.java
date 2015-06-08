package com.coreman2200.ringstrings.symbol.inputprocessor.astrology;

import android.app.Activity;

import com.coreman2200.ringstrings.BuildConfig;
import com.coreman2200.ringstrings.RingStringsActivity;
import com.coreman2200.ringstrings.profile.IProfileTestLoc;
import com.coreman2200.ringstrings.profile.RandomizedTestProfileImpl;
import com.coreman2200.ringstrings.profile.TestProfileImpl;
import com.coreman2200.ringstrings.swisseph.ISwissephFileHandler;
import com.coreman2200.ringstrings.swisseph.SwissephFileHandlerImpl;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IChartedAstralSymbols;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * TestAstrologicalChartInputProcessorImpl
 * Tests for Astrological chart production
 *
 * Created by Cory Higginbottom on 6/8/15
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
public class TestAstrologicalChartInputProcessorImpl {
    private IProfileTestLoc mTestProfile;
    private ISwissephFileHandler mTestFileHandler;
    private AstrologicalChartInputProcessorImpl mTestProcessor;
    private Activity mTestActivity;

    @Before
    public void setup() {
        mTestActivity = Robolectric.setupActivity(RingStringsActivity.class);
        mTestFileHandler = new SwissephFileHandlerImpl(mTestActivity.getApplicationContext());
        assert(mTestFileHandler.isEphemerisDataAvailable());
        mTestProfile = new TestProfileImpl();
        mTestProcessor = new AstrologicalChartInputProcessorImpl(mTestProfile, mTestFileHandler.getEphemerisPath());
    }

    @Test
    public void testProcessorProducesBirthChart() {
        IChartedAstralSymbols chart = mTestProcessor.produceAstrologicalBirthChart();
        assert (chart != null);
        //chart.testGenerateLoggingsForFullChart();
    }

    @Test
    public void testProcessorProducesCurrentChart() {
        IChartedAstralSymbols chart = mTestProcessor.produceAstrologicalCurrentChart();
        assert (chart != null);
        //chart.testGenerateLoggingsForFullChart();
    }

    @Test
    public void testDurationProcessorProducesXRandomBirthCharts() {
        mTestProfile = new RandomizedTestProfileImpl();
        int highval = 0;
        int lowval = Integer.MAX_VALUE;
        int testCount = 1000;
        double averageSize = 0;

        long loopstart = System.currentTimeMillis();

        IChartedAstralSymbols chart;
        for (int i = 0; i < testCount; i++) {
            mTestProfile.genProfile();
            chart = mTestProcessor.produceAstrologicalBirthChart();

            int gsize = chart.size();
            averageSize += gsize;

            if (gsize > highval)
                highval = chart.size();

            if (chart.size() < lowval)
                lowval = chart.size();
        }
        long elapsedtime = (System.currentTimeMillis() - loopstart)/1000;
        averageSize /= testCount;

        System.out.println("Chart size average: " + averageSize);
        System.out.println("Chart size low: " + lowval);
        System.out.println("Chart size high: " + highval);
        System.out.println("Time to process " + testCount + " charts ~ " + elapsedtime + " seconds.");
    }

}
