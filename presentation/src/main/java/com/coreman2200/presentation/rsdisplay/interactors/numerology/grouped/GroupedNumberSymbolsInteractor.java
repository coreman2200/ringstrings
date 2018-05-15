package com.coreman2200.presentation.rsdisplay.interactors.numerology.grouped;

import com.coreman2200.presentation.rsdisplay.interactors.numerology.NumberSymbolInteractorImpl;
import com.coreman2200.domain.model.systems.numbers.NumberSystemType;
import com.coreman2200.domain.model.profiles.interfaces.IProfileDataBundle;
import com.coreman2200.domain.model.protos.RingStringsAppSettings;
import com.coreman2200.domain.model.symbols.numbers.grouped.GroupedNumberSymbols;
import com.coreman2200.domain.model.symbols.numbers.interfaces.IGroupedNumberSymbols;

/**
 * GroupedNumberSymbolsInteractor
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

abstract public class GroupedNumberSymbolsInteractor extends NumberSymbolInteractorImpl implements IGroupedNumberSymbolsInteractor {
    protected IProfileDataBundle userProfile;

    protected GroupedNumberSymbolsInteractor(RingStringsAppSettings settings) {
        super(settings);
        setNumberSystem(getNumberSystemType());
    }

    private NumberSystemType getNumberSystemType() {
        final int ordinal = mAppSettings.num.number_system.getValue();
        return NumberSystemType.values()[ordinal];
    }

    abstract public IGroupedNumberSymbols produceGroupedNumberSymbolsForProfile(IProfileDataBundle profile);


    //QUALITIES, CHALLENGES, PERIODS, PINNACLES, PERSONAL, KARMICLESSON, HIDDENPASSIONS, CHART, RELATIONAL

    final static public GroupedNumberSymbolsInteractor getProcessor(RingStringsAppSettings settings, GroupedNumberSymbols type) {
        switch (type) {
            case QUALITIES:
                return new QualitiesInteractor(settings);
            case CHALLENGES:
                return new ChallengesInteractor(settings);
            case PERIODS:
                return new PeriodsInteractor(settings);
            case PERSONAL:
                return new PersonalsInteractor(settings);
            case PINNACLES:
                return new PinnaclesInteractor(settings);
            default:
                throw new RuntimeException("Improper GroupedSymbol type. Processor unavailable");
        }
    }
}
