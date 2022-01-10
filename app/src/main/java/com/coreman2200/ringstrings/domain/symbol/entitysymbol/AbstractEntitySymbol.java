package com.coreman2200.ringstrings.domain.symbol.entitysymbol;

import com.coreman2200.ringstrings.domain.symbol.SymbolStrata;
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.Tags.TagSymbols;
import com.coreman2200.ringstrings.domain.input.entity.symboldef.ISymbolDefFileHandler;
import com.coreman2200.ringstrings.domain.input.entity.symboldef.SymbolDefFileHandlerImpl;
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.IEntitySymbol;
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * AbstractEntitySymbol
 * The Entity Symbol expresses symbols/symbol groupings per their relationships and respective
 * "Quality", or rather, an aggregated and comparator-ready grouping of Tags
 *
 * Created by Cory Higginbottom on 11/4/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

abstract public class AbstractEntitySymbol<T extends ISymbol> extends AbstractSymbol<T> implements IEntitySymbol {
    protected Map<TagSymbols, Integer> mQualityCountMap;
    protected SortedSet<Map.Entry<TagSymbols, Integer>> mSortedInstanceSet;
    protected final ISymbol mSymbol;

    protected AbstractEntitySymbol(ISymbol symbol) {
        super(symbol.id());
        mSymbol = symbol;
        addSymbolMap(mSymbol.produceSymbol());
        produceQualities();
    }

    private final void produceQualities() {
        Comparator comparator = new QualityComparator();
        mQualityCountMap = new HashMap<>();
        mSortedInstanceSet = new TreeSet<>(comparator);
        Map<Enum<? extends Enum<?>>, T> symbolmap = produceSymbol();


        for (Enum<? extends Enum<?>> entry : symbolmap.keySet()) {
            ISymbol symbol = symbolmap.get(entry);

            //
            SymbolDescription symboldef = getSymbolDefForKey(entry);
            if ((symbol.strata().compareTo(SymbolStrata.CHART) < 0) && symboldef != null) {
                aggregateTags(symboldef.qualities);
            }


            Map<Enum<? extends Enum<?>>, ISymbol> subsymbols = symbol.produceSymbol();
            for (ISymbol ss : chartedSymbolsListFromMap(subsymbols)) {
                SymbolDescription sd = getSymbolDefForKey(ss.id());
                if (sd != null) {
                    aggregateTags(sd.qualities);
                }
            }


        }

        mSortedInstanceSet.addAll(mQualityCountMap.entrySet());

        /*
        Iterator<Map.Entry<TagSymbols, Integer>> iter = mSortedInstanceSet.iterator();

        System.out.println(mSymbol.name());
        System.out.println("Total Unique Quality: " + mQualityCountMap.size() + "(vs " + TagSymbols.values().length + " overall)");

        while (iter.hasNext()) {
            Map.Entry<TagSymbols, Integer> entry = iter.next();
            System.out.print(entry.getKey().name() + "(" + entry.getValue() + ") ");
        }

        System.out.println();
        System.out.println("Total Symbols Aggregated: " + symbolmap.size());
        */

    }



    protected final SymbolDescription getSymbolDefForKey(Enum<? extends Enum<?>> id) {
        ISymbolDefFileHandler handler = SymbolDefFileHandlerImpl.getInstance();
        return handler.produceSymbolDefForSymbol(id);
    }

    private final Collection<ISymbol> chartedSymbolsListFromMap(Map<Enum<? extends Enum<?>>, ISymbol> map) {
        Collection<ISymbol> list = new ArrayList<>();
        for (ISymbol elem : map.values()) {
            if (elem.strata().compareTo(SymbolStrata.CHART) < 0)
                list.add(elem);
        }
        return list;
    }

    protected final void aggregateTags(List<String> tagnames) {
        for (String tag : tagnames)
            addQuality(TagSymbols.getTagForString(tag), 1);
    }

    protected final void aggregateTags(Collection<TagSymbols> tags) {
        for (TagSymbols tag : tags)
            addQuality(tag, 1);
    }

    protected final void aggregateTags(Map<TagSymbols, Integer> tags) {
        for (TagSymbols tag : tags.keySet()) {
            addQuality(tag, tags.get(tag));
        }
    }

    private final void addQuality(TagSymbols tag, Integer count) {
        Integer current = mQualityCountMap.get(tag);
        if (current == null)
            mQualityCountMap.put(tag, count);
        else
            mQualityCountMap.put(tag, current+count);
    }

    public final Collection<TagSymbols> getQualities() {
        return mQualityCountMap.keySet();

    }

    @Override
    public int getTagCount(TagSymbols tag) {
        return mQualityCountMap.get(tag).intValue();
    }


    private class QualityComparator implements Comparator<Map.Entry<Enum<? extends Enum<?>>, Integer>> {

        @Override
        public int compare(Map.Entry<Enum<? extends Enum<?>>, Integer> o1, Map.Entry<Enum<? extends Enum<?>>, Integer> o2) {
            int res = o2.getValue().compareTo(o1.getValue());
            return res != 0 ? res : 1;
        }
    }


}
