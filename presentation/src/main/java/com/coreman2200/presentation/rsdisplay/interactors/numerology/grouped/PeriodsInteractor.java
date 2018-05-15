package com.coreman2200.presentation.rsdisplay.interactors.numerology.grouped;

import com.coreman2200.domain.model.profiles.interfaces.IProfileDataBundle;
import com.coreman2200.domain.model.protos.RingStringsAppSettings;
import com.coreman2200.domain.model.symbols.numbers.grouped.GroupedNumberSymbols;
import com.coreman2200.domain.model.symbols.numbers.impl.GroupedNumberSymbolsImpl;
import com.coreman2200.domain.model.symbols.numbers.interfaces.IGroupedNumberSymbols;
import com.coreman2200.domain.model.symbols.numbers.interfaces.INumberSymbol;
import com.coreman2200.domain.model.symbols.numbers.grouped.Periods;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * PeriodsInteractor
 * Processes the Period Cycles in a Number Chart.
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

public class PeriodsInteractor extends GroupedNumberSymbolsInteractor implements IGroupedNumberSymbolsInteractor {

    public PeriodsInteractor(RingStringsAppSettings settings) {
        super(settings);
    }

    IGroupedNumberSymbols getPeriods() {
        int reducedMonth = singularizeValue(userProfile.getBirthMonth());
        int reducedDay = singularizeValue(userProfile.getBirthDay());
        int reducedYear = singularizeValue(userProfile.getBirthYear());

        IGroupedNumberSymbols periods = new GroupedNumberSymbolsImpl(GroupedNumberSymbols.PERIODS);
        List<INumberSymbol> periodslist = new ArrayList<>(3);

        periodslist.add(convertValueToNumberSymbol(reducedMonth));
        periodslist.add(convertValueToNumberSymbol(reducedDay));
        periodslist.add(convertValueToNumberSymbol(reducedYear));

        Iterator<INumberSymbol> iter = periodslist.listIterator();
        for (Periods p : Periods.values()) {
            INumberSymbol symbol = iter.next();
            assert (symbol != null);
            periods.addNumberSymbol(GroupedNumberSymbols.valueOf(p.name()), symbol);
        }

        return periods;
    }

    public IGroupedNumberSymbols produceGroupedNumberSymbolsForProfile(IProfileDataBundle profile) {
        userProfile = profile;
        return getPeriods();
    }
}
