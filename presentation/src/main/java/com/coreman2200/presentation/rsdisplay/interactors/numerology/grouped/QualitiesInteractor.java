package com.coreman2200.presentation.rsdisplay.interactors.numerology.grouped;

import com.coreman2200.domain.model.profiles.interfaces.IProfileDataBundle;
import com.coreman2200.domain.model.protos.RingStringsAppSettings;
import com.coreman2200.domain.model.symbols.numbers.grouped.BaseNumberSymbols;
import com.coreman2200.domain.model.symbols.numbers.grouped.GroupedNumberSymbols;
import com.coreman2200.domain.model.symbols.numbers.impl.GroupedNumberSymbolsImpl;
import com.coreman2200.domain.model.symbols.numbers.interfaces.IGroupedNumberSymbols;
import com.coreman2200.domain.model.symbols.numbers.interfaces.IListedNumberSymbols;
import com.coreman2200.domain.model.symbols.numbers.interfaces.INumberSymbol;
import com.coreman2200.domain.model.symbols.numbers.impl.ListedNumberSymbolsImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * QualitiesInteractor
 * Processes the user's different numerological qualities.
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

public class QualitiesInteractor extends GroupedNumberSymbolsInteractor implements IGroupedNumberSymbolsInteractor {
    IGroupedNumberSymbols processedQualities;

    public QualitiesInteractor(RingStringsAppSettings settings) {
        super(settings);
    }

    private String getFirstCharacterOfString(String text) {
        return text.substring(0, 1);
    }


    private INumberSymbol numGetBalance()
    {
        int firstNameFirstLetter = convertTextStringToValue(getFirstCharacterOfString(userProfile.getFirstName()));
        int midNameFirstLetter = convertTextStringToValue(getFirstCharacterOfString(userProfile.getMiddleName()));
        int lastNameFirstLetter = convertTextStringToValue(getFirstCharacterOfString(userProfile.getLastName()));

        return convertValueToNumberSymbol(firstNameFirstLetter + midNameFirstLetter + lastNameFirstLetter);
    }

    // TODO: ?? Not used..
    private INumberSymbol numGetBirthDay()
    {
        return convertValueToNumberSymbol(userProfile.getBirthDay());
    }

    private INumberSymbol numGetExpression()
    {
        int firstName = convertTextStringToValue(userProfile.getFirstName());
        int midName = convertTextStringToValue(userProfile.getMiddleName());
        int lastName = convertTextStringToValue(userProfile.getLastName());

        return convertValueToNumberSymbol(firstName + midName + lastName);
    }

    private INumberSymbol numGetLifePath()
    {
        int bday = userProfile.getBirthDay();
        int bmonth = userProfile.getBirthMonth();
        int byear = userProfile.getBirthYear();
        return convertValueToNumberSymbol(bday + bmonth + byear);
    }

    // Life Path + Expression..
    private INumberSymbol numGetMaturity()
    {
        int lifePath = processedQualities.getNumberSymbol(GroupedNumberSymbols.LIFEPATH).getNumberSymbolValue();
        int expression = processedQualities.getNumberSymbol(GroupedNumberSymbols.EXPRESSION).getNumberSymbolValue();
        assert (lifePath != 0);
        assert (expression != 0);

        return convertValueToNumberSymbol(lifePath + expression);
    }

    // First, Middle, Last..
    private INumberSymbol numGetPersonality()
    {
        int first = convertTextStringToValue(getConsonants(userProfile.getFirstName()));
        int middle = convertTextStringToValue(getConsonants(userProfile.getMiddleName()));
        int last = convertTextStringToValue(getConsonants(userProfile.getLastName()));

        return convertValueToNumberSymbol(first + middle + last);
    }

    // First Name, Bday..
    private INumberSymbol numGetRationalThought()
    {
        int i = convertTextStringToValue(userProfile.getFirstName()) + singularizeValue(userProfile.getBirthDay());
        return convertValueToNumberSymbol(i);
    }

    // First, Middle, Last name..
    private INumberSymbol numGetSoulUrge()
    {
        int firstName = convertTextStringToValue(getVowels(userProfile.getFirstName()));
        int midName = convertTextStringToValue(getVowels(userProfile.getMiddleName()));
        int lastName = convertTextStringToValue(getVowels(userProfile.getLastName()));

        return convertValueToNumberSymbol(firstName + midName + lastName);
    }

    // Karmic Lessons Count..
    private INumberSymbol numGetSubconsciousSelf()
    {
        IGroupedNumberSymbols karmiclessons = processedQualities.getGroupedNumberSymbol(GroupedNumberSymbols.KARMICLESSON);

        int karmiccount = karmiclessons.size();
        return convertValueToNumberSymbol(9 - karmiccount);
    }

