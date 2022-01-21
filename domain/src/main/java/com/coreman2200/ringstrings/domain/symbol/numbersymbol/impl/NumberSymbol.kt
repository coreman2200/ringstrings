package com.coreman2200.ringstrings.domain.symbol.numbersymbol.impl

import com.coreman2200.ringstrings.domain.symbol.ISymbolStrata
import com.coreman2200.ringstrings.domain.symbol.SymbolModel
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID

/**
 * NumberSymbolImpl
 * Implements ISymbol. Symbol elements relevant to Numerological aspects of the app.
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
abstract class NumberSymbol(
    override val id: ISymbolID,
    override val name: String = id.toString(),
    override val strata: ISymbolStrata,
    override val value: Int
) : SymbolModel(id, name, strata), INumberSymbol
