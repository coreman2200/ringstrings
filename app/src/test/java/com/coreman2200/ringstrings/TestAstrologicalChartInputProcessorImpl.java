package com.coreman2200.ringstrings;

import android.app.Activity;

import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.profiledata.ProfileDataBundleAdapter;
import com.coreman2200.ringstrings.app.activity.RingStringsActivity;
import com.coreman2200.ringstrings.domain.swisseph.ISwissephFileHandler;
import com.coreman2200.ringstrings.domain.symbol.chart.Charts;
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.ringstrings.domain.input.astrology.AstrologicalChartInputProcessorImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

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

@RunWith(RobolectricTestRunner.class)

public class TestAstrologicalChartInputProcessorImpl {
    private IProfileDataBundle mTestProfile;
    private ISwissephFileHandler mTestFileHandler;
    private AstrologicalChartInputProcessorImpl mTestProcessor;
    private Activity mTestActivity;
    private RingStringsAppSettings mAppSettings;

    @Before
    public void setup() {
        mTestActivity = Robolectric.setupActivity(RingStringsActivity.class);
        mAppSettings = TestDefaultDataBundles.produceDefaultAppSettingsBundle(mTestActivity);
        mTestProfile = new ProfileDataBundleAdapter(TestDefaultDataBundles.testProfileBundleCoryH);
        mTestProcessor = new AstrologicalChartInputProcessorImpl(mAppSettings);
    }

    @Test
    public void testProcessorProducesBirthChart() {
        IChartedAstralSymbols chart = mTestProcessor.produceAstrologicalChart(mTestProfile, Charts.ASTRAL_NATAL);
        assert (chart != null);
        //chart.testGenerateLoggingsForFullChart();
    }

    @Test
    public void testProcessorProducesCurrentChart() {
        IChartedAstralSymbols chart = mTestProcessor.produceAstrologicalChart(mTestProfile, Charts.ASTRAL_CURRENT);
        assert (chart != null);
        //chart.testGenerateLoggingsForFullChart();
    }

    @Test
    public void testChartProducesCorrectHouseForBodies() {
        IChartedAstralSymbols chart = mTestProcessor.produceAstrologicalChart(mTestProfile, Charts.ASTRAL_NATAL);

        for (CelestialBodies body : CelestialBodies.values()) {
            assert (chart.getHouseForBody(body) != null);
        }
    }

    @Test
    public void testChartProducesCorrectSignForBodies() {
        IChartedAstralSymbols chart = mTestProcessor.produceAstrologicalChart(mTestProfile, Charts.ASTRAL_NATAL);

        for (CelestialBodies body : CelestialBodies.values()) {
            assert (chart.getSignForBody(body) != null);
        }
    }

    //@Test
    public void testDurationProcessorProducesXRandomBirthCharts() {
        mTestProfile = new ProfileDataBundleAdapter(TestDefaultDataBundles.generateRandomProfile());
        int highval = 0;
        int lowval = Integer.MAX_VALUE;
        int testCount = 1000;
        double averageSize = 0;

        long loopstart = System.currentTimeMillis();

        IChartedAstralSymbols chart;
        for (int i = 0; i < testCount; i++) {
            mTestProfile = new ProfileDataBundleAdapter(TestDefaultDataBundles.generateRandomProfile());
            chart = mTestProcessor.produceAstrologicalChart(mTestProfile, Charts.ASTRAL_NATAL);

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
