package com.coreman2200.ringstrings.symbol;

import com.coreman2200.ringstrings.symbol.symbolcomparator.SymbolComparatorImpl;
import com.coreman2200.ringstrings.symbol.symbolcomparator.SymbolStrataComparatorImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * AbstractRelatedSymbolMap
 * Related Symbol Maps apply comparators to member symbols and group symbols
 *
 * Created by Cory Higginbottom on 5/26/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class RelatedSymbolMap<T> {
    public enum SortOrder {
        ASCENDING,
        DESCENDING
    }

    protected HashMap<Enum<? extends Enum<?>>, T> mBackingUnsortedMap;
    protected SortedSet<Map.Entry<Enum<? extends Enum<?>>, T>> mSortedInstanceSet;
    private SymbolComparatorImpl mCurrentComparator;

    public RelatedSymbolMap() {
        mCurrentComparator = new SymbolStrataComparatorImpl();
        mBackingUnsortedMap = new HashMap<>();
        mSortedInstanceSet = new TreeSet<>();
    }

    public RelatedSymbolMap(SymbolComparatorImpl comparator) {
        mCurrentComparator = comparator;
        mBackingUnsortedMap = new HashMap<>();
        mSortedInstanceSet = new TreeSet<>(comparator);
    }

    public void setCurrentComparator(SymbolComparatorImpl comparator) {
        mCurrentComparator = comparator;
        resortMap();
    }

    private void resortMap() {
        mSortedInstanceSet.clear();
        mSortedInstanceSet = new TreeSet<>(mCurrentComparator);
        mSortedInstanceSet.addAll(mBackingUnsortedMap.entrySet());
    }

    public void put(Enum<? extends Enum<?>> key, T symbol) {
        mBackingUnsortedMap.put(key, symbol);
        resortMap();
    }

    public void putAll(Map<Enum<? extends Enum<?>>, T> map) {
        mBackingUnsortedMap.putAll(map);
        resortMap();
    }

    public T get(Enum<? extends Enum<?>> key) {
        return mBackingUnsortedMap.get(key);
    }

    public int size() {
        return mBackingUnsortedMap.values().size();
    }

    public Collection<T> getSortedSymbols(SortOrder order) {
        switch (order) {
            case ASCENDING:
                return produceValueListFromSortedSet();
            case DESCENDING:
                List sortedset = produceValueListFromSortedSet();
                Collections.reverse(sortedset);
                return (sortedset);
            default:
                throw new RuntimeException("Sort order not set.");
        }
    }

    public Collection<T> getSymbolsSortedBy(SymbolComparatorImpl comparator, SortOrder order) {
        setCurrentComparator(comparator);
        resortMap();

        return getSortedSymbols(order);
    }

    private List<T> produceValueListFromSortedSet() {
        List<T> list = new ArrayList<>();
        for (Map.Entry<Enum<? extends Enum<?>>, T> entry : mSortedInstanceSet)
            list.add(entry.getValue());

        return list;
    }

}