    private INumberSymbol numGetHiddenPassionsFromOccurrenceMap(HashMap<BaseNumberSymbols, Integer> occurrences) {
        final ArrayList<INumberSymbol> passions = getListOfMostCommonFromMap(occurrences);

        if (passions.size() == 0)
            return BaseNumberSymbols.ZERO.getBaseNumberSymbol();

        IListedNumberSymbols hpgroup = new ListedNumberSymbolsImpl(GroupedNumberSymbols.HIDDENPASSIONS);

        for (INumberSymbol lesson : passions)
            hpgroup.addNumberSymbol(lesson);

        return hpgroup;
    }


    private HashMap<BaseNumberSymbols, Integer> numMapOccurenceOfNumberSymbolsInName() {
        final String fullname = userProfile.getFirstName() + userProfile.getMiddleName() + userProfile.getLastName();
        char[] nameArray = prepareTextForProcessing(fullname);
        HashMap<BaseNumberSymbols, Integer> occurrences = new HashMap<>();

        for (char c : nameArray) {
            int charValue = mNumberSystem.numValueForChar(c);
            BaseNumberSymbols symbol = BaseNumberSymbols.getBaseNumberSymbolIDForValue(charValue);
            Integer count = (!occurrences.containsKey(symbol)) ? Integer.valueOf(0) : occurrences.get(symbol);
            occurrences.put(symbol, ++count);
        }
        return occurrences;
    }

    private ArrayList<INumberSymbol> getListOfMostCommonFromMap(HashMap<BaseNumberSymbols, Integer> occurrences) {

        Integer[] counts = occurrences.values().toArray(new Integer[occurrences.size()]);
        Arrays.sort(counts);

        int high = counts[counts.length-1];

        ArrayList<INumberSymbol> mostcommon = new ArrayList<>();

        for (BaseNumberSymbols symbol : BaseNumberSymbols.values()) {
            if (occurrences.containsKey(symbol) && occurrences.get(symbol) == high)
                mostcommon.add(symbol.getBaseNumberSymbol());
        }

        return mostcommon;
    }

    private INumberSymbol numGetKarmicLessonsListFromOccurrenceMap(HashMap<BaseNumberSymbols, Integer> occurrences) {
        final ArrayList<INumberSymbol> lessons = getListOfNotOccurredFromMap(occurrences);

        IListedNumberSymbols klgroup = new ListedNumberSymbolsImpl(GroupedNumberSymbols.KARMICLESSON);

        for (INumberSymbol lesson : lessons)
            klgroup.addNumberSymbol(lesson);

        return klgroup;
    }

    private ArrayList<INumberSymbol> getListOfNotOccurredFromMap(HashMap<BaseNumberSymbols, Integer> occurrences) {
        ArrayList<INumberSymbol> notfound = new ArrayList<>();
        for (BaseNumberSymbols symbol : BaseNumberSymbols.values()) {
            if (symbol.equals(BaseNumberSymbols.ZERO))
                continue;
            if (!occurrences.containsKey(symbol) && !checkIsSymbolMasterNumber(symbol))
                notfound.add(symbol.getBaseNumberSymbol());
        }
        return notfound;
    }

    protected final boolean checkIsSymbolMasterNumber(BaseNumberSymbols symbol) {
        assert (symbol != null);
        return symbol.compareTo(BaseNumberSymbols.NINE) > 0;
    }

    @Override
    public IGroupedNumberSymbols produceGroupedNumberSymbolsForProfile(IProfileDataBundle profile) {
        userProfile = profile;
        // TODO: Protocol for building grouped & charted elems..
        processedQualities = new GroupedNumberSymbolsImpl(GroupedNumberSymbols.QUALITIES);
        processedQualities.addNumberSymbol(GroupedNumberSymbols.LIFEPATH, numGetLifePath());
        processedQualities.addNumberSymbol(GroupedNumberSymbols.BALANCE, numGetBalance());
        processedQualities.addNumberSymbol(GroupedNumberSymbols.EXPRESSION, numGetExpression());
        processedQualities.addNumberSymbol(GroupedNumberSymbols.MATURITY, numGetMaturity());
        processedQualities.addNumberSymbol(GroupedNumberSymbols.RATIONALTHOUGHT, numGetRationalThought());
        processedQualities.addNumberSymbol(GroupedNumberSymbols.SOULURGE, numGetSoulUrge());
        processedQualities.addNumberSymbol(GroupedNumberSymbols.PERSONALITY, numGetPersonality());

        HashMap<BaseNumberSymbols, Integer> occurrences = numMapOccurenceOfNumberSymbolsInName();
        processedQualities.addNumberSymbol(GroupedNumberSymbols.HIDDENPASSIONS, numGetHiddenPassionsFromOccurrenceMap(occurrences));
        processedQualities.addNumberSymbol(GroupedNumberSymbols.KARMICLESSON, numGetKarmicLessonsListFromOccurrenceMap(occurrences));

        processedQualities.addNumberSymbol(GroupedNumberSymbols.SUBCONSCIOUSSELF, numGetSubconsciousSelf());

        return processedQualities;
    }
}
