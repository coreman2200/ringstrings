package com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl

import com.coreman2200.ringstrings.domain.symbol.Charts
import com.coreman2200.ringstrings.domain.symbol.CompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.SymbolModel
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.AstralStrata
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.Houses
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralChartSymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralGroupSymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IHouseSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.IGroupedNumberSymbolID
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID
import com.squareup.wire.internal.newMutableMap

/**
 * AstroChartSymbol
 * contains all elements of the astrological chart
 *
 * Created by Cory Higginbottom on 6/6/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
class AstrologicalChart(
    override val id: Charts,
    override val degree: Double
) : CompositeSymbol<SymbolModel>(
    id = id,
    strata = AstralStrata.ASTRALCHART
),
    IAstralChartSymbol {


    override fun producedCelestialBodyMap(): Map<ISymbolID, CelestialBodySymbol> {
        val bodies:List<CelestialBodySymbol> = Houses.values().associate { Pair(it,related[it] as IHouseSymbol) }
            .values.flatMap { it.elems() }
        return bodies.associateBy { it.id }
    }

    override fun add(group: ISymbolID, symbol: SymbolModel) {
        related[group] = symbol
        add(symbol)
    }

    override fun add(map: Map<ISymbolID, SymbolModel>) {
        related.putAll(map)
        map.values.forEach { add(it) }
    }

    override fun add(symbol: SymbolModel) {
        super.add(symbol)
        if (symbol is IAstralGroupSymbol<*>) { related[symbol.id] = symbol }
    }

    override fun add(symbols: Collection<SymbolModel>) {
        super.add(symbols)
        val groups = symbols.filter { it is IAstralGroupSymbol<*> }
            .map { it.id to it }.toMap()
        related.putAll(groups)
    }
}
