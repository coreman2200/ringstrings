package com.coreman2200.ringstrings;

import android.app.Activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.profiledata.ProfileDataBundleAdapter;
import com.coreman2200.ringstrings.app.activity.RingStringsActivity;
import com.coreman2200.ringstrings.domain.symbol.SymbolStrata;
import com.coreman2200.ringstrings.domain.input.numerology.NumerologicalChartProcessor;
import com.coreman2200.ringstrings.domain.input.numerology.grouped.IGroupedNumberSymbolsInputProcessor;
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.BaseNumberSymbol;
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.BaseNumbers;
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.DerivedNumberSymbol;
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.GroupedNumbers;
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbol;
import com.coreman2200.ringstrings.domain.symbol.chart.NumerologicalChartImpl;

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

@RunWith(RobolectricTestRunner.class)

public class TestSymbolStrata {

    final IProfileDataBundle mTestProfile = new ProfileDataBundleAdapter(TestDefaultDataBundles.testProfileBundleCoryH);
    IGroupedNumberSymbolsInputProcessor mTestProcessor;
    private Activity mTestActivity;
    private RingStringsAppSettings mAppSettings;

    @Before
    public void setup() {
        mTestActivity = Robolectric.setupActivity(RingStringsActivity.class);
        mAppSettings = TestDefaultDataBundles.produceDefaultAppSettingsBundle(mTestActivity);
        mTestProcessor = new NumerologicalChartProcessor(mAppSettings);
    }

    @Test
    public void testAllStrataForNumberSymbols() {
        BaseNumbers eight = BaseNumbers.EIGHT;
        INumberSymbol[] numberSymbols = {new BaseNumberSymbol(eight),
                                        new DerivedNumberSymbol(eight, eight, eight),
                                        new GroupedNumberSymbolsImpl(GroupedNumbers.QUALITIES),
                                        new ListedNumberSymbolsImpl(GroupedNumbers.KARMICLESSON),
                                        new NumerologicalChartImpl(),
                                        new RelatedNumberSymbolsImpl()};

        for (INumberSymbol numbersymbol : numberSymbols)
            SymbolStrata.getSymbolStrataFor(numbersymbol.getNumberSymbolStrata());

    }

    @Test
    public void testNumerologicalChartStratas() {
        mTestProcessor.produceGroupedNumberSymbolsForProfile(mTestProfile);
    }

}
