package com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped;

import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.protos.RingStringsAppSettings;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.GroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.impl.GroupedNumberSymbolsImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.IGroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.INumberSymbol;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.Personal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * PersonalsProcessor
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

public class PersonalsProcessor extends GroupedNumberSymbolsInputProcessor implements IGroupedNumberSymbolsInputProcessor {

    public PersonalsProcessor(RingStringsAppSettings settings) {
        super(settings);
    }

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
        return singularizeValue(numGetPersonalMonth() + singularizeValue(userProfile.getBirthMonth()));
    }

    private int numGetPersonalMonth()
    {
        return singularizeValue(numGetPersonalYear() + singularizeValue(userProfile.getBirthDay()));
    }

    private int numGetPersonalYear()
    {
        int month = singularizeValue(userProfile.getBirthMonth());
        int day = singularizeValue(userProfile.getBirthDay());
        int year = singularizeValue(userProfile.getBirthYear());

        return singularizeValue(month + day + year);
    }

    public IGroupedNumberSymbols produceGroupedNumberSymbolsForProfile(IProfileDataBundle profile) {
        userProfile = profile;
        return getPersonals();
    }
}
