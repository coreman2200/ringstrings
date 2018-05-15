package com.coreman2200.presentation.rsdisplay.interactors.numerology;

import com.coreman2200.presentation.rsdisplay.interactors.numerology.grouped.GroupedNumberSymbolsInteractor;
import com.coreman2200.presentation.rsdisplay.interactors.numerology.grouped.IGroupedNumberSymbolsInteractor;
import com.coreman2200.domain.model.profiles.interfaces.IProfileDataBundle;
import com.coreman2200.domain.model.protos.RingStringsAppSettings;
import com.coreman2200.domain.model.symbols.charts.NumerologicalChartImpl;
import com.coreman2200.domain.model.symbols.numbers.grouped.GroupedNumberSymbols;
import com.coreman2200.domain.model.symbols.numbers.interfaces.IChartedNumberSymbols;
import com.coreman2200.domain.model.symbols.numbers.interfaces.IGroupedNumberSymbols;

/**
 * NumerologicalChartInteractor
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

public class NumerologicalChartInteractor extends GroupedNumberSymbolsInteractor implements IGroupedNumberSymbolsInteractor {

    public NumerologicalChartInteractor(RingStringsAppSettings settings) {
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
    
    protected IGroupedNumberSymbolsInteractor genProcessorType(GroupedNumberSymbols type) {
        return GroupedNumberSymbolsInteractor.getProcessor(mAppSettings, type);
    }

}
