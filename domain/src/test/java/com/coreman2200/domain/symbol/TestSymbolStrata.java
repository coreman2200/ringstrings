package com.coreman2200.domain.symbol;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import com.coreman2200.data.entity.profiledata.IProfileDataBundle;
import com.coreman2200.data.entity.profiledata.ProfileDataBundleAdapter;
import com.coreman2200.domain.com.coreman2200.domain.profiledata.MockDefaultDataBundles;
import com.coreman2200.data.entity.protos.RingStringsAppSettings;
import com.coreman2200.ringstrings.rsdisplay.activity.RingStringsActivity;
import com.coreman2200.domain.symbol.inputprocessor.numerology.NumerologicalChartProcessor;
import com.coreman2200.domain.symbol.inputprocessor.numerology.grouped.IGroupedNumberSymbolsInputProcessor;
import com.coreman2200.domain.symbol.numbersymbol.impl.BaseNumberSymbolImpl;
import com.coreman2200.domain.symbol.numbersymbol.grouped.BaseNumberSymbols;
import com.coreman2200.domain.symbol.numbersymbol.impl.DerivedNumberSymbolImpl;
import com.coreman2200.domain.symbol.numbersymbol.grouped.GroupedNumberSymbols;
import com.coreman2200.domain.symbol.numbersymbol.impl.GroupedNumberSymbolsImpl;
import com.coreman2200.domain.symbol.numbersymbol.interfaces.INumberSymbol;
import com.coreman2200.domain.symbol.numbersymbol.impl.ListedNumberSymbolsImpl;
import com.coreman2200.domain.symbol.chart.NumerologicalChartImpl;
import com.coreman2200.domain.symbol.numbersymbol.impl.RelatedNumberSymbolsImpl;

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

public class TestSymbolStrata {

    final IProfileDataBundle mTestProfile = new ProfileDataBundleAdapter(MockDefaultDataBundles.testProfileBundleCoryH);
    IGroupedNumberSymbolsInputProcessor mTestProcessor;
    private RingStringsAppSettings mAppSettings;

    @Before
    public void setup() {
        mAppSettings = MockDefaultDataBundles.produceDefaultAppSettingsBundle();
        mTestProcessor = new NumerologicalChartProcessor(mAppSettings);
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
        mTestProcessor.produceGroupedNumberSymbolsForProfile(mTestProfile);
    }

}
