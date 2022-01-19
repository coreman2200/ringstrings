package com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces

import com.coreman2200.ringstrings.domain.symbol.SymbolModel
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl.CelestialBodySymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl.HouseSymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl.ZodiacSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ICompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID

/**
 * IAstralSymbol
 * Base implementation for Astrological Symbols
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
interface IAstralSymbol : ISymbol {
    val degree: Double

    var houseid:ISymbolID?
    var zodiacid:ISymbolID?

    fun setHouse(house:IHouseSymbol) {
        houseid = house.id
        related[house.id] = house
    }
    fun setZodiacSign(zodiac:IZodiacSymbol) {
        zodiacid = zodiac.id
        related[zodiac.id] = zodiac
    }


    fun wrapDegree(degree: Double): Double {
        return (360 + degree) % 360.0
    }
}

interface IAstralGroupSymbol : ICompositeSymbol<IAstralSymbol>, IAstralSymbol
interface IHouseSymbol : IAstralGroupSymbol
interface IZodiacSymbol : IAstralGroupSymbol
interface IAstralChartSymbol : IAstralGroupSymbol {
    fun producedCelestialBodyMap(): Map<ISymbolID, IAstralSymbol>

    fun add(group: ISymbolID, symbol: IAstralSymbol)
    fun add(map: Map<ISymbolID, IAstralSymbol>)
}
