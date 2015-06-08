package com.coreman2200.ringstrings.symbol.chart;

import com.coreman2200.ringstrings.symbol.RelatedSymbolMap;
import com.coreman2200.ringstrings.symbol.astralsymbol.AstralStrata;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.AstralCharts;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Houses;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Zodiac;
import com.coreman2200.ringstrings.symbol.astralsymbol.impl.AstralSymbolImpl;
import com.coreman2200.ringstrings.symbol.astralsymbol.impl.GroupedAstralSymbolsImpl;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IAstralSymbol;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IChartedAstralSymbols;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IGroupedAstralSymbols;
import com.coreman2200.ringstrings.symbol.symbolcomparator.AstralSymbolDegreeComparatorImpl;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

/**
 * AstrologicalChartImpl
 * Describes Astrological Chart as Astral Symbols related by their degree within the chart.
 *
 * Created by Cory Higginbottom on 6/7/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class AstrologicalChartImpl extends AstralSymbolImpl implements IGroupedAstralSymbols, IChartedAstralSymbols {
    RelatedSymbolMap<IAstralSymbol> mMappedChart;

    public AstrologicalChartImpl(AstralCharts chart, double offset) {
        super(chart, offset);
        AstralSymbolDegreeComparatorImpl comparator = new AstralSymbolDegreeComparatorImpl(offset);
        mMappedChart = new RelatedSymbolMap(comparator);
        mAstralStrata = AstralStrata.ASTRALCHART;
    }

    public void addAstralMappings(Map<Enum<? extends Enum<?>>, IAstralSymbol> map) {
        mMappedChart.putAll(map);
    }

    public void addAstralSymbol(Enum<? extends Enum<?>> name, IAstralSymbol symbol) {
        mMappedChart.put(name, symbol);
    }

    public IAstralSymbol getAstralSymbol(Enum<? extends Enum<?>> name) {
        return mMappedChart.get(name);
    }

    public void testGenerateLoggingsForFullChart() {
        Collection<IAstralSymbol> chart = mMappedChart.getSortedSymbols(RelatedSymbolMap.SortOrder.ASCENDING);

        System.out.println(name() + " Astrological Chart");
        for (IAstralSymbol symbol : chart) {
            System.out.print(symbol.name());
            System.out.print(" degree: " + symbol.getAstralSymbolDegree());
            System.out.print(" strata: " + symbol.getAstralSymbolStrata());
            System.out.println(" size: " + symbol.size());
        }
    }

    @Override
    public int size() {
        return mMappedChart.size();
    }

    //public Houses getHouseForBody(CelestialBodies body)

    //public Zodiac getSignForBody(CelestialBodies body)
}
