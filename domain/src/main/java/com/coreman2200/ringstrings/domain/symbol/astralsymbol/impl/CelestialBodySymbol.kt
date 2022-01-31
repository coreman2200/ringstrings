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
    override var isRetrograde: Boolean = false

}
