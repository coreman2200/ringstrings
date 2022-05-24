package com.coreman2200.ringstrings.domain.util

import com.coreman2200.ringstrings.domain.SymbolData
import com.coreman2200.ringstrings.domain.SymbolDataResponse
import com.coreman2200.ringstrings.domain.symbol.Charts
import com.coreman2200.ringstrings.domain.symbol.SymbolStrata
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.ICelestialBodySymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ICompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbol
import kotlinx.coroutines.flow.lastOrNull

/**
 * Symbol Conversions
 * description
 *
 * Created by Cory Higginbottom on 5/22/22
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

suspend fun SymbolDataResponse.toSymbol(): ISymbol {
    val symbolMap: MutableMap<Int, ISymbol> = mutableMapOf()
    val instanceMap: MutableMap<String, MutableList<Int>> = mutableMapOf()

    val emitted = symbols.lastOrNull() ?: emptyList()

    val profileCharts = emitted
        .sortedBy { it.type }
        .groupBy { Pair<Int, String?>(it.profileid, it.chartid) }

    profileCharts.forEach {data ->
        // Do for each profile..

        data.value.forEach { symbolData ->
            val symbol = produceSymbol(symbolData) ?: return@forEach
            instanceMap.addInstance(symbol.id.toString(), symbolData.instanceid)
            symbolMap[symbolData.instanceid] = symbol

            symbolData.children.forEach {
                symbol.linkChildSymbol(it, symbolMap, instanceMap)
            }

        }

    }

    return symbolMap[emitted.last().instanceid]!!
}

private fun produceSymbol(symbolData: SymbolData): ISymbol? {
    val strata = SymbolStrata.realStrataFor(symbolData.strata)
    return strata.produce(symbolData)?.apply {
        profileid = symbolData.profileid
        chartid = Charts.valueOf(symbolData.chartid)
        detail = symbolData.details

        if (this is ICelestialBodySymbol) {
            isRetrograde = symbolData.flag
        }
    }
}

private fun MutableMap<String, MutableList<Int>>.addInstance(
    name:String,
    instanceid:Int
) {
    val list = this.getOrDefault(name, mutableListOf())
    if (list.contains(instanceid)) return
    list.add(instanceid)
    this[name] = list

}

private fun MutableMap<String, MutableList<Int>>.removeInstance(
    name:String,
    instanceid:Int
) {
    val list = this.getOrDefault(name, mutableListOf())
    list.remove(instanceid)
    this[name] = list

}

private fun ISymbol.linkChildSymbol(
    id: String,
    symbolMap: MutableMap<Int, ISymbol>,
    instanceMap: MutableMap<String, MutableList<Int>>
) {
    if (id.isEmpty() || this !is ICompositeSymbol<*>) return

    val instanceList = instanceMap[id]
    val instance = instanceList?.get(0) ?: return
    val child = symbolMap[instance]
    val group = this as ICompositeSymbol<ISymbol>
    child?.let {
        group.add(it)
    }

    if (instanceList.size > 1) {
        instanceMap.removeInstance(id, instance)
    }

}

fun ISymbol.toData(): SymbolData = SymbolData(
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
