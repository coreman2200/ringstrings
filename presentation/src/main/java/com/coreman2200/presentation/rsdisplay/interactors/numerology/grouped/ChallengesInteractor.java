package com.coreman2200.presentation.rsdisplay.interactors.numerology.grouped;

import com.coreman2200.domain.model.profiles.interfaces.IProfileDataBundle;
import com.coreman2200.domain.model.protos.RingStringsAppSettings;
import com.coreman2200.domain.model.symbols.numbers.grouped.GroupedNumberSymbols;
import com.coreman2200.domain.model.symbols.numbers.impl.GroupedNumberSymbolsImpl;
import com.coreman2200.domain.model.symbols.numbers.interfaces.IGroupedNumberSymbols;
import com.coreman2200.domain.model.symbols.numbers.interfaces.INumberSymbol;
import com.coreman2200.domain.model.symbols.numbers.grouped.Challenges;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ChallengesInteractor
 * Processes the Challenges in a Numerological chart.
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

public class ChallengesInteractor extends GroupedNumberSymbolsInteractor implements IGroupedNumberSymbolsInteractor {

    public ChallengesInteractor(RingStringsAppSettings settings) {
        super(settings);
    }

    public IGroupedNumberSymbols produceGroupedNumberSymbolsForProfile(IProfileDataBundle profile) {
        this.userProfile = profile;
        return getChallenges();
    }

    private IGroupedNumberSymbols getChallenges() {
        int reducedMonth = singularizeValue(userProfile.getBirthMonth());
        int reducedDay = singularizeValue(userProfile.getBirthDay());
        int reducedYear = singularizeValue(userProfile.getBirthYear());

        IGroupedNumberSymbols challenges = new GroupedNumberSymbolsImpl(GroupedNumberSymbols.CHALLENGES);
        List<INumberSymbol> challengelist = new ArrayList<>(4);

        challengelist.add(convertValueToNumberSymbol(Math.abs(reducedDay - reducedMonth)));
        challengelist.add(convertValueToNumberSymbol(Math.abs(reducedYear - reducedDay)));
        int p1 = challengelist.get(0).getNumberSymbolValue();
        int p2 = challengelist.get(1).getNumberSymbolValue();
        challengelist.add(convertValueToNumberSymbol(Math.abs(p2 - p1)));
        challengelist.add(convertValueToNumberSymbol(Math.abs(reducedYear - reducedMonth)));

        Iterator<INumberSymbol> iter = challengelist.listIterator();
        for (Challenges c : Challenges.values()) {
            INumberSymbol symbol = iter.next();
            assert (symbol != null);
            challenges.addNumberSymbol(GroupedNumberSymbols.valueOf(c.name()), symbol);
        }

        return challenges;
    }

}
