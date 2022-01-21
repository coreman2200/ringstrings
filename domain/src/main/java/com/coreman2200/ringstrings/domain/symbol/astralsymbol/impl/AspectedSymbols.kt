package com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl

import com.coreman2200.ringstrings.domain.symbol.CompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.AstralStrata
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.Aspects
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralGroupSymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.IDerivedNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID

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
) : CompositeSymbol<IAstralSymbol>(
    id,
    name = id.toString(),
    strata = AstralStrata.ASTRALASPECT
),
    IAstralGroupSymbol {
    override var groupid: ISymbolID? = null
    override var houseid: ISymbolID? = null
    override var zodiacid: ISymbolID? = null
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
        s1.groupid = id
        s2.groupid = id
        s1.related[s2.id] = s2
        s2.related[s1.id] = s1
        add(s1)
        add(s2)
    }
}
