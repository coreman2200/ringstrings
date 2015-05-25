package com.coreman2200.ringstrings.symbol;

import java.util.HashMap;

import javax.management.RuntimeErrorException;

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


abstract public class AbstractSymbol implements ISymbol {
    private HashMap<Enum<? extends Enum<?>>, Object>  symbolDataMap;

    private final void initializeSymbolMap() {
        symbolDataMap = new HashMap<Enum<? extends Enum<?>>, Object>();
    }

    protected final void addSymbolDataForKey(Enum<? extends Enum<?>> key, Object data) throws RuntimeException {
        assert(key != null);
        assert(data != null);

        if (symbolDataMap.containsKey(key))
            throw new RuntimeException("Symbol method does not permit altering existing values by this means.");

        symbolDataMap.put(key, data);
    }

    protected final void updateSymbolDataForKey(Enum<? extends Enum<?>> key, Object data) throws NullPointerException {
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

    protected final Object getSymbolDataForKey(Enum<? extends Enum<?>> key) throws NullPointerException {
        assert (key != null);

        if (!symbolDataMap.containsKey(key))
            throw new NullPointerException(key.name() + "does not exist as an element within this Symbol.");

        return symbolDataMap.get(key);
    }

    protected final AbstractSymbol produceSymbol() {
        initializeSymbolMap();
        return this;
    }

    // TODO: Stubbed.
    protected void storeSymbol()  {};
    protected void restoreSymbol() {};
}
