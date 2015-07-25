package com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped;

import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.BaseNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.GroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.impl.GroupedNumberSymbolsImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.IGroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.IListedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.INumberSymbol;
import com.coreman2200.ringstrings.symbol.numbersymbol.impl.ListedNumberSymbolsImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.Qualities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * QualitiesProcessorImpl
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

public class QualitiesProcessorImpl extends GroupedNumberSymbolsInputProcessorImpl implements IGroupedNumberSymbolsInputProcessor {
    IGroupedNumberSymbols processedQualities;

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

    // ??
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
        int lifePath = processedQualities.getNumberSymbol(Qualities.LIFEPATH).getNumberSymbolValue();
        int expression = processedQualities.getNumberSymbol(Qualities.EXPRESSION).getNumberSymbolValue();
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
    private INumberSymbol numGetSubconsciouSelf()
    {
        IGroupedNumberSymbols karmiclessons = processedQualities.getGroupedNumberSymbol(Qualities.KARMICLESSONS);

        int karmiccount = karmiclessons.size();
        return convertValueToNumberSymbol(9 - karmiccount);
    }

    private INumberSymbol numGetHiddenPassionsFromOccurrenceMap(HashMap<BaseNumberSymbols, Integer> occurrences) {
        final ArrayList<INumberSymbol> passions = getListOfMostCommonFromMap(occurrences);

        if (passions.size() == 0)
            return BaseNumberSymbols.ZERO.getBaseNumberSymbol();

        IListedNumberSymbols hpgroup = new ListedNumberSymbolsImpl(GroupedNumberSymbols.HIDDEN_PASSIONS);

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
            int count = occurrences.get(symbol);
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

        IListedNumberSymbols klgroup = new ListedNumberSymbolsImpl(GroupedNumberSymbols.KARMIC_LESSONS);

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
    public IGroupedNumberSymbols produceGroupedNumberSymbolsForProfile() {
        processedQualities = new GroupedNumberSymbolsImpl(GroupedNumberSymbols.QUALITIES);
        processedQualities.addNumberSymbol(Qualities.LIFEPATH, numGetLifePath());
        processedQualities.addNumberSymbol(Qualities.BALANCE, numGetBalance());
        processedQualities.addNumberSymbol(Qualities.EXPRESSION, numGetExpression());
        processedQualities.addNumberSymbol(Qualities.MATURITY, numGetMaturity());
        processedQualities.addNumberSymbol(Qualities.RATIONALTHOUGHT, numGetRationalThought());
        processedQualities.addNumberSymbol(Qualities.SOULURGE, numGetSoulUrge());
        processedQualities.addNumberSymbol(Qualities.PERSONALITY, numGetPersonality());

        HashMap<BaseNumberSymbols, Integer> occurrences = numMapOccurenceOfNumberSymbolsInName();
        processedQualities.addNumberSymbol(Qualities.HIDDENPASSIONS, numGetHiddenPassionsFromOccurrenceMap(occurrences));
        processedQualities.addNumberSymbol(Qualities.KARMICLESSONS, numGetKarmicLessonsListFromOccurrenceMap(occurrences));

        processedQualities.addNumberSymbol(Qualities.SUBCONSCIOUSSELF, numGetSubconsciouSelf());

        return processedQualities;
    }
}
