package com.coreman2200.ringstrings.symbol.inputprocessor.numerology;

import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.protos.RingStringsAppSettings;
import com.coreman2200.ringstrings.symbol.chart.NumerologicalChartImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.GroupedNumberSymbolsInputProcessor;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.IGroupedNumberSymbolsInputProcessor;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.GroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.IChartedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.IGroupedNumberSymbols;

/**
 * NumerologicalChartProcessor
 * Builds a full Numerological Chart for a Profile.
 *
 * Created by Cory Higginbottom on 5/28/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class NumerologicalChartProcessor extends GroupedNumberSymbolsInputProcessor implements IGroupedNumberSymbolsInputProcessor {

    public NumerologicalChartProcessor(RingStringsAppSettings settings) {
        super(settings);
    }

    public IChartedNumberSymbols produceGroupedNumberSymbolsForProfile(IProfileDataBundle profile) {
        assert (profile != null);
        userProfile = profile;
        IChartedNumberSymbols chart = new NumerologicalChartImpl();

        // Grouped Symbols that establish the full number symbol
        final GroupedNumberSymbols[] groupedSymbols = { GroupedNumberSymbols.QUALITIES, GroupedNumberSymbols.CHALLENGES,
                                                        GroupedNumberSymbols.PERIODS, GroupedNumberSymbols.PERSONAL,
                                                        GroupedNumberSymbols.PINNACLES };

        for (GroupedNumberSymbols type : groupedSymbols)
            chart.addNumberSymbol(type, getGroupedNumberSymbolsForType(type));

        return chart;
    }

    private IGroupedNumberSymbols getGroupedNumberSymbolsForType(GroupedNumberSymbols type) {
        return genProcessorType(type).produceGroupedNumberSymbolsForProfile(userProfile);
    }
    
    protected IGroupedNumberSymbolsInputProcessor genProcessorType(GroupedNumberSymbols type) {
        return GroupedNumberSymbolsInputProcessor.getProcessor(mAppSettings, type);
    }

}
