package com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl

import com.coreman2200.ringstrings.domain.symbol.CompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.SymbolModel
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.AstralStrata
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.Houses
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralSymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.ICelestialBodySymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IHouseSymbol

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
class HouseSymbol(
    override val id: Houses,
    override val degree: Double
) : CompositeSymbol<CelestialBodySymbol>(
    id = id,
    strata = AstralStrata.ASTRALHOUSE
), IHouseSymbol {
    override fun add(symbol: CelestialBodySymbol) {
        super.add(symbol)
        symbol.setHouse(this)
    }

    override fun add(symbols: Collection<CelestialBodySymbol>) {
        super.add(symbols)
        symbols.forEach { it.setHouse(this) }
    }

}