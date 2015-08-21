package com.coreman2200.ringstrings.symbol.chart;

import com.coreman2200.ringstrings.symbol.Charts;
import com.coreman2200.ringstrings.symbol.ISymbol;
import com.coreman2200.ringstrings.symbol.RelatedSymbolMap;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.GroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.NumberStrata;
import com.coreman2200.ringstrings.symbol.numbersymbol.impl.GroupedNumberSymbolsImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.IChartedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.INumberSymbol;
import com.coreman2200.ringstrings.symbol.symbolcomparator.SymbolStrataComparatorImpl;

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
        super(GroupedNumberSymbols.CHART);
    }

    public void testGenerateLoggingsForFullChart() {
        RelatedSymbolMap<INumberSymbol> relatedsymbols = new RelatedSymbolMap<>(new SymbolStrataComparatorImpl());
        relatedsymbols.putAll(mGroupedNumberSymbolsMap);

        Collection<INumberSymbol> chart = relatedsymbols.getSortedSymbols(RelatedSymbolMap.SortOrder.ASCENDING);

        System.out.println(name() + " Numerological Chart");
        for (INumberSymbol symbol : chart) {
            System.out.print(symbol.name());
            System.out.print(" value: " + symbol.getNumberSymbolValue());
            System.out.print(" strata: " + symbol.getNumberSymbolStrata());
            System.out.println(" size: " + symbol.size());
        }
    }

    public Charts getChartType() {
        return mChartType;
    }

    @Override
    protected void setNumberStrata() {
        this.mSymbolStrata = NumberStrata.CHARTEDNUMBERS;
    }
}
