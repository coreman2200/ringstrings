package com.coreman2200.ringstrings.symbol;

import com.coreman2200.ringstrings.symbol.symbolcomparator.SymbolComparatorImpl;
import com.coreman2200.ringstrings.symbol.symbolinterface.ISymbol;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * AbstractSymbol
 * The Symbol class (abstract) defines the base entity elem in MyResonance.
 *
 * Created by Cory Higginbottom on 5/24/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */


abstract public class AbstractSymbol<T> implements ISymbol {
    private RelatedSymbolMap<T> symbolDataMap;
    protected Enum<? extends Enum<?>> mSymbolStrata;
    protected Enum<? extends Enum<?>> mSymbolID;

    protected AbstractSymbol(Enum<? extends Enum<?>> id) {
        assert (id != null);
        mSymbolID = id;
        setSymbolStrata();
        initializeSymbolMap();

    }

    private void initializeSymbolMap() {
        symbolDataMap = new RelatedSymbolMap<>();
    }

    //abstract protected void produceSymbol();

    abstract protected void setSymbolStrata();

    protected void setSymbolComparator(SymbolComparatorImpl<T> comparator) {
        symbolDataMap.setCurrentComparator(comparator);
    }

    protected final void addSymbolDataForKey(Enum<? extends Enum<?>> key, T data) throws RuntimeException {
        assert(key != null);
        assert(data != null);

        if (symbolDataMap.containsKey(key))
            throw new RuntimeException("Symbol method does not permit altering existing values by this means.");

        symbolDataMap.put(key, data);
    }

    public void addSymbolMap(Map<Enum<? extends Enum<?>>, T> map) {
        symbolDataMap.putAll(map);
    }

    protected final void updateSymbolDataForKey(Enum<? extends Enum<?>> key, T data) throws NullPointerException {
        assert(key != null);
        assert(data != null);

        if (!symbolDataMap.containsKey(key))
            throw new NullPointerException(key.name() + "does not exist as an element within this Symbol.");

        symbolDataMap.put(key, data);
    }

    protected final void removeSymbolDataForKey(Enum<? extends Enum<?>> key) throws NullPointerException {
        assert(key != null);

        if (symbolDataMap.containsKey(key))
            symbolDataMap.remove(key);
        else
            throw new NullPointerException(key.name() + "does not exist as an element within this Symbol.");
    }

    protected final T getSymbolDataForKey(Enum<? extends Enum<?>> key) throws NullPointerException {
        assert (key != null);

        if (!symbolDataMap.containsKey(key))
            throw new NullPointerException(key.name() + "does not exist as an element within this Symbol.");

        return symbolDataMap.get(key);
    }

    protected final Collection<T> getAllSymbols() throws NullPointerException {
        return symbolDataMap.getUnsortedSymbols();
    }

    protected final Collection<T> getSortedSymbols(RelatedSymbolMap.SortOrder order) {
        return symbolDataMap.getSortedSymbols(order);
    }

    protected HashMap<Enum<? extends Enum<?>>, T> prepareSymbolToStore() {
        throw new NoClassDefFoundError("Must be overridden.");
    }
    protected void refreshSymbolFromMap(HashMap<Enum<? extends Enum<?>>, T> storedSymbolMap) {
        throw new NoClassDefFoundError("Must be overridden.");
    }

    final public int size() {
        if (symbolStrata().compareTo(SymbolStrata.CHART) < 0)
            return symbolDataMap.size();

        Collection<T> allSymbols = getAllSymbols();

        int size = 0;
        for (T elem : allSymbols) {
            ISymbol symbol = (ISymbol)elem;
            size += symbol.size();
        }
        return size;
    }

    public String name() {
        return mSymbolID.toString();
    }

    public final SymbolStrata symbolStrata() {return SymbolStrata.getSymbolStrataFor(mSymbolStrata);}

    public void testGenerateLogs() {
        for (Map.Entry<Enum<? extends Enum<?>>, T> entry: symbolDataMap.getSortedSymbolMap()) {
            ISymbol symbol = (ISymbol)entry.getValue();

            int strataDepth = symbol.symbolStrata().ordinal()+1;
            String strataSpacing = String.format("%1$" + strataDepth + "s", "").replace(' ', '*');

            System.out.print(strataSpacing + " ");

            if (!entry.getKey().name().contentEquals(symbol.name()))
                System.out.print(entry.getKey().name() + ": ");
            System.out.print(symbol.name());
            System.out.println(" -strata: " + symbol.symbolStrata().name().toLowerCase());
            if (symbol.symbolStrata().compareTo(SymbolStrata.GROUP) >= 0)
                symbol.testGenerateLogs();
        }
    }

    // TODO: Stubbed.
    protected final void storeSymbol()  {};
    protected final void restoreSymbol() {};
}
