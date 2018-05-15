package com.coreman2200.domain.model.symbols;

import org.junit.Before;
import org.junit.Test;

import com.coreman2200.domain.model.profiles.ProfileDataBundle;
import com.coreman2200.domain.model.profiles.interfaces.IProfileDataBundle;
import com.coreman2200.domain.profiledata.MockDefaultDataBundles;
import com.coreman2200.domain.model.protos.RingStringsAppSettings;
import com.coreman2200.domain.model.symbols.strata.SymbolStrata;
import com.coreman2200.domain.model.symbols.numbers.impl.BaseNumberSymbolImpl;
import com.coreman2200.domain.model.symbols.numbers.grouped.BaseNumberSymbols;
import com.coreman2200.domain.model.symbols.numbers.impl.DerivedNumberSymbolImpl;
import com.coreman2200.domain.model.symbols.numbers.grouped.GroupedNumberSymbols;
import com.coreman2200.domain.model.symbols.numbers.impl.GroupedNumberSymbolsImpl;
import com.coreman2200.domain.model.symbols.numbers.interfaces.INumberSymbol;
import com.coreman2200.domain.model.symbols.numbers.impl.ListedNumberSymbolsImpl;
import com.coreman2200.domain.model.symbols.charts.NumerologicalChartImpl;
import com.coreman2200.domain.model.symbols.numbers.impl.RelatedNumberSymbolsImpl;

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

    final IProfileDataBundle mTestProfile = new ProfileDataBundle(MockDefaultDataBundles.testProfileBundleCoryH);
    private RingStringsAppSettings mAppSettings;

    @Before
    public void setup() {
        mAppSettings = MockDefaultDataBundles.produceDefaultAppSettingsBundle();
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

}
