package com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl

import com.coreman2200.ringstrings.domain.symbol.CompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.AstralStrata
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.Aspects
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralSymbol

/**
 * AspectedSymbolsImpl
 * Aspects/Transits on Astro Chart.
 *
 * Created by Cory Higginbottom on 6/8/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
class AspectedSymbols(
    override val id: Aspects,
    val s1: CelestialBodySymbol,
    val s2: CelestialBodySymbol
) : CompositeSymbol<CelestialBodySymbol>(
    id,
    name = id.toString(),
    strata = AstralStrata.ASTRALASPECT,
    size = 2,
),
    IAstralSymbol {
    override val degree: Double = getDegreeBetweenSymbols(id, s1, s2)

    private fun getDegreeBetweenSymbols(
        aspect: Aspects,
        s1: CelestialBodySymbol,
        s2: CelestialBodySymbol
    ): Double {
        val deg1: Double = s1.degree
        val deg2: Double = s2.degree
        val deghalf = aspect.degree() / 2
        val min = deg1.coerceAtMost(deg2)
        return wrapDegree(min + deghalf)
    }

    init {
        add(s1)
        add(s2)
    }
}
