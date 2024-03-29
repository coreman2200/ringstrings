package com.coreman2200.ringstrings.domain.input.astrology.grouped

import com.coreman2200.ringstrings.domain.AstrologySettings
import com.coreman2200.ringstrings.domain.input.InputProcessor
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.Aspects
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl.AspectedSymbols
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl.CelestialBodySymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralChartSymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID
import java.util.*
import kotlin.math.abs

/**
 * AspectsProcessor
 * description
 *
 * Created by Cory Higginbottom on 1/11/22
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

class AspectsProcessor(
    val chart: IAstralChartSymbol,
    val settings: AstrologySettings
) : InputProcessor() {

    private val aspectValueMap: TreeMap<Double, Aspects> = TreeMap(
        Aspects.values().associateBy { it.degree() }.toMap()
    )

    fun calcAspects() {
        val map: Map<ISymbolID, IAstralSymbol> = chart.producedCelestialBodyMap()
        val bodies:List<CelestialBodySymbol> = map.values.filterIsInstance<CelestialBodySymbol>()
        val comparelist: MutableList<IAstralSymbol> = mutableListOf()
        comparelist.addAll(bodies.filter { it.id.isRealCelestialBody })

        for (body in bodies) {
            comparelist.remove(body)
            val aspects: List<AspectedSymbols> = checkForAspects(body, comparelist)
            chart.add(aspects)
        }
    }

    private fun checkForAspects(
        body: IAstralSymbol,
        comparelist: List<IAstralSymbol>
    ): List<AspectedSymbols> {
        val orb: Double = settings.maxorb
        val list: MutableList<AspectedSymbols> = mutableListOf()
        comparelist.forEach { elem ->
            val diff = abs(elem.degree - body.degree)
            val min = aspectValueMap.minWithOrNull(compareBy { abs(diff - it.key) })

            if (min != null && checkValueWithinOrbOfAspect(min.key, diff, orb)) {
                val aspect = AspectedSymbols(min.value)
                aspect.add(body)
                aspect.add(elem)
                list.add(aspect)
            }
        }
        return list
    }

    private fun checkValueWithinOrbOfAspect(
        aspect: Double,
        degree: Double,
        orb: Double
    ): Boolean { // TODO This looks extra..
        return if (aspect == 0.0 && 360.0 - degree < orb) {
            true
        } else
            abs(degree - aspect) <= orb
    }
}
