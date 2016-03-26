package com.coreman2200.data.entity.symbol.lights;

import android.app.Activity;

import com.coreman2200.data.BuildConfig;
import com.coreman2200.data.activity.MainActivity;
import com.coreman2200.domain.profiledata.MockDefaultDataBundles;
import com.coreman2200.domain.symbol.symbolinterface.ILightSymbol;
import com.coreman2200.domain.adapter.profiledata.IProfileDataBundle;
import com.coreman2200.domain.adapter.profiledata.ProfileDataBundleAdapter;
import com.coreman2200.domain.protos.RingStringsAppSettings;
import com.coreman2200.domain.symbol.strata.EntityStrata;
import com.coreman2200.domain.symbol.symbolinterface.IProfileSymbol;
import com.coreman2200.data.processor.profile.ProfileInputProcessor;
import com.coreman2200.data.rsio.symboldef.SymbolDefFileHandlerImpl;
import com.coreman2200.domain.symbol.symbolinterface.ISymbol;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Map;

/**
 * TestLightSymbol
 * description
 *
 * Created by Cory Higginbottom on 11/5/15
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
public class TestLightSymbol {
    private ProfileInputProcessor mTestProcessor;
    private Activity mTestActivity;
    private RingStringsAppSettings mAppSettings;
    private IProfileDataBundle mTestProfile;

    @Before
    public void setup() {
        mTestActivity = Robolectric.setupActivity(MainActivity.class);
        mAppSettings = MockDefaultDataBundles.produceDefaultAppSettingsBundle();
        SymbolDefFileHandlerImpl.createInstance(mTestActivity);
    }


    @Test
    public void testDurationProcessorProducesXRandomProfiles() {
        int highval = 0;
        int lowval = Integer.MAX_VALUE;
        int testCount = 1000;
        double averageSize = 0;

        long loopstart = System.currentTimeMillis();

        for (int i = 0; i < testCount; i++) {
            IProfileSymbol profile = produceTestSubject();

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

    //@Test
    public void testProducingLightsForEntireSymbolStructure() {

        Map<Enum<? extends Enum<?>>, ISymbol> map = produceTestSubject().produceSymbol();
        for (ISymbol elem : map.values()) {
            produceLightSymbol(elem);
        }
    }


    private void produceLightSymbol(ISymbol symbol) {
        ILightSymbol light = produceTestSubject().produceLightSymbolFor(symbol);
        System.out.println(light.getName());
        System.out.println(light.getDescription());
        light.getQualities();
    }

    private IProfileSymbol produceTestSubject() {
        //IProfileDataBundle mTestProfile = new TestProfileDataBundleImpl();
        mTestProfile = new ProfileDataBundleAdapter(MockDefaultDataBundles.generateRandomProfile());
        mTestProcessor = new ProfileInputProcessor(mAppSettings);
        return mTestProcessor.produceProfile(mTestProfile, EntityStrata.PROFILE);
    }
}
