package com.coreman2200.domain;

import android.app.Activity;

import com.coreman2200.domain.com.coreman2200.domain.profiledata.MockDefaultDataBundles;
import com.coreman2200.domain.profiledata.IProfileDataBundle;
import com.coreman2200.domain.profiledata.ProfileDataBundleAdapter;
import com.coreman2200.domain.protos.RingStringsAppSettings;
import com.coreman2200.presentation.rsdisplay.activity.RingStringsActivity;
import com.coreman2200.data.rsio.swisseph.ISwissEphemerisManager;
import com.coreman2200.data.rsio.swisseph.SwissEphemerisManagerImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * TestSwissEphemerisManagerImpl
 * Tests for Swisseph manager.
 *
 * Created by Cory Higginbottom on 6/6/15
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
public class TestSwissEphemerisManagerImpl {
    private Activity mTestActivity;
    private ISwissEphemerisManager mTestSwissephManager;
    private IProfileDataBundle mTestProfile;
    private RingStringsAppSettings mAppSettings;

    @Before
    public void setup() {
        mTestActivity = Robolectric.setupActivity(RingStringsActivity.class);
        mAppSettings = MockDefaultDataBundles.produceDefaultAppSettingsBundle(mTestActivity);
        mTestSwissephManager = new SwissEphemerisManagerImpl(mAppSettings.astro);
        mTestProfile = new ProfileDataBundleAdapter(MockDefaultDataBundles.testProfileBundleCoryH);
    }

    @Test
    public void testSwissephManagerProducesAccurateNatalPlacementInHouses() {
        mTestSwissephManager.produceNatalAstralMappingsForProfile(mTestProfile);
    }

    @Test
    public void testSwissephManagerProducesAccurateCurrentPlacementInHouses() {
        mTestSwissephManager.produceCurrentAstralMappingsForProfile(mTestProfile);
    }

}
