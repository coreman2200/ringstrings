package com.coreman2200.ringstrings.domain.symbol.symbolinterface

import com.coreman2200.ringstrings.domain.symbol.entitysymbol.grouped.TagSymbols
import java.util.*

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

interface ICompositeSymbol<T : ISymbol> : ISymbol {
    fun add(symbol: T)
    fun add(symbols: Collection<T>)
    fun remove(symbol: T)
    fun clear()
    fun getAll():List<ISymbol>
    override fun size():Int = getAll().size

    override fun qualities(): Map<TagSymbols, MutableList<ISymbolID>> {
        val self = detail?.qualities?.associate { Pair(it, mutableListOf(id)) } ?: mapOf()
        val map = mutableMapOf<TagSymbols, MutableList<ISymbolID>>().apply {
            self.forEach { (tagSymbols, list) ->
                merge(tagSymbols,list) { a: MutableList<ISymbolID>, b: MutableList<ISymbolID> ->
                    a.addAll(b)
                    return@merge a
                }
            }

            get().forEach { iSymbol ->
                iSymbol.qualities().forEach { (tagSymbols, list) ->
                    merge(tagSymbols,list) { a: MutableList<ISymbolID>, b: MutableList<ISymbolID> ->
                        a.addAll(b)
                        return@merge a
                    }
                }
            }

        }
        return map.toList().sortedByDescending { it.second.size }.toMap()
    }

}

interface IChartedSymbols<T:ISymbol> : ICompositeSymbol<T>
