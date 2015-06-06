package com.coreman2200.ringstrings.symbol.inputprocessor.numerology;

import com.coreman2200.ringstrings.profile.IProfile;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.ChallengesProcessorImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.GroupedNumberSymbolsInputProcessorImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.IGroupedNumberSymbolsInputProcessor;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.PeriodsProcessorImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.PersonalsProcessorImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.PinnaclesProcessorImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped.QualitiesProcessorImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.GroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.impl.GroupedNumberSymbolsImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.IGroupedNumberSymbols;

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

    public NumerologicalChartProcessorImpl(IProfile profile) {
        super();
        setUserProfileAndNumberSystem(profile);
    }

    protected IGroupedNumberSymbolsInputProcessor genProcessorType(GroupedNumberSymbols type) {
        switch (type) {
            case QUALITIES:
                return prepProcessor(new QualitiesProcessorImpl());
            case CHALLENGES:
                return prepProcessor(new ChallengesProcessorImpl());
            case PERIODS:
                return prepProcessor(new PeriodsProcessorImpl());
            case PERSONAL:
                return prepProcessor(new PersonalsProcessorImpl());
            case PINNACLES:
                return prepProcessor(new PinnaclesProcessorImpl());
            default:
                throw new RuntimeException("Improper GroupedSymbol type. Processor unavailable");
        }
    }

    private IGroupedNumberSymbolsInputProcessor prepProcessor(GroupedNumberSymbolsInputProcessorImpl processor) {
        processor.setUserProfileAndNumberSystem(userProfile);
        return processor;
    }

    public IGroupedNumberSymbols produceGroupedNumberSymbolsForProfile() {
        IGroupedNumberSymbols chart = new GroupedNumberSymbolsImpl(GroupedNumberSymbols.CHART);

        chart.addNumberSymbol(GroupedNumberSymbols.QUALITIES, getQualities());
        chart.addNumberSymbol(GroupedNumberSymbols.CHALLENGES, getChallenges());
        chart.addNumberSymbol(GroupedNumberSymbols.PERIODS, getPeriods());
        chart.addNumberSymbol(GroupedNumberSymbols.PERSONAL, getPersonals());
        chart.addNumberSymbol(GroupedNumberSymbols.PINNACLES, getPinnacles());
        return chart;
    }

    private IGroupedNumberSymbols getQualities() {
        return genProcessorType(GroupedNumberSymbols.QUALITIES).produceGroupedNumberSymbolsForProfile();
    }

    private IGroupedNumberSymbols getChallenges() {
        return genProcessorType(GroupedNumberSymbols.CHALLENGES).produceGroupedNumberSymbolsForProfile();
    }

    private IGroupedNumberSymbols getPinnacles() {
        return genProcessorType(GroupedNumberSymbols.PINNACLES).produceGroupedNumberSymbolsForProfile();
    }

    private IGroupedNumberSymbols getPeriods() {
        return genProcessorType(GroupedNumberSymbols.PERIODS).produceGroupedNumberSymbolsForProfile();
    }

    private IGroupedNumberSymbols getPersonals() {
        return genProcessorType(GroupedNumberSymbols.PERSONAL).produceGroupedNumberSymbolsForProfile();
    }

}
