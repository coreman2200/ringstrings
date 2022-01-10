package com.coreman2200.ringstrings.domain.input.numerology.grouped;

import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.GroupedNumbers;
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbol;
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.Pinnacles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * PinnaclesProcessor
 * Processes input into Pinnacles Number Symbols Grouping.
 *
 * Created by Cory Higginbottom on 5/27/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class PinnaclesProcessor extends GroupedNumberSymbolsInputProcessor implements IGroupedNumberSymbolsInputProcessor {

    public PinnaclesProcessor(RingStringsAppSettings settings) {
        super(settings);
    }

    private IGroupedNumberSymbols getPinnacles() {
        int reducedMonth = singularizeValue(userProfile.getBirthMonth());
        int reducedDay = singularizeValue(userProfile.getBirthDay());
        int reducedYear = singularizeValue(userProfile.getBirthYear());

        IGroupedNumberSymbols pinnacles = new GroupedNumberSymbolsImpl(GroupedNumbers.PINNACLES);
        List<INumberSymbol> pinnaclelist = new ArrayList<>(4);

        pinnaclelist.add(convertValueToNumberSymbol(reducedMonth + reducedDay));
        pinnaclelist.add(convertValueToNumberSymbol(reducedDay + reducedYear));
        int p1 = pinnaclelist.get(0).value();
        int p2 = pinnaclelist.get(1).value();
        pinnaclelist.add(convertValueToNumberSymbol(p1 + p2));
        pinnaclelist.add(convertValueToNumberSymbol(reducedMonth + reducedYear));

        Iterator<INumberSymbol> iter = pinnaclelist.listIterator();
        for (Pinnacles p : Pinnacles.values()) {
            INumberSymbol symbol = iter.next();
            assert (symbol != null);
            pinnacles.addNumberSymbol(p, symbol);
        }

        return pinnacles;
    }

    public IGroupedNumberSymbols produceGroupedNumberSymbolsForProfile(IProfileDataBundle profile) {
        userProfile = profile;
        return getPinnacles();
    }
}
