package com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped;

import com.coreman2200.ringstrings.protos.RingStringsAppSettings;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.GroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.impl.GroupedNumberSymbolsImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.IGroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.INumberSymbol;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.Challenges;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ChallengesProcessor
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

public class ChallengesProcessor extends GroupedNumberSymbolsInputProcessor implements IGroupedNumberSymbolsInputProcessor {

    public ChallengesProcessor(RingStringsAppSettings settings) {
        super(settings);
    }

    IGroupedNumberSymbols getChallenges() {
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
            challenges.addNumberSymbol(c, symbol);
        }

        return challenges;
    }

    public IGroupedNumberSymbols produceGroupedNumberSymbolsForProfile() {
        return getChallenges();
    }

}
