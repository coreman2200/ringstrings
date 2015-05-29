package com.coreman2200.ringstrings.symbol.inputprocessor.numerology;

import com.coreman2200.ringstrings.symbol.IProfile;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.ChallengesProcessorImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.GroupedNumberSymbolsInputProcessorImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.IGroupedNumberSymbolsInputProcessor;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.PeriodsProcessorImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.PersonalsProcessorImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.PinnaclesProcessorImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.QualitiesProcessorImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.GroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.GroupedNumberSymbolsImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.IGroupedNumberSymbols;

/**
 * NumerologicalChartProcessorImpl
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

public class NumerologicalChartProcessorImpl extends GroupedNumberSymbolsInputProcessorImpl implements IGroupedNumberSymbolsInputProcessor {
    private IGroupedNumberSymbolsInputProcessor mProcessor;

    public IGroupedNumberSymbols produceGroupedNumberSymbolsForProfile(IProfile profile) {
        super.produceGroupedNumberSymbolsForProfile(profile);
        IGroupedNumberSymbols chart = new GroupedNumberSymbolsImpl(GroupedNumberSymbols.CHART);

        chart.addNumberSymbol(GroupedNumberSymbols.QUALITIES, getQualities());
        chart.addNumberSymbol(GroupedNumberSymbols.CHALLENGES, getChallenges());
        chart.addNumberSymbol(GroupedNumberSymbols.PERIODS, getPeriods());
        chart.addNumberSymbol(GroupedNumberSymbols.PERSONAL, getPersonals());
        chart.addNumberSymbol(GroupedNumberSymbols.PINNACLES, getPinnacles());
        return chart;
    }

    private IGroupedNumberSymbols getQualities() {
        return new QualitiesProcessorImpl().produceGroupedNumberSymbolsForProfile(userProfile);
    }

    private IGroupedNumberSymbols getChallenges() {
        return new ChallengesProcessorImpl().produceGroupedNumberSymbolsForProfile(userProfile);
    }

    private IGroupedNumberSymbols getPinnacles() {
        return new PinnaclesProcessorImpl().produceGroupedNumberSymbolsForProfile(userProfile);
    }

    private IGroupedNumberSymbols getPeriods() {
        return new PeriodsProcessorImpl().produceGroupedNumberSymbolsForProfile(userProfile);
    }

    private IGroupedNumberSymbols getPersonals() {
        return new PersonalsProcessorImpl().produceGroupedNumberSymbolsForProfile(userProfile);
    }

}
