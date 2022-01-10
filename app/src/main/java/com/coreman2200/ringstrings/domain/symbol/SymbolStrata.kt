package com.coreman2200.ringstrings.domain.symbol

import com.coreman2200.ringstrings.domain.symbol.numbersymbol.NumberStrata
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.AstralStrata
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.EntityStrata
import com.coreman2200.ringstrings.domain.symbol.SymbolStrata
import java.lang.NullPointerException
import java.util.*

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
enum class SymbolStrata(vararg stratas: Enum<*>) {
    SYMBOL(NumberStrata.BASENUMBER, AstralStrata.ASTRALBODY),
    RELATED_SYMBOLS(
        NumberStrata.DERIVEDNUMBER,
        AstralStrata.ASTRALASPECT
    ),
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    GROUP(
        NumberStrata.GROUPEDNUMBERS,
        AstralStrata.ASTRALGROUP,
        AstralStrata.ASTRALZODIAC,
        AstralStrata.ASTRALHOUSE
    ),
    CHART(
        NumberStrata.CHARTEDNUMBERS,
        AstralStrata.ASTRALCHART
    ),
    RELATIONAL_MAP(NumberStrata.RELATIONALNUMBERMAP, AstralStrata.RELATIONALASTRALMAP),
    ENTITY(
        EntityStrata.TAG,
        EntityStrata.LIGHT,
        EntityStrata.RING,
        EntityStrata.PROFILE,
        EntityStrata.USER,
        EntityStrata.SOCIAL,
        EntityStrata.GROUPED,
        EntityStrata.ALL,
        EntityStrata.GLOBAL
    );

    val relevantSymbolGroup: List<Enum<*>>

    companion object {
        @JvmStatic
        fun getSymbolStrataFor(stratatype: Enum<out Enum<*>?>): SymbolStrata {
            if (stratatype.javaClass == SymbolStrata::class.java) return stratatype as SymbolStrata
            for (strata in values()) {
                if (strata.relevantSymbolGroup.contains(stratatype)) {
                    return strata
                }
            }
            throw NullPointerException()
        }
    }

    init {
        relevantSymbolGroup = Arrays.asList(*stratas)
    }
}