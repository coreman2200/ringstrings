package com.coreman2200.ringstrings.domain

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.coreman2200.ringstrings.domain.input.astrology.AstrologicalChartInputProcessor
import com.coreman2200.ringstrings.domain.input.entity.IProfileData
import com.coreman2200.ringstrings.domain.swisseph.ISwissEphemerisManager
import com.coreman2200.ringstrings.domain.swisseph.SwissEphemerisManager
import com.coreman2200.ringstrings.domain.symbol.Charts
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.IChartedSymbols
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * TestSymbols
 * Simple tests to ensure symbol structure is consistent across symbol types, correct
 * associations are built, etc.
 *
 * Created by Cory Higginbottom on 1/19/22
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

@RunWith(RobolectricTestRunner::class)
class TestSymbols {
    private val context: Context = ApplicationProvider.getApplicationContext<Application>()
    private val settings: AstrologySettings = MockDefaultDataBundles.produceDefaultAppSettingsBundle(context).astro
    private val swisseph: ISwissEphemerisManager = SwissEphemerisManager(settings)
    private var testProfile: IProfileData = MockDefaultDataBundles.testProfileBundleCoryH
    private var testProcessor: AstrologicalChartInputProcessor? = null
    private var chart:IChartedSymbols<*>? = null

    @Test
    fun `Assert Astrological Chart Processor produces charted symbols`() {
        testProcessor = AstrologicalChartInputProcessor(settings, swisseph)
        chart = testProcessor!!.produceAstrologicalChart(testProfile,Charts.ASTRAL_NATAL)
        val test = chart!!
        assert(test.chartid == test.id)
        assert(test.profileid != 0)
        assert(test.get().isNotEmpty())
        println(test.get().joinToString { it.name })
    }

}