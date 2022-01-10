package com.coreman2200.ringstrings.domain.symbol.chart;

import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.GroupedNumbers;
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.NumberStrata;
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.IChartedNumberSymbols;
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbol;
import com.coreman2200.ringstrings.domain.symbol.comparator.ChainedSymbolComparatorImpl;
import com.coreman2200.ringstrings.domain.symbol.comparator.NumberSymbolOrderComparatorImpl;
import com.coreman2200.ringstrings.domain.symbol.comparator.SymbolStrataComparatorImpl;

import java.util.Collection;

/**
 * NumerologicalChartImpl
 * Describes entire numerological chart as groups of numerological symbols.
 *
 * Created by Cory Higginbottom on 5/28/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class NumerologicalChartImpl extends GroupedNumberSymbolsImpl implements IChartedNumberSymbols {
    private final Charts mChartType = Charts.NUMEROLOGICAL;

    public NumerologicalChartImpl() {
        super(GroupedNumbers.CHART);
        // TODO: Chains?? These aren't even tested to function properly, so.. Supertest code..
        ChainedSymbolComparatorImpl comparator = new ChainedSymbolComparatorImpl(new SymbolStrataComparatorImpl(),
                new NumberSymbolOrderComparatorImpl());
        setSymbolComparator(comparator);
    }

    public void testGenerateLoggingsForFullChart() {
        Collection<INumberSymbol> chart = getSortedSymbols(RelatedSymbolMap.SortOrder.ASCENDING);

        System.out.println(name() + " Numerological Chart");
        testGenerateLogs();
    }

    public Charts getChartType() {
        return mChartType;
    }

    @Override
    protected void setSymbolStrata() {
        this.mSymbolStrata = NumberStrata.CHARTEDNUMBERS;
    }
}
