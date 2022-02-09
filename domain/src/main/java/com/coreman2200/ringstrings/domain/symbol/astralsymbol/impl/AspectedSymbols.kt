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
    override val id: Aspects
) : CompositeSymbol<IAstralSymbol>(
    id,
    name = id.toString(),
    strata = AstralStrata.ASTRALASPECT
),
    IAstralGroupSymbol {
    override var groupid: ISymbolID? = null
    override var houseid: ISymbolID? = null
    override var zodiacid: ISymbolID? = null
    override val degree: Double
    get() = getDegreeBetweenSymbols(id, children[0], children[1])

    private fun getDegreeBetweenSymbols(
        aspect: Aspects,
        s1: IAstralSymbol,
        s2: IAstralSymbol
    ): Double {
        val deg1: Double = s1.degree
        val deg2: Double = s2.degree
        val deghalf = aspect.degree() / 2
        val min = deg1.coerceAtMost(deg2)
        return wrapDegree(min + deghalf)
    }

    override fun add(symbol: IAstralSymbol) {
        super.add(symbol)
        symbol.groupid = id
        symbol.related[id] = this
    }
}
