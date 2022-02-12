package com.coreman2200.ringstrings.domain.symbol

import com.coreman2200.ringstrings.domain.ProfileData
import com.coreman2200.ringstrings.domain.SymbolData
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.AstralStrata
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.EntityStrata
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.NumberStrata
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID

/**
 * SymbolStrata
 * Describes generic symbol stratas, as to enable cross-symbol type comparisons of scale..
 *
 * Created by Cory Higginbottom on 5/29/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

interface ISymbolStrata {
    fun produce(data:SymbolData): ISymbol? = null
}

enum class SymbolStrata(vararg stratas: Enum<*>) : ISymbolStrata {
    NONE,
    SYMBOL(NumberStrata.BASENUMBER, AstralStrata.ASTRALBODY),
    RELATED_SYMBOLS(
        NumberStrata.DERIVEDNUMBER,
        AstralStrata.ASTRALASPECT
    ),
    GROUP(
        NumberStrata.GROUPEDNUMBERS,
        AstralStrata.ASTRALZODIAC,
        AstralStrata.ASTRALHOUSE
    ),
    CHART(
        NumberStrata.CHARTEDNUMBERS,
        AstralStrata.ASTRALCHART
    ),
    // ~~~~~~~~~~~~~~~~~~~~~~~
    RELATIONAL_MAP(NumberStrata.RELATIONALNUMBERMAP, AstralStrata.RELATIONALASTRALMAP),
    ENTITY(
        EntityStrata.TAG,
        EntityStrata.SYMBOL,
        EntityStrata.GROUPED,
        EntityStrata.PROFILE,
        EntityStrata.USER,
    ),
    ENTITY_GROUP(
        EntityStrata.SOCIAL,
        EntityStrata.ALL,
        EntityStrata.GLOBAL
    );

    val relevantSymbolGroup: List<Enum<*>> = listOf(*stratas)

    companion object {
        fun symbolStrataFor(id: ISymbolStrata): SymbolStrata {
            return values().find { strata ->
                strata.relevantSymbolGroup.any { it.toString() == id.toString() }
            } ?: NONE
        }

        fun realStrataFor(id: String): ISymbolStrata {
            val group = values().find { strata ->
                strata.relevantSymbolGroup.any { it.toString() == id }
            } ?: NONE
            return group.relevantSymbolGroup.find { it.toString() == id } as ISymbolStrata
        }
    }
}
