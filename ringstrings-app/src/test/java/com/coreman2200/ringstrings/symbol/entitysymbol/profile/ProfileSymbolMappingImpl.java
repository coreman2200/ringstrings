package com.coreman2200.ringstrings.symbol.entitysymbol.profile;

import com.coreman2200.ringstrings.profile.IProfileTestLoc;
import com.coreman2200.ringstrings.symbol.AbstractSymbol;
import com.coreman2200.ringstrings.symbol.chart.Charts;
import com.coreman2200.ringstrings.symbol.entitysymbol.EntityStrata;
import com.coreman2200.ringstrings.symbol.symbolinterface.IChartedSymbols;
import com.coreman2200.ringstrings.symbol.RelatedSymbolMap;

import java.util.Collection;

/**
 * ProfileSymbolMappingImpl
 * Profile Symbol Map is a relational map for all symbols in one's available charts
 *
 * Created by Cory Higginbottom on 6/10/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class ProfileSymbolMappingImpl extends AbstractSymbol<IChartedSymbols> {
    protected IProfileTestLoc mProfile;

    protected ProfileSymbolMappingImpl(EntityStrata type, IProfileTestLoc profile) {
        super(type);
        mProfile = profile;
    }

    @Override
    protected void setSymbolStrata() {
        mSymbolStrata = EntityStrata.PROFILE;
    }

    public void addChart(IChartedSymbols chart) {
        addSymbolDataForKey(chart.getChartType(), chart);
    }

    public void addCharts(Collection<IChartedSymbols> charts) {
        for (IChartedSymbols chart : charts)
            addChart(chart);
    }

    public IChartedSymbols getChart(Charts type) {
        return getSymbolDataForKey(type);
    }

    @Override
    public String name() { // TODO: testy
        return mProfile.getFirstName() + " " + mProfile.getMiddleName() + " " + mProfile.getLastName();
    }

}
