package com.coreman2200.ringstrings.domain.symbol.entitysymbol.impl

import com.coreman2200.ringstrings.domain.ProfileData
import com.coreman2200.ringstrings.domain.symbol.Charts
import com.coreman2200.ringstrings.domain.symbol.CompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.SymbolStrata
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.EntityStrata
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.IChartedSymbols
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID

/**
 * GroupedProfilesSymbol
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


class GroupedProfilesSymbol() : CompositeSymbol<ProfileSymbol>(
    id = ProfileSymbolID(0),
    strata = EntityStrata.SOCIAL
), IChartedSymbols<ProfileSymbol> {
    override var chartid: ISymbolID? = Charts.NO_CHART
    override fun value(): Double = size().toDouble()
}