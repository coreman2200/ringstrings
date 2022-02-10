package com.coreman2200.ringstrings.domain.symbol.entitysymbol.impl

import com.coreman2200.ringstrings.domain.symbol.Charts
import com.coreman2200.ringstrings.domain.symbol.CompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.SymbolStrata
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.EntityStrata
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.IChartedSymbols
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID

/**
 * ProfileSymbol
 * description
 *
 * Created by Cory Higginbottom on 2/9/22
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

class ProfileSymbolID(val profileid: Int ) : ISymbolID {
    override fun toString(): String {
        return profileid.toString()
    }
}

class ProfileSymbol(override var profileid: Int) : CompositeSymbol<IChartedSymbols<*>>(
    id = ProfileSymbolID(profileid),
    strata = EntityStrata.PROFILE
), IChartedSymbols<IChartedSymbols<*>> {
    override var chartid: ISymbolID? = Charts.NO_CHART
    override fun value(): Double = profileid.toDouble()
}