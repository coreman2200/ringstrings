package com.coreman2200.ringstrings.symbol.inputprocessor.entity.symboldef;

import android.app.Activity;

import com.coreman2200.ringstrings.BuildConfig;
import com.coreman2200.ringstrings.protos.SymbolDescription;
import com.coreman2200.ringstrings.rsdisplay.activity.RingStringsActivity;
import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.profiledata.ProfileDataBundleAdapter;
import com.coreman2200.ringstrings.profiledata.TestDefaultDataBundles;
import com.coreman2200.ringstrings.protos.RingStringsAppSettings;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.ringstrings.symbol.entitysymbol.EntityStrata;
import com.coreman2200.ringstrings.symbol.entitysymbol.Profile.IProfileSymbol;
import com.coreman2200.ringstrings.symbol.entitysymbol.Profile.LocalProfileSymbolImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.ProfileInputProcessor;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.BaseNumberSymbols;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Collection;

/**
 * TestSymbolDefFileHandler
 * Tests fo jezits..
 *
 * Created by Cory Higginbottom on 11/4/15
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
public class TestSymbolDefFileHandler {
    private ISymbolDefFileHandler mTestFileHandler;
    private Activity mTestActivity;
    private RingStringsAppSettings mAppSettings;

    @Before
    public void setup() {
        mTestActivity = Robolectric.setupActivity(RingStringsActivity.class);
        mAppSettings = TestDefaultDataBundles.produceDefaultAppSettingsBundle(mTestActivity);
        mTestFileHandler = SymbolDefFileHandlerImpl.createInstance(mTestActivity.getApplicationContext());

    }

    //@Test
    public void testMappingsAvailableForBaseNumbers() {
        for (BaseNumberSymbols symbol : BaseNumberSymbols.values())
            assert (mTestFileHandler.produceSymbolDefForSymbol(symbol) != null);
    }

    //@Test
    public void testMappingsAvailableForCelestialBodies() {
        for (CelestialBodies symbol : CelestialBodies.values())
            assert (mTestFileHandler.produceSymbolDefForSymbol(symbol) != null);
    }

    //@Test
    public void testMappingsAvailableForFullProfile() {
        IProfileSymbol user = produceTestSubject();
        int count = 0;

        Collection<Enum<? extends Enum<?>>> idList = user.symbolIDCollection();
        for (Enum<? extends Enum<?>> id : idList) {
            SymbolDescription sd = mTestFileHandler.produceSymbolDefForSymbol(id);
            if (sd != null) {
                count++;
                System.out.println("Successful mapping  for " + sd.name);
                System.out.println("Quality: " + sd.qualities.toString());
            }
        }
        System.out.println("Mapping Complete: " + count + " symbols found of " + idList.size());
    }

    private IProfileSymbol produceTestSubject() {
        IProfileDataBundle mTestProfile = new ProfileDataBundleAdapter(TestDefaultDataBundles.testProfileBundleCoryH);
        return new ProfileInputProcessor(mAppSettings).
                produceProfile(mTestProfile, EntityStrata.USER);
    }



}
