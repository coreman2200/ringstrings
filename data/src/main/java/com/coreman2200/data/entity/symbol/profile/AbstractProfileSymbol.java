package com.coreman2200.data.entity.symbol.profile;

import com.coreman2200.data.adapter.symbolid.SymbolIdBundleAdapter;
import com.coreman2200.domain.adapter.profiledata.IProfileDataBundle;
import com.coreman2200.domain.protos.SymbolIDBundle;
import com.coreman2200.domain.symbol.AbstractSymbol;
import com.coreman2200.domain.symbol.chart.Charts;
import com.coreman2200.domain.symbol.symbolinterface.IProfileSymbol;
import com.coreman2200.data.entity.symbol.AbstractEntitySymbol;
import com.coreman2200.domain.symbol.strata.EntityStrata;
import com.coreman2200.domain.symbol.symbolinterface.ILightSymbol;
import com.coreman2200.data.entity.symbol.lights.LightSymbolImpl;
import com.coreman2200.domain.symbol.symbolinterface.IChartedSymbols;
import com.coreman2200.domain.symbol.symbolinterface.IRelatedSymbolMapping;
import com.coreman2200.domain.symbol.symbolinterface.ISymbol;

import java.util.Collection;

/**
 * AbstractProfileSymbol
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

public class AbstractProfileSymbol extends AbstractEntitySymbol<ISymbol> implements IProfileSymbol {
    protected IProfileDataBundle mProfileData;

    protected AbstractProfileSymbol(IProfileDataBundle profile, Collection<IChartedSymbols> data) {
        super(new GroupedChartSymbolsImpl(data));
        mProfileData = profile;
    }

    @Override
    public ILightSymbol produceLightSymbolFor(ISymbol symbol) {
        assert (containsSymbol(symbol));

        SymbolIDBundle bundle = produceIdBundleForSymbol(symbol);
        return new LightSymbolImpl(bundle, symbol);
    }

    public SymbolIDBundle getIdBundle() {
        return SymbolIdBundleAdapter.fromSymbol(this, mProfileData.getProfileId(), Charts.NO_CHART.ordinal())
                .getIdBundle();
    }

    private SymbolIDBundle produceIdBundleForSymbol(ISymbol symbol) {
        return SymbolIdBundleAdapter.fromSymbol(symbol, mProfileData.getProfileId(), getChartForSymbol(symbol).ordinal())
                .getIdBundle();
    }

    private Charts getChartForSymbol(ISymbol symbol) {
        if (symbol instanceof IChartedSymbols)
            return ((IChartedSymbols) symbol).getChartType();

        for (Charts chart : Charts.values()) {
            try {
                IChartedSymbols chartsymbol = (IChartedSymbols) getSymbolDataForKey(chart);
                if (chartsymbol != null && chartsymbol.containsSymbol(symbol))
                    return chart;
            } catch (NullPointerException e) {}
        }
        System.out.print(symbol.name() + " has no chart? "+ symbol.symbolType().toString() + " - ");
        System.out.println(symbol.symbolStrata());
        return Charts.NO_CHART;
    }

    @Override
    public ISymbol getSymbol() {
        return mSymbol;
    }

    @Override
    protected void setSymbolStrata() {
        mSymbolStrata = EntityStrata.PROFILE;
    }

    @Override
    public String name() {
        return mProfileData.getDisplayName();
    }

    public String getName() {
        return name();
    }
    public String getDescription() {
        return "Birthdate: " + mProfileData.getBirthDate().toString();
    }

    // TODO: Meh. I see where I was going with this, now but don't really like.
    // TODO: Should eventually clarify this need for a symbol object to instantiate a symbol object..
    //
    private static class GroupedChartSymbolsImpl extends AbstractSymbol<IChartedSymbols> implements IRelatedSymbolMapping {

        public GroupedChartSymbolsImpl(Collection<IChartedSymbols> charts) {
            super(EntityStrata.PROFILE);
            addCharts(charts);
        }

        public void addChart(IChartedSymbols chart) {
            addSymbolDataForKey(chart.getChartType(), chart);
        }

        public void addCharts(Collection<IChartedSymbols> charts) {
            for (IChartedSymbols chart : charts)
                addChart(chart);
        }

        @Override
        protected void setSymbolStrata() {
            mSymbolStrata = EntityStrata.PROFILE;
        }
    }

    @Override
    public boolean saveProfile() {
        return false;
    }
}
