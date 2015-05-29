package com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped;

import com.coreman2200.ringstrings.symbol.IProfile;
import com.coreman2200.ringstrings.symbol.numbersymbol.GroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.GroupedNumberSymbolsImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.IGroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.INumberSymbol;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.Challenges;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.Personal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * PersonalsProcessorImpl
 * Processes the user's personal day, month, year..
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

public class PersonalsProcessorImpl  extends GroupedNumberSymbolsInputProcessorImpl implements IGroupedNumberSymbolsInputProcessor {

    IGroupedNumberSymbols getPersonals() {
        IGroupedNumberSymbols personals = new GroupedNumberSymbolsImpl(GroupedNumberSymbols.PERSONAL);
        List<INumberSymbol> personallist = new ArrayList<>(3);

        personallist.add(convertValueToNumberSymbol(numGetPersonalDay()));
        personallist.add(convertValueToNumberSymbol(numGetPersonalMonth()));
        personallist.add(convertValueToNumberSymbol(numGetPersonalYear()));

        Iterator<INumberSymbol> iter = personallist.listIterator();
        for (Personal p : Personal.values()) {
            INumberSymbol symbol = iter.next();
            assert (symbol != null);
            personals.addNumberSymbol(p, symbol);
        }

        return personals;
    }

    private int numGetPersonalDay()
    {
        int val = singularizeValue(numGetPersonalMonth() + singularizeValue(userProfile.getBirthMonth()));

        return val;
    }

    private int numGetPersonalMonth()
    {
        int val = singularizeValue(numGetPersonalYear() + singularizeValue(userProfile.getBirthDay()));

        return val;
    }

    private int numGetPersonalYear()
    {
        int i = singularizeValue(userProfile.getBirthMonth());
        int j = singularizeValue(userProfile.getBirthDay());
        int k = singularizeValue(userProfile.getBirthYear()) + (i + j);

        return singularizeValue(k);
    }

    public IGroupedNumberSymbols produceGroupedNumberSymbolsForProfile(IProfile profile) {
        super.produceGroupedNumberSymbolsForProfile(profile);
        return getPersonals();
    }
}
