package com.coreman2200.ringstrings.symbol.entitysymbol.Profile;

import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.protos.RingStringsAppSettings;
import com.coreman2200.ringstrings.symbol.AbstractSymbol;
import com.coreman2200.ringstrings.symbol.SymbolStrata;
import com.coreman2200.ringstrings.symbol.entitysymbol.AbstractEntitySymbol;
import com.coreman2200.ringstrings.symbol.entitysymbol.EntityStrata;
import com.coreman2200.ringstrings.symbol.entitysymbol.Tags.IThemeSymbol;
import com.coreman2200.ringstrings.symbol.inputprocessor.ProfileInputProcessor;
import com.coreman2200.ringstrings.symbol.symbolinterface.IChartedSymbols;
import com.coreman2200.ringstrings.symbol.symbolinterface.IRelatedSymbolMapping;
import com.coreman2200.ringstrings.symbol.symbolinterface.ISymbol;

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

public class AbstractProfileSymbol extends AbstractEntitySymbol<IThemeSymbol> implements IProfileSymbol {
    protected IProfileDataBundle mProfile;

    protected AbstractProfileSymbol(IProfileDataBundle profile, RingStringsAppSettings settings) {
        super(produceSymbolForProfile(profile, settings));
        mProfile = profile;
        produceQualities();
    }

    private static ISymbol produceSymbolForProfile(IProfileDataBundle profile, RingStringsAppSettings settings) {
        ProfileInputProcessor processor = new ProfileInputProcessor(profile, settings);
        return new GroupedChartSymbolsImpl(processor.produceUserCharts());
    }

    @Override
    protected void setSymbolStrata() {
        mSymbolStrata = EntityStrata.PROFILE;
    }

    @Override
    public String name() { // TODO: testy
        return mProfile.getFirstName() + " " + mProfile.getMiddleName() + " " + mProfile.getLastName();
    }

    public String getName() {
        return name();
    }
    public String getDescription() {
        return "Birthdate: " + mProfile.getBirthDate().toString();
    }

    private static class GroupedChartSymbolsImpl extends AbstractSymbol<IChartedSymbols> implements IRelatedSymbolMapping {

        public GroupedChartSymbolsImpl(Collection<IChartedSymbols> charts) {
            super(SymbolStrata.RELATIONAL_MAP);
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
            mSymbolStrata = SymbolStrata.RELATIONAL_MAP;
        }
    }
}
