package com.coreman2200.ringstrings;

import android.app.Activity;

import com.coreman2200.ringstrings.profile.IProfileTestLoc;
import com.coreman2200.ringstrings.profile.RandomizedTestProfileImpl;
import com.coreman2200.ringstrings.swisseph.ISwissephFileHandler;
import com.coreman2200.ringstrings.swisseph.SwissephFileHandlerImpl;
import com.coreman2200.ringstrings.symbol.symbolinterface.IChartedSymbols;
import com.coreman2200.ringstrings.symbol.inputprocessor.astrology.ProfileInputProcessor;
import com.coreman2200.ringstrings.symbol.entitysymbol.profile.LocalProfileSymbolMappingImpl;
import com.coreman2200.ringstrings.symbol.entitysymbol.profile.UserProfileSymbolMappingImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Collection;

/**
 * TestProfileInputProcessor
 * Tests for ProfileInputProcessor
 *
 * Created by Cory Higginbottom on 6/10/15
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
public class TestProfileInputProcessor {
    private IProfileTestLoc mTestProfile;
    private ISwissephFileHandler mTestFileHandler;
    private Activity mTestActivity;
    private ProfileInputProcessor mTestProcessor;

    @Before
    public void setup() {
        mTestActivity = Robolectric.setupActivity(RingStringsActivity.class);
        mTestFileHandler = new SwissephFileHandlerImpl(mTestActivity.getApplicationContext());
        assert(mTestFileHandler.isEphemerisDataAvailable());
        mTestProfile = new TestFriendProfileImpl();
        mTestProcessor = new ProfileInputProcessor(mTestProfile, mTestFileHandler.getEphemerisPath());
    }

    @Test
    public void testProfileInputProcessorProducesUserProfile() {
        Collection<IChartedSymbols> charts = mTestProcessor.produceUserCharts();
        UserProfileSymbolMappingImpl user = new UserProfileSymbolMappingImpl(mTestProfile);
        user.addCharts(charts);
        user.testGenerateLogs();
    }

    //@Test
    public void testDurationProcessorProducesXRandomProfiles() {
        mTestProfile = new RandomizedTestProfileImpl();
        int highval = 0;
        int lowval = Integer.MAX_VALUE;
        int testCount = 1000;
        double averageSize = 0;

        long loopstart = System.currentTimeMillis();

        Collection<IChartedSymbols> charts;
        for (int i = 0; i < testCount; i++) {
            mTestProfile.genProfile();
            charts = mTestProcessor.produceUserCharts();
            LocalProfileSymbolMappingImpl profile = new LocalProfileSymbolMappingImpl(mTestProfile);
            profile.addCharts(charts);
            //user.testGenerateLog();

            int gsize = profile.size();
            averageSize += gsize;

            if (gsize > highval)
                highval = profile.size();

            if (profile.size() < lowval)
                lowval = profile.size();
        }
        long elapsedtime = (System.currentTimeMillis() - loopstart)/1000;
        averageSize /= testCount;

        System.out.println("user size average: " + averageSize);
        System.out.println("user size low: " + lowval);
        System.out.println("user size high: " + highval);
        System.out.println("Time to process " + testCount + " users ~ " + elapsedtime + " seconds.");
    }
}
