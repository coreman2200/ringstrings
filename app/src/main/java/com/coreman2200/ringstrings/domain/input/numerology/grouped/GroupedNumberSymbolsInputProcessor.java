package com.coreman2200.ringstrings.domain.input.numerology.grouped;

import com.coreman2200.ringstrings.domain.numbersystem.NumberSystemType;
import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.domain.input.numerology.NumberSymbolInputProcessorImpl;
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.GroupedNumbers;

/**
 * GroupedNumberSymbolsInputProcessor
 * Base class for grouped number symbol input processors used to produce a chart.
 *
 * Created by Cory Higginbottom on 5/26/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

abstract public class GroupedNumberSymbolsInputProcessor extends NumberSymbolInputProcessorImpl implements IGroupedNumberSymbolsInputProcessor {
    protected IProfileDataBundle userProfile;

    protected GroupedNumberSymbolsInputProcessor(RingStringsAppSettings settings) {
        super(settings);
        setNumberSystem(getNumberSystemType());
    }

    private NumberSystemType getNumberSystemType() {
        final int ordinal = mAppSettings.num.number_system.getValue();
        return NumberSystemType.values()[ordinal];
    }

    abstract public IGroupedNumberSymbols produceGroupedNumberSymbolsForProfile(IProfileDataBundle profile);


    //QUALITIES, CHALLENGES, PERIODS, PINNACLES, PERSONAL, KARMICLESSON, HIDDENPASSIONS, CHART, RELATIONAL

    final static public GroupedNumberSymbolsInputProcessor getProcessor(RingStringsAppSettings settings, GroupedNumbers type) {
        switch (type) {
            case QUALITIES:
                return new QualitiesProcessor(settings);
            case CHALLENGES:
                return new ChallengesProcessor(settings);
            case PERIODS:
                return new PeriodsProcessor(settings);
            case PERSONAL:
                return new PersonalsProcessor(settings);
            case PINNACLES:
                return new PinnaclesProcessor(settings);
            default:
                throw new RuntimeException("Improper GroupedSymbol type. Processor unavailable");
        }
    }
}
