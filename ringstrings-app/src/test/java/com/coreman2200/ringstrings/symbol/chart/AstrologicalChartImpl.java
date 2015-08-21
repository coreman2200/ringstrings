package com.coreman2200.ringstrings.symbol.chart;

import com.coreman2200.ringstrings.symbol.Charts;
import com.coreman2200.ringstrings.symbol.RelatedSymbolMap;
import com.coreman2200.ringstrings.symbol.astralsymbol.AstralStrata;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Houses;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Zodiac;
import com.coreman2200.ringstrings.symbol.astralsymbol.impl.AstralSymbolImpl;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IAstralSymbol;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IChartedAstralSymbols;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IGroupedAstralSymbols;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IHouseSymbol;
import com.coreman2200.ringstrings.symbol.symbolcomparator.AstralSymbolDegreeComparatorImpl;

import java.util.Collection;
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
    private final Charts mChartType;

    public AstrologicalChartImpl(Charts chart, double offset) {
        super(chart, offset);
        AstralSymbolDegreeComparatorImpl comparator = new AstralSymbolDegreeComparatorImpl(offset);
        mMappedChart = new RelatedSymbolMap<>(comparator);
        mSymbolStrata = AstralStrata.ASTRALCHART;
        mChartType = chart;
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

    public Charts getChartType() {
        return mChartType;
    }

    @Override
    public int size() {
        return mMappedChart.size();
    }

    public Houses getHouseForBody(CelestialBodies body) {
        double offset = mMappedChart.get(Houses.HOUSEI).getAstralSymbolDegree();
        IAstralSymbol bodysymbol = mMappedChart.get(body);
        assert (bodysymbol != null);

        double offsetplacement = (bodysymbol.getAstralSymbolDegree() - mDegree) / 30.0;
        double wrappeddegree = (Houses.values().length + offsetplacement) % Houses.values().length;
        int index = (int)wrappeddegree;
        Houses house = Houses.values()[index];
        IHouseSymbol housesymbol = (IHouseSymbol)mMappedChart.get(house);
        housesymbol.testGenerateLoggings();
        assert (housesymbol.getAstralSymbol(body) != null);
        return house;
    }

    public Zodiac getSignForBody(CelestialBodies body) {
        IAstralSymbol bodysymbol = mMappedChart.get(body);
        int index = (int)(bodysymbol.getAstralSymbolDegree() / 30.0);
        Zodiac sign = Zodiac.values()[index];
        IGroupedAstralSymbols signsymbol = (IGroupedAstralSymbols)mMappedChart.get(sign);
        assert (signsymbol.getAstralSymbol(body) != null);
        return sign;
    }
}
