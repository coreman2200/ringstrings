package com.coreman2200.ringstrings.domain.symbol

import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ICompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID

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
    override val strata: ISymbolStrata,
    override var size: Int = 0,
) : SymbolModel(id, name, strata, size), ICompositeSymbol<T> {
    var children: MutableList<T> = mutableListOf()

    override fun add(symbol: T) {
        children.add(symbol)
        size = children.size
    }

    override fun add(symbols: Collection<T>) {
        children.addAll(symbols)
        size = children.size
    }

    override fun remove(symbol: T) {
        if (children.contains(symbol))
            children.remove(symbol)
        size = children.size
    }

    override fun clear() {
        children.clear()
        size = children.size
    }

    override fun elems(): List<T> {
        return children.toList()
    }

    override fun get(): List<T> {
        return children
    }

    override fun get(id: ISymbolID): T? {
        return children.find { it.id == id }
    }
}
