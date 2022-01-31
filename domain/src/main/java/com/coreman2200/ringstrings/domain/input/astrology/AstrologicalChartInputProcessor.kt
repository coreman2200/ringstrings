package com.coreman2200.ringstrings.domain.input.astrology

import com.coreman2200.ringstrings.domain.AstrologySettings
import com.coreman2200.ringstrings.domain.input.InputProcessor
import com.coreman2200.ringstrings.domain.input.astrology.grouped.AspectsProcessor
import com.coreman2200.ringstrings.domain.input.entity.IProfileData
import com.coreman2200.ringstrings.domain.swisseph.ISwissEphemerisManager
import com.coreman2200.ringstrings.domain.swisseph.SwissEphemerisManager
import com.coreman2200.ringstrings.domain.symbol.Charts
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl.AstrologicalChart
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralChartSymbol

/**
 * AstrologicalChartInputProcessorImpl
 * Processes Astrological full Chart per Swisseph Manager and aspect comparator.
 *
 * Created by Cory Higginbottom on 6/8/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
class AstrologicalChartInputProcessor(
    val settings: AstrologySettings,
    val swisseph: ISwissEphemerisManager = SwissEphemerisManager(settings)
) : InputProcessor() {

    fun produceAstrologicalChart(profile: IProfileData, type: Charts): IAstralChartSymbol {
        swisseph.produceNatalAstralMappingsForProfile(profile)
        val chart: IAstralChartSymbol = AstrologicalChart(type, swisseph.cuspOffset)
        chart.profileid = profile.id

        chart.add(swisseph.producedZodiacMap())
        chart.add(swisseph.producedHouseMap())
        chart.add(swisseph.producedCelestialBodyMap())
        AspectsProcessor(chart, settings).calcAspects()

        return chart
    }
}
