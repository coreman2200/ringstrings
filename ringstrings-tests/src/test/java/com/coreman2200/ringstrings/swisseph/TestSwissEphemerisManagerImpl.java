package com.coreman2200.ringstrings.swisseph;

import android.app.Activity;

import com.coreman2200.ringstrings.BuildConfig;
import com.coreman2200.ringstrings.RingStringsActivity;
import com.coreman2200.ringstrings.profile.IProfileTestLoc;
import com.coreman2200.ringstrings.profile.TestProfileImpl;

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
    private ISwissephFileHandler mTestFileHandler;
    private Activity mTestActivity;
    private ISwissEphemerisManager mTestSwissephManager;
    private IProfileTestLoc mTestProfile;

    @Before
    public void setup() {
        mTestActivity = Robolectric.setupActivity(RingStringsActivity.class);
        mTestFileHandler = new SwissephFileHandlerImpl(mTestActivity.getApplicationContext());
        assert(mTestFileHandler.isEphemerisDataAvailable());
        mTestSwissephManager = new SwissEphemerisManagerImpl(mTestFileHandler.getEphemerisPath());
        mTestProfile = new TestProfileImpl();
    }

    @Test
    public void testSwissephManagerProducesHouseCuspValues() {
        mTestSwissephManager.testAstrGetHousesForProfile(mTestProfile);
    }

    @Test
    public void testSwissephManagerProducesAccuratePlacementInHouses() {
        mTestSwissephManager.testAstrPlaceHousesForProfile(mTestProfile);
    }

}
