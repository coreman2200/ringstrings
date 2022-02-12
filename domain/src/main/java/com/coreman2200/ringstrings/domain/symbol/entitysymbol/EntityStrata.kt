package com.coreman2200.ringstrings.domain.symbol.entitysymbol

import com.coreman2200.ringstrings.domain.ProfileData
import com.coreman2200.ringstrings.domain.SymbolData
import com.coreman2200.ringstrings.domain.symbol.ISymbolStrata
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.impl.GroupedProfilesSymbol
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.impl.ProfileSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.NumberStrata
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.BaseNumbers
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.GroupedNumbers
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.BaseNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.DerivedNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.GroupedNumberSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl.NumerologicalChart
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbolID
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbol


/**
 * EntityStrata
 * Enums Entity Symbols
 *
 * Created by Cory Higginbottom on 6/10/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
enum class EntityStrata : ISymbolStrata {
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    TAG, SYMBOL, GROUPED, PROFILE, USER, SOCIAL, ALL, GLOBAL;

    override fun produce(data:SymbolData): ISymbol {
            return when (this) {
                PROFILE -> ProfileSymbol(data.profileid, data.name)
                else -> GroupedProfilesSymbol() // TODO
            }
        }

}
