package com.coreman2200.ringstrings.symbol.entitysymbol.Lights;

import android.app.Activity;

import com.coreman2200.ringstrings.BuildConfig;
import com.coreman2200.ringstrings.RingStringsActivity;
import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.profiledata.ProfileDataBundleAdapter;
import com.coreman2200.ringstrings.profiledata.TestDefaultDataBundles;
import com.coreman2200.ringstrings.protos.RingStringsAppSettings;
import com.coreman2200.ringstrings.symbol.entitysymbol.Profile.IProfileSymbol;
import com.coreman2200.ringstrings.symbol.entitysymbol.Profile.LocalProfileSymbolImpl;
import com.coreman2200.ringstrings.symbol.entitysymbol.Tags.IThemeSymbol;
import com.coreman2200.ringstrings.symbol.inputprocessor.entity.symboldef.ISymbolDefFileHandler;
import com.coreman2200.ringstrings.symbol.inputprocessor.entity.symboldef.SymbolDefFileHandlerImpl;
import com.coreman2200.ringstrings.symbol.symbolinterface.ISymbol;

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
    private ISymbolDefFileHandler mTestFileHandler;
    private Activity mTestActivity;
    private RingStringsAppSettings mAppSettings;

    @Before
    public void setup() {
        mTestActivity = Robolectric.setupActivity(RingStringsActivity.class);
        mAppSettings = TestDefaultDataBundles.produceDefaultAppSettingsBundle(mTestActivity);
        mTestFileHandler = SymbolDefFileHandlerImpl.createInstance(mTestActivity);
    }

    @Test
    public void testLightSymbolAccuratelyDescribesTestSubject() {
        final LocalProfileSymbolImpl profile = produceTestSubject();
        System.out.println(profile.getName());
        System.out.println(profile.getQualities().toString());

    }

    //@Test
    public void testDurationProcessorProducesXRandomProfiles() {
        int highval = 0;
        int lowval = Integer.MAX_VALUE;
        int testCount = 10;
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
        for (Map.Entry<Enum<? extends Enum<?>>, IThemeSymbol> entry : produceTestSubject().produceSymbol()) {
            produceLightSymbol(entry.getValue());
        }
    }


    private void produceLightSymbol(ISymbol symbol) {
        ILightSymbol light = new LightSymbolImpl(symbol);
        System.out.println(light.getName());
        System.out.println(light.getDescription());
        light.getQualities();
    }

    private LocalProfileSymbolImpl produceTestSubject() {
        //IProfileDataBundle mTestProfile = new TestProfileDataBundleImpl();
        IProfileDataBundle mTestProfile = new ProfileDataBundleAdapter(TestDefaultDataBundles.generateRandomProfile());
        LocalProfileSymbolImpl local = new LocalProfileSymbolImpl(mTestProfile, mAppSettings);
        return local;
    }
}
