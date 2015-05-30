package com.coreman2200.ringstrings.symbol;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import com.coreman2200.ringstrings.BuildConfig;
import com.coreman2200.ringstrings.profile.IProfile;
import com.coreman2200.ringstrings.profile.TestProfileImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.NumerologicalChartProcessorImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.IGroupedNumberSymbolsInputProcessor;
import com.coreman2200.ringstrings.symbol.numbersymbol.BaseNumberSymbolImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.BaseNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.DerivedNumberSymbolImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.GroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.GroupedNumberSymbolsImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.IGroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.INumberSymbol;
import com.coreman2200.ringstrings.symbol.numbersymbol.ListedNumberSymbolsImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.NumerologicalChartImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.RelatedNumberSymbolsImpl;

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

    final IProfile mTestProfile = new TestProfileImpl();
    final IGroupedNumberSymbolsInputProcessor mTestProcessor = new NumerologicalChartProcessorImpl(mTestProfile);

    @Test
    public void testAllStrataForNumberSymbols() {
        BaseNumberSymbols eight = BaseNumberSymbols.EIGHT;
        INumberSymbol[] numberSymbols = {new BaseNumberSymbolImpl(eight),
                                        new DerivedNumberSymbolImpl(eight, eight, eight),
                                        new GroupedNumberSymbolsImpl(GroupedNumberSymbols.QUALITIES),
                                        new ListedNumberSymbolsImpl(GroupedNumberSymbols.KARMIC_LESSONS),
                                        new NumerologicalChartImpl(),
                                        new RelatedNumberSymbolsImpl()};

        for (INumberSymbol numbersymbol : numberSymbols)
            SymbolStrata.getSymbolStrataFor(numbersymbol.getNumberSymbolStrata());

    }

    @Test
    public void testNumerologicalChartStratas() {
        IGroupedNumberSymbols grouped = mTestProcessor.produceGroupedNumberSymbolsForProfile();
    }

}
