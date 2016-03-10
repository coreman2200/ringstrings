package com.coreman2200.ringstrings.symbol.entitysymbol.Profile;

import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.protos.RingStringsAppSettings;
import com.coreman2200.ringstrings.protos.SymbolIDBundle;
import com.coreman2200.ringstrings.symbol.AbstractSymbol;
import com.coreman2200.ringstrings.symbol.RelatedSymbolMap;
import com.coreman2200.ringstrings.symbol.SymbolStrata;
import com.coreman2200.ringstrings.symbol.chart.Charts;
import com.coreman2200.ringstrings.symbol.entitysymbol.AbstractEntitySymbol;
import com.coreman2200.ringstrings.symbol.entitysymbol.EntityStrata;
import com.coreman2200.ringstrings.symbol.entitysymbol.Lights.ILightSymbol;
import com.coreman2200.ringstrings.symbol.entitysymbol.Lights.LightSymbolImpl;
import com.coreman2200.ringstrings.symbol.entitysymbol.Tags.IThemeSymbol;
import com.coreman2200.ringstrings.symbol.entitysymbol.Tags.TagSymbols;
import com.coreman2200.ringstrings.symbol.inputprocessor.ProfileInputProcessor;
import com.coreman2200.ringstrings.symbol.symbolinterface.IChartedSymbols;
import com.coreman2200.ringstrings.symbol.symbolinterface.IRelatedSymbolMapping;
import com.coreman2200.ringstrings.symbol.symbolinterface.ISymbol;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
        return new SymbolIDBundle.Builder()
                .profile_id(mProfileData.getProfileId())
                .strata_id(symbolStrata().ordinal())
                .type_id(symbolType().ordinal())
                .symbol_id(symbolID().ordinal())
                .build();
    }

    private SymbolIDBundle produceIdBundleForSymbol(ISymbol symbol) {

        return new SymbolIDBundle.Builder()
                .profile_id(mProfileData.getProfileId())
                .chart_id(getChartForSymbol(symbol).ordinal())
                .strata_id(symbol.symbolStrata().ordinal())
                .type_id(symbol.symbolType().ordinal())
                .symbol_id(symbol.symbolID().ordinal())
                .desc_id(symbol.symbolID().ordinal())
                .name(symbol.name())
                .build();
    }

    private Charts getChartForSymbol(ISymbol symbol) {

        for (Charts chart : Charts.values()) {
            try {
                IChartedSymbols chartsymbol = (IChartedSymbols) getSymbolDataForKey(chart);
                if (chartsymbol != null && chartsymbol.containsSymbol(symbol))
                    return chart;
            } catch (NullPointerException e) {}
        }
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
    public String name() { // TODO: testy
        return mProfileData.getFirstName() + " " + mProfileData.getMiddleName() + " " + mProfileData.getLastName();
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
