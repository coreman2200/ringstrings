package com.coreman2200.ringstrings;

import android.app.Activity;

import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.profiledata.ProfileDataBundleAdapter;
import com.coreman2200.ringstrings.app.activity.RingStringsActivity;
import com.coreman2200.ringstrings.domain.swisseph.ISwissEphemerisManager;
import com.coreman2200.ringstrings.domain.swisseph.SwissEphemerisManagerImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

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

@RunWith(RobolectricTestRunner.class)
//
public class TestSwissEphemerisManagerImpl {
    private Activity mTestActivity;
    private ISwissEphemerisManager mTestSwissephManager;
    private IProfileDataBundle mTestProfile;
    private RingStringsAppSettings mAppSettings;

    @Before
    public void setup() {
        mTestActivity = Robolectric.setupActivity(RingStringsActivity.class);
        mAppSettings = TestDefaultDataBundles.produceDefaultAppSettingsBundle(mTestActivity);
        mTestSwissephManager = new SwissEphemerisManagerImpl(mAppSettings.astro);
        mTestProfile = new ProfileDataBundleAdapter(TestDefaultDataBundles.testProfileBundleCoryH);
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
