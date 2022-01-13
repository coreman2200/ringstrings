package com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped

import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID

/**
 * Aspects
 * Astrological Aspects
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
enum class Aspects(degree: Int) : ISymbolID {
    CONJUNCTION(0), SEMISEXTILE(30), SEXTILE(60),
    SQUARE(90), TRINE(120), QUINCUNX(150), OPPOSITION(180);

    private val mAspectDegree: Double = degree.toDouble()

    fun degree(): Double {
        return mAspectDegree
    }
}
