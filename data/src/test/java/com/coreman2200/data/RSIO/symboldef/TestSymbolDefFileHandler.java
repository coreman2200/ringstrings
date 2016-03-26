package com.coreman2200.data.rsio.symboldef;

import android.app.Activity;

import com.coreman2200.data.BuildConfig;
import com.coreman2200.domain.profiledata.MockDefaultDataBundles;
import com.coreman2200.domain.protos.RingStringsAppSettings;
import com.coreman2200.domain.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.domain.symbol.numbersymbol.grouped.BaseNumberSymbols;

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
        mTestActivity = Robolectric.setupActivity(Activity.class);
        mAppSettings = MockDefaultDataBundles.produceDefaultAppSettingsBundle();
        mTestFileHandler = SymbolDefFileHandlerImpl.createInstance(mTestActivity.getApplicationContext());

    }

    @Test
    public void testMappingsAvailableForBaseNumbers() {
        for (BaseNumberSymbols symbol : BaseNumberSymbols.values())
            assert (mTestFileHandler.produceSymbolDefForSymbol(symbol) != null);
    }

    @Test
    public void testMappingsAvailableForCelestialBodies() {
        for (CelestialBodies symbol : CelestialBodies.values())
            assert (mTestFileHandler.produceSymbolDefForSymbol(symbol) != null);
    }

}
