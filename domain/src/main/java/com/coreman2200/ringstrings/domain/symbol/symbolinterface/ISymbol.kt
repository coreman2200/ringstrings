package com.coreman2200.ringstrings.domain.symbol.symbolinterface

import com.coreman2200.ringstrings.domain.SymbolData
import com.coreman2200.ringstrings.domain.symbol.ISymbolStrata
import com.coreman2200.ringstrings.domain.symbol.SymbolStrata
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.grouped.TagSymbols
import java.util.*

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
interface ISymbol {
    val name: String
    val id: ISymbolID
    val strata: ISymbolStrata
    var profileid:Int
    var chartid:ISymbolID?
    var groupid:ISymbolID?
    val related:MutableMap<ISymbolID,ISymbol>


    fun size():Int = 1
    fun get(): List<ISymbol>
    fun get(id: ISymbolID): ISymbol?
    fun value():Double
    fun flag():Boolean = false
    fun qualities():SortedMap<TagSymbols, Int>
    fun toData():SymbolData = SymbolData(
        profileid = profileid,
        chartid = chartid.toString(),
        groupid = groupid.toString(),
        symbolid = id.toString(),
        strata = strata.toString(),
        type = SymbolStrata.symbolStrataFor(strata).ordinal,
        value = value(),
        flag = flag(),
        children = get().map { it.id.toString() },
        relations = related.keys.map { it.toString() }
    )
}
