package com.coreman2200.ringstrings.symbol.symbolinterface;

import com.coreman2200.ringstrings.symbol.SymbolStrata;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * ISymbol
 * Defines interface for all Symbol elements in MyResonance (numbers, celestial bodies, individuals..)
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

public interface ISymbol<T extends ISymbol> {
    String name();
    int size();
    SymbolStrata symbolStrata();
    Enum<? extends Enum<?>> symbolType();
    Enum<? extends Enum<?>> symbolID();
    boolean containsSymbol(ISymbol symbol);
    Collection<Enum<? extends Enum<?>>> symbolIDCollection();
    Map<Enum<? extends Enum<?>>, T> produceSymbol();
    void testGenerateLogs();
}
