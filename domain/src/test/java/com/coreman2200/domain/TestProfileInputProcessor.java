package com.coreman2200.domain;

import android.app.Activity;

import com.coreman2200.domain.com.coreman2200.domain.profiledata.MockDefaultDataBundles;
import com.coreman2200.data.entity.profiledata.IProfileDataBundle;
import com.coreman2200.data.entity.profiledata.ProfileDataBundleAdapter;
import com.coreman2200.data.entity.protos.RingStringsAppSettings;
import com.coreman2200.ringstrings.rsdisplay.activity.RingStringsActivity;
import com.coreman2200.domain.swisseph.ISwissephFileHandler;
import com.coreman2200.domain.symbol.entitysymbol.EntityStrata;
import com.coreman2200.domain.symbol.entitysymbol.Profile.IProfileSymbol;
import com.coreman2200.domain.symbol.inputprocessor.entity.symboldef.SymbolDefFileHandlerImpl;
import com.coreman2200.domain.symbol.inputprocessor.ProfileInputProcessor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

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
        mAppSettings = MockDefaultDataBundles.produceDefaultAppSettingsBundle(mTestActivity);
        mTestProfile = new ProfileDataBundleAdapter(MockDefaultDataBundles.testProfileBundleCoryH);
        mTestProcessor = new ProfileInputProcessor(mAppSettings);
        SymbolDefFileHandlerImpl.createInstance(mTestActivity);
    }

    @Test
    public void testProfileInputProcessorProducesUserProfile() {
        IProfileSymbol user = mTestProcessor.produceProfile(mTestProfile, EntityStrata.USER);
        user.testGenerateLogs();
    }

    //@Test
    public void testDurationProcessorProducesXRandomProfiles() {
        mTestProfile = new ProfileDataBundleAdapter(MockDefaultDataBundles.generateRandomProfile());
        int highval = 0;
        int lowval = Integer.MAX_VALUE;
        int testCount = 1000;
        double averageSize = 0;

        long loopstart = System.currentTimeMillis();

        for (int i = 0; i < testCount; i++) {
            mTestProfile = new ProfileDataBundleAdapter(MockDefaultDataBundles.generateRandomProfile());
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
