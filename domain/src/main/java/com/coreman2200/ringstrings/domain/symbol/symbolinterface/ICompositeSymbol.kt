package com.coreman2200.ringstrings.domain.symbol.symbolinterface

import com.coreman2200.ringstrings.domain.symbol.SymbolModel

/**
 * ICompositeSymbol
 * Interface for composed symbol objects, extends ISymbol
 *
 * Created by Cory Higginbottom on 1/7/22
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

interface ICompositeSymbol<T : SymbolModel> : ISymbol {
    fun add(symbol: T)
    fun add(symbols: Collection<T>)
    fun remove(symbol: T)
    fun clear()
    fun elems(): List<T>

}

interface IChartedSymbols : ICompositeSymbol<SymbolModel>
