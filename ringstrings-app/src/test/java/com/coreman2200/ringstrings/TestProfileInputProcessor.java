package com.coreman2200.ringstrings;

import android.app.Activity;

import com.coreman2200.ringstrings.profile.IProfileTestLoc;
import com.coreman2200.ringstrings.swisseph.ISwissephFileHandler;
import com.coreman2200.ringstrings.swisseph.SwissephFileHandlerImpl;
import com.coreman2200.ringstrings.symbol.IChartedSymbols;
import com.coreman2200.ringstrings.symbol.inputprocessor.astrology.ProfileInputProcessor;
import com.coreman2200.ringstrings.symbol.profilemap.UserProfileSymbolMapImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.LinkedList;

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
        mTestProfile = new TestProfileImpl();
        mTestProcessor = new ProfileInputProcessor(mTestProfile, mTestFileHandler.getEphemerisPath());
    }

    @Test
    public void testProfileInputProcessorProducesUserProfile() {
        LinkedList<IChartedSymbols> charts = mTestProcessor.produceUserCharts();
        UserProfileSymbolMapImpl user = new UserProfileSymbolMapImpl(mTestProfile);
        user.addCharts(charts);
        user.testGenerateLog();
    }


}
