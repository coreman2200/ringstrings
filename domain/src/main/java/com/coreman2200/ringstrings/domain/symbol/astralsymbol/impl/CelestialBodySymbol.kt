package com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl

import com.coreman2200.ringstrings.domain.symbol.astralsymbol.AstralStrata
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.CelestialBodies
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.ICelestialBodySymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IHouseSymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IZodiacSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID

/**
 * CelestialBodySymbolImpl
 * Implementation for Celestial Body Symbols
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
class CelestialBodySymbol(
    override val id: CelestialBodies,
    override val degree: Double
) : AstralSymbol(
    id,
    strata = AstralStrata.ASTRALBODY
),
    ICelestialBodySymbol {
    private var retrograde: Boolean? = false
    private var house: ISymbolID? = null
    private var sign: ISymbolID? = null

    override fun setRetrograde(retrograde: Boolean) {
        this.retrograde = retrograde
    }

    override fun isRetrograde(): Boolean {
        return this.retrograde ?: false
    }

    override fun getHouse(): IHouseSymbol {
        return related[this.house] as IHouseSymbol
    }

    override fun setHouse(house: IHouseSymbol) {
        this.house = house.id
        related[house.id] = house
    }

    override fun setZodiacSign(sign: IZodiacSymbol) {
        this.sign = sign.id
        related[sign.id] = sign
    }

    override fun getZodiacSign(): IZodiacSymbol {
        return related[this.sign] as IZodiacSymbol
    }
}
