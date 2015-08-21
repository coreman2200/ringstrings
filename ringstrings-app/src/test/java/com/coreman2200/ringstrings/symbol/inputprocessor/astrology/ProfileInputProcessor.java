package com.coreman2200.ringstrings.symbol.inputprocessor.astrology;

import com.coreman2200.ringstrings.profile.IProfileTestLoc;
import com.coreman2200.ringstrings.symbol.Charts;
import com.coreman2200.ringstrings.symbol.IChartedSymbols;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.NumerologicalChartProcessorImpl;

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

public class ProfileInputProcessor {
    private IProfileTestLoc mProfile;
    private final String mEphemerisDirectoryPath; // TODO: Inappropriate to place here..
    LinkedList<IChartedSymbols> mCharts;

    public ProfileInputProcessor(IProfileTestLoc profile, String ephedir) {
        mProfile = profile;
        mEphemerisDirectoryPath = ephedir;
    }

    public LinkedList<IChartedSymbols> produceUserCharts() {
        mCharts = new LinkedList<>();
        AstrologicalChartInputProcessorImpl astroprocessor = new AstrologicalChartInputProcessorImpl(mProfile, mEphemerisDirectoryPath);
        mCharts.add(astroprocessor.produceAstrologicalChart(Charts.ASTRAL_NATAL));
        mCharts.add(astroprocessor.produceAstrologicalChart(Charts.ASTRAL_CURRENT));

        NumerologicalChartProcessorImpl numberprocessor = new NumerologicalChartProcessorImpl(mProfile);
        mCharts.add(numberprocessor.produceGroupedNumberSymbolsForProfile());

        return mCharts;
    }

    public void testGenerateLog() {
        for (IChartedSymbols chart : mCharts)
            chart.testGenerateLoggingsForFullChart();
    }

}
