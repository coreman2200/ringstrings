package com.coreman2200.ringstrings.domain.input.numerology

import com.coreman2200.ringstrings.domain.symbol.SymbolModel
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbol

/**
 * INumberSymbolInputProcessor
 * Interface for number symbol input processors
 *
 * Created by Cory Higginbottom on 5/25/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
interface INumberSymbolInputProcessor {
    fun convertTextStringToNumberSymbol(text: String): INumberSymbol
    fun convertValueToNumberSymbol(value: Int): INumberSymbol
}
