package com.coreman2200.ringstrings.domain.symbol.astralsymbol

import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolStrata
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.AstralStrata

/**
 * AstralStrata
 * Defining strata for astrological symbols
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
enum class AstralStrata : ISymbolStrata {
    ASTRALBODY,  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    ASTRALHOUSE, ASTRALZODIAC, ASTRALASPECT,  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    ASTRALGROUP, ASTRALCHART, RELATIONALASTRALMAP;
}