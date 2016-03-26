package com.coreman2200.domain.symbol.chart;

import com.coreman2200.domain.symbol.astralsymbol.AstralStrata;
import com.coreman2200.domain.symbol.astralsymbol.grouped.AstralCharts;
import com.coreman2200.domain.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.domain.symbol.astralsymbol.grouped.Houses;
import com.coreman2200.domain.symbol.astralsymbol.grouped.Zodiac;
import com.coreman2200.domain.symbol.astralsymbol.impl.AstralSymbolImpl;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.IAstralSymbol;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.IChartedAstralSymbols;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.IGroupedAstralSymbols;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.IHouseSymbol;

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
    private final Charts mChartType;

    public AstrologicalChartImpl(AstralCharts chart, double offset) {
        super(chart, offset);
        mChartType = (chart.equals(AstralCharts.NATAL)) ? Charts.ASTRAL_NATAL : Charts.ASTRAL_CURRENT;
    }

    @Override
    protected void setSymbolStrata() {
        mSymbolStrata = AstralStrata.ASTRALCHART;
    }

    public void addAstralMappings(Map<Enum<? extends Enum<?>>, IAstralSymbol> map) {
        addSymbolMap(map);
    }

    public void addAstralSymbol(Enum<? extends Enum<?>> name, IAstralSymbol symbol) {
        addSymbolDataForKey(name, symbol);
    }

    public IAstralSymbol getAstralSymbol(Enum<? extends Enum<?>> name) {
        return getSymbolDataForKey(name);
    }

    public void testGenerateLoggingsForFullChart() {
        Collection<IAstralSymbol> chart = getAllSymbols();

        System.out.println(name() + " Astrological Chart");
        testGenerateLogs();
    }

    public Charts getChartType() {
        return mChartType;
    }

    public Houses getHouseForBody(CelestialBodies body) {
        double offset = getSymbolDataForKey(Houses.HOUSEI).getAstralSymbolDegree();
        IAstralSymbol bodysymbol = getSymbolDataForKey(body);
        assert (bodysymbol != null);

        double offsetplacement = (bodysymbol.getAstralSymbolDegree() - mDegree) / 30.0;
        double wrappeddegree = (Houses.values().length + offsetplacement) % Houses.values().length;
        int index = (int)wrappeddegree;
        Houses house = Houses.values()[index];
        IHouseSymbol housesymbol = (IHouseSymbol)getSymbolDataForKey(house);
        assert (housesymbol.getAstralSymbol(body) != null);
        return house;
    }

    public Zodiac getSignForBody(CelestialBodies body) {
        IAstralSymbol bodysymbol = getSymbolDataForKey(body);
        int index = (int)(bodysymbol.getAstralSymbolDegree() / 30.0);
        Zodiac sign = Zodiac.values()[index];
        IGroupedAstralSymbols signsymbol = (IGroupedAstralSymbols)getSymbolDataForKey(sign);
        assert (signsymbol.getAstralSymbol(body) != null);
        return sign;
    }
}
