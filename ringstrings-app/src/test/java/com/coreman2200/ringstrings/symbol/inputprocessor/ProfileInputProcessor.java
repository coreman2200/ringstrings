package com.coreman2200.ringstrings.symbol.inputprocessor;

import android.database.Cursor;

import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.protos.RingStringsAppSettings;
import com.coreman2200.ringstrings.rsprovider.RingStringsContract;
import com.coreman2200.ringstrings.symbol.chart.Charts;
import com.coreman2200.ringstrings.symbol.entitysymbol.EntityStrata;
import com.coreman2200.ringstrings.symbol.entitysymbol.Profile.IProfileSymbol;
import com.coreman2200.ringstrings.symbol.entitysymbol.Profile.LocalProfileSymbolImpl;
import com.coreman2200.ringstrings.symbol.entitysymbol.Profile.UserProfileSymbolImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.astrology.AstrologicalChartInputProcessorImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.NumerologicalChartProcessor;
import com.coreman2200.ringstrings.symbol.symbolinterface.IChartedSymbols;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

/**
 * ProfileInputProcessor
 * Produces relational symbol map for profile.
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

public class ProfileInputProcessor extends AbstractInputProcessor {

    private IProfileDataBundle mProfileData;

    public ProfileInputProcessor(RingStringsAppSettings settings) {
        super(settings);
    }

    public IProfileSymbol produceProfile(IProfileDataBundle profile, EntityStrata type) {
        mProfileData = profile;

        switch (type) {
            case USER:
                return new UserProfileSymbolImpl(mProfileData, produceProfileCharts());
            default:
                return new LocalProfileSymbolImpl(mProfileData, produceProfileCharts());
        }
    }

    private Collection<IChartedSymbols> produceProfileCharts() {
        Collection<IChartedSymbols> mCharts = new LinkedList<>();
        AstrologicalChartInputProcessorImpl astroprocessor = new AstrologicalChartInputProcessorImpl(mAppSettings);
        mCharts.add(astroprocessor.produceAstrologicalChart(mProfileData, Charts.ASTRAL_NATAL));
        mCharts.add(astroprocessor.produceAstrologicalChart(mProfileData, Charts.ASTRAL_CURRENT));

        NumerologicalChartProcessor numberprocessor = new NumerologicalChartProcessor(mAppSettings);
        mCharts.add(numberprocessor.produceGroupedNumberSymbolsForProfile(mProfileData));

        return mCharts;
    }

}
