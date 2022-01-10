package com.coreman2200.ringstrings.domain.symbol

import com.coreman2200.ringstrings.domain.symbol.astralsymbol.AstralStrata
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.Aspects
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ICompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolStrata

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

abstract class CompositeSymbol<T: SymbolModel>(
    override val id: ISymbolID,
    override val name: String = id.toString(),
    override val strata: ISymbolStrata,
    override var size: Int = 0,
) : SymbolModel(id,name,strata,size), ICompositeSymbol<T> {
    var children: MutableList<T> = mutableListOf()

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

    override fun elems(): List<T> {
        return children.toList()
    }


}
