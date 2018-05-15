package com.coreman2200.presentation.rsdisplay.interactors.numerology.grouped;

import com.coreman2200.domain.model.profiles.interfaces.IProfileDataBundle;
import com.coreman2200.domain.model.protos.RingStringsAppSettings;
import com.coreman2200.domain.model.symbols.numbers.grouped.GroupedNumberSymbols;
import com.coreman2200.domain.model.symbols.numbers.impl.GroupedNumberSymbolsImpl;
import com.coreman2200.domain.model.symbols.numbers.interfaces.IGroupedNumberSymbols;
import com.coreman2200.domain.model.symbols.numbers.interfaces.INumberSymbol;
import com.coreman2200.domain.model.symbols.numbers.grouped.Personal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * PersonalsInteractor
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

public class PersonalsInteractor extends GroupedNumberSymbolsInteractor implements IGroupedNumberSymbolsInteractor {

    public PersonalsInteractor(RingStringsAppSettings settings) {
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
            personals.addNumberSymbol(GroupedNumberSymbols.valueOf(p.name()), symbol);
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
