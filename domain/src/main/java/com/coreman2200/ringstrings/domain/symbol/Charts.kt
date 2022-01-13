package com.coreman2200.ringstrings.domain.symbol

import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.IGroupedNumberSymbolID

/**
 * Charts
 * Describes Astrological and Numerological as (currently?) available chart types
 *
 * Created by Cory Higginbottom on 5/28/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
enum class Charts : IGroupedNumberSymbolID {
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    ASTRAL_NATAL, ASTRAL_CURRENT, NUMEROLOGICAL, MIXED_CHART, NO_CHART
}
