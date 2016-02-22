package com.coreman2200.ringstrings;

import android.app.Activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.profiledata.ProfileDataBundleAdapter;
import com.coreman2200.ringstrings.profiledata.TestDefaultDataBundles;
import com.coreman2200.ringstrings.protos.RingStringsAppSettings;
import com.coreman2200.ringstrings.symbol.SymbolStrata;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.NumerologicalChartProcessor;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.IGroupedNumberSymbolsInputProcessor;
import com.coreman2200.ringstrings.symbol.numbersymbol.impl.BaseNumberSymbolImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.BaseNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.impl.DerivedNumberSymbolImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.GroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.impl.GroupedNumberSymbolsImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.INumberSymbol;
import com.coreman2200.ringstrings.symbol.numbersymbol.impl.ListedNumberSymbolsImpl;
import com.coreman2200.ringstrings.symbol.chart.NumerologicalChartImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.impl.RelatedNumberSymbolsImpl;

/**
 * TestSymbolStrata
 * Tests for SymbolStrata
 *
 * Created by Cory Higginbottom on 5/29/15
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
public class TestSymbolStrata {

    final IProfileDataBundle mTestProfile = new ProfileDataBundleAdapter(TestDefaultDataBundles.testProfileBundleCoryH);
    IGroupedNumberSymbolsInputProcessor mTestProcessor;
    private Activity mTestActivity;
    private RingStringsAppSettings mAppSettings;

    @Before
    public void setup() {
        mTestActivity = Robolectric.setupActivity(RingStringsActivity.class);
        mAppSettings = TestDefaultDataBundles.produceDefaultAppSettingsBundle(mTestActivity);
        mTestProcessor = new NumerologicalChartProcessor(mTestProfile, mAppSettings);
    }

    @Test
    public void testAllStrataForNumberSymbols() {
        BaseNumberSymbols eight = BaseNumberSymbols.EIGHT;
        INumberSymbol[] numberSymbols = {new BaseNumberSymbolImpl(eight),
                                        new DerivedNumberSymbolImpl(eight, eight, eight),
                                        new GroupedNumberSymbolsImpl(GroupedNumberSymbols.QUALITIES),
                                        new ListedNumberSymbolsImpl(GroupedNumberSymbols.KARMICLESSON),
                                        new NumerologicalChartImpl(),
                                        new RelatedNumberSymbolsImpl()};

        for (INumberSymbol numbersymbol : numberSymbols)
            SymbolStrata.getSymbolStrataFor(numbersymbol.getNumberSymbolStrata());

    }

    @Test
    public void testNumerologicalChartStratas() {
        mTestProcessor.produceGroupedNumberSymbolsForProfile();
    }

}
