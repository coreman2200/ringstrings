package com.coreman2200.ringstrings.symbol.inputprocessor;

import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.protos.RingStringsAppSettings;
import com.coreman2200.ringstrings.symbol.chart.Charts;
import com.coreman2200.ringstrings.symbol.inputprocessor.astrology.AstrologicalChartInputProcessorImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.NumerologicalChartProcessor;
import com.coreman2200.ringstrings.symbol.symbolinterface.IChartedSymbols;

import java.util.Collection;
import java.util.LinkedList;

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
    Collection<IChartedSymbols> mCharts;

    public ProfileInputProcessor(IProfileDataBundle profile, RingStringsAppSettings settings) {
        super(settings);
        mProfileData = profile;
    }

    public Collection<IChartedSymbols> produceUserCharts() {
        mCharts = new LinkedList<>();
        AstrologicalChartInputProcessorImpl astroprocessor = new AstrologicalChartInputProcessorImpl(mProfileData, mAppSettings);
        mCharts.add(astroprocessor.produceAstrologicalChart(Charts.ASTRAL_NATAL));
        mCharts.add(astroprocessor.produceAstrologicalChart(Charts.ASTRAL_CURRENT));

        NumerologicalChartProcessor numberprocessor = new NumerologicalChartProcessor(mProfileData, mAppSettings);
        mCharts.add(numberprocessor.produceGroupedNumberSymbolsForProfile());

        return mCharts;
    }

}
