package com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl

import com.coreman2200.ringstrings.domain.symbol.CompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.AstralStrata
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.Zodiac
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralSymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IZodiacSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID

/**
 * HouseSymbolImpl
 * Extends GroupedAstralSymbolsImpl to more specifically define HouseSymbols
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
class ZodiacSymbol(
    override val id: Zodiac,
    override val degree: Double
) : CompositeSymbol<IAstralSymbol>(
    id = id,
    strata = AstralStrata.ASTRALZODIAC
),
    IZodiacSymbol {
    override var houseid: ISymbolID? = null
    override var zodiacid: ISymbolID? = id
    override var groupid: ISymbolID? = id

    override fun add(symbol: IAstralSymbol) {
        super.add(symbol)
        symbol.setZodiacSign(this)
    }

    override fun add(symbols: Collection<IAstralSymbol>) {
        super.add(symbols)
        symbols.forEach { it.setZodiacSign(this) }
    }
}
