package com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl

import com.coreman2200.ringstrings.domain.symbol.SymbolModel
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolStrata

/**
 * AstralSymbolImpl
 * Base implementation for Astral Symbols
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
abstract class AstralSymbol(
    override val id: ISymbolID,
    override val name: String = id.toString(),
    override val strata: ISymbolStrata,
    override var size: Int = 1
) : SymbolModel(id, name, strata, size), IAstralSymbol {
}