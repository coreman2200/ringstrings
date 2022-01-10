package com.coreman2200.ringstrings.domain.symbol.numbersymbol

import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolStrata

/**
 * NumberStrata
 * Describes the different tiers for numbers, in the numerological context/sense.
 *
 * Created by Cory Higginbottom on 5/24/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
enum class NumberStrata : ISymbolStrata {
    BASENUMBER, DERIVEDNUMBER,  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    GROUPEDNUMBERS, CHARTEDNUMBERS, RELATIONALNUMBERMAP
}