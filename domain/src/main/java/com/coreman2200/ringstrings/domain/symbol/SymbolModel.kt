package com.coreman2200.ringstrings.domain.symbol

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

abstract class SymbolModel(
    override val id: ISymbolID,
    override val name: String = id.toString(),
    override val strata: ISymbolStrata,
    override var size: Int = 1,
) : ISymbol {
    override var related: MutableMap<ISymbolID, ISymbol> = mutableMapOf()
    override var profileid:Int = 0
    override var chartid:ISymbolID? = null
    override var groupid:ISymbolID? = null

    override fun get(): List<ISymbol> = related.values.toList()
    override fun get(id: ISymbolID): ISymbol? = related[id]
}
