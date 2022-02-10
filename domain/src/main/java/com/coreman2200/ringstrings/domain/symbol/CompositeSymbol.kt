package com.coreman2200.ringstrings.domain.symbol

import com.coreman2200.ringstrings.domain.symbol.entitysymbol.grouped.TagSymbols
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ICompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID
import java.util.*

/**
 * SymbolModel
 * description
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

abstract class CompositeSymbol<T : ISymbol>(
    override val id: ISymbolID,
    override val name: String = id.toString(),
    override val strata: ISymbolStrata
) :  ICompositeSymbol<T>, SymbolModel(id, name, strata) {
    protected var children: MutableList<T> = mutableListOf()

    override fun add(symbol: T) {
        children.add(symbol)
    }

    override fun add(symbols: Collection<T>) {
        children.addAll(symbols)
    }

    override fun remove(symbol: T) {
        if (children.contains(symbol))
            children.remove(symbol)
    }

    override fun clear() {
        children.clear()
    }

    override fun get(): List<T> {
        return children
    }

    override fun get(id: ISymbolID): T? {
        return children.find { it.id == id }
    }

    override fun getAll(): List<ISymbol> {
        val all = mutableListOf<ISymbol>()
        recurseChildren(all,this)
        return all
    }

    private fun recurseChildren(all :MutableList<ISymbol>, symbol:ISymbol) {
        if (symbol.get().isNotEmpty()) {
            symbol.get().forEach { recurseChildren(all, it) }
            all.add(symbol)
        } else if (!all.contains(symbol)) {
            all.add(symbol)
        }
    }

}
