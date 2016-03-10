package com.coreman2200.ringstrings;

import android.app.Activity;
import android.content.Context;

import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.profiledata.ProfileDataBundleAdapter;
import com.coreman2200.ringstrings.profiledata.TestDefaultDataBundles;
import com.coreman2200.ringstrings.protos.RingStringsAppSettings;
import com.coreman2200.ringstrings.rsdisplay.activity.RingStringsActivity;
import com.coreman2200.ringstrings.swisseph.ISwissephFileHandler;
import com.coreman2200.ringstrings.symbol.entitysymbol.EntityStrata;
import com.coreman2200.ringstrings.symbol.entitysymbol.Profile.IProfileSymbol;
import com.coreman2200.ringstrings.symbol.entitysymbol.Profile.LocalProfileSymbolImpl;
import com.coreman2200.ringstrings.symbol.entitysymbol.Profile.UserProfileSymbolImpl;
import com.coreman2200.ringstrings.symbol.symbolinterface.IChartedSymbols;
import com.coreman2200.ringstrings.symbol.inputprocessor.ProfileInputProcessor;

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
    private IProfileDataBundle mTestProfile;
    private ISwissephFileHandler mTestFileHandler;
    private Activity mTestActivity;
    private ProfileInputProcessor mTestProcessor;
    private RingStringsAppSettings mAppSettings;

    @Before
    public void setup() {
        mTestActivity = Robolectric.setupActivity(RingStringsActivity.class);
        mAppSettings = TestDefaultDataBundles.produceDefaultAppSettingsBundle(mTestActivity);
        mTestProfile = new ProfileDataBundleAdapter(TestDefaultDataBundles.testProfileBundleCoryH);
        mTestProcessor = new ProfileInputProcessor(mAppSettings);
    }

    @Test
    public void testProfileInputProcessorProducesUserProfile() {
        IProfileSymbol user = mTestProcessor.produceProfile(mTestProfile, EntityStrata.USER);
        user.testGenerateLogs();
    }

    //@Test
    public void testDurationProcessorProducesXRandomProfiles() {
        mTestProfile = new ProfileDataBundleAdapter(TestDefaultDataBundles.generateRandomProfile());
        int highval = 0;
        int lowval = Integer.MAX_VALUE;
        int testCount = 1000;
        double averageSize = 0;

        long loopstart = System.currentTimeMillis();

        for (int i = 0; i < testCount; i++) {
            mTestProfile = new ProfileDataBundleAdapter(TestDefaultDataBundles.generateRandomProfile());
            mTestProcessor = new ProfileInputProcessor(mAppSettings);
            IProfileSymbol profile = mTestProcessor.produceProfile(mTestProfile, EntityStrata.PROFILE);
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
