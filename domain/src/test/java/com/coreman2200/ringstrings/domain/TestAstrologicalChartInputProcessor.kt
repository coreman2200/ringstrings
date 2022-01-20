package com.coreman2200.ringstrings.domain

import com.coreman2200.ringstrings.domain.MockDefaultDataBundles.produceDefaultAppSettingsBundle
import com.coreman2200.ringstrings.domain.MockDefaultDataBundles.generateRandomProfile
import org.robolectric.RobolectricTestRunner
import com.coreman2200.ringstrings.domain.input.astrology.AstrologicalChartInputProcessor
import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.coreman2200.ringstrings.domain.input.entity.IProfileData
import com.coreman2200.ringstrings.domain.swisseph.ISwissEphemerisManager
import com.coreman2200.ringstrings.domain.swisseph.SwissEphemerisManager
import com.coreman2200.ringstrings.domain.symbol.Charts
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.CelestialBodies
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralChartSymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralSymbol
import org.junit.Test
import org.junit.runner.RunWith

/**
 * TestAstrologicalChartInputProcessorImpl
 * Tests for Astrological chart production
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
@RunWith(RobolectricTestRunner::class)
class TestAstrologicalChartInputProcessor {
    private val context: Context = ApplicationProvider.getApplicationContext<Application>()
    private val settings: AstrologySettings = produceDefaultAppSettingsBundle(context).astro
    private val swisseph: ISwissEphemerisManager = SwissEphemerisManager(settings)
    private var testProfile: IProfileData = MockDefaultDataBundles.testProfileBundleCoryH
    private val mTestProcessor: AstrologicalChartInputProcessor = AstrologicalChartInputProcessor(settings, swisseph)

    @Test
    fun `test Processor produces full Natal Chart`() {
        val chart: IAstralChartSymbol =
            mTestProcessor.produceAstrologicalChart(testProfile, Charts.ASTRAL_NATAL)

        assert(chart.size() > 0)
        //chart.testGenerateLoggingsForFullChart();
    }

    //@Test
    fun `test Processor produces full Current Chart`() {
        val chart: IAstralChartSymbol =
            mTestProcessor.produceAstrologicalChart(testProfile, Charts.ASTRAL_CURRENT)
        //chart.testGenerateLoggingsForFullChart();
    }

    @Test
    fun `testChartProducesCorrectHouseForBodies`() {
        val chart: IAstralChartSymbol =
            mTestProcessor.produceAstrologicalChart(testProfile, Charts.ASTRAL_NATAL)
        for (body in CelestialBodies.values()) {
            val symbol:IAstralSymbol = chart.get(body) as IAstralSymbol
            assert(symbol.houseid != null)
        }
    }

    @Test
    fun `testChartProducesCorrectSignForBodies`() {
        val chart: IAstralChartSymbol =
            mTestProcessor.produceAstrologicalChart(testProfile, Charts.ASTRAL_NATAL)
        for (body in CelestialBodies.values()) {
            val symbol:IAstralSymbol = chart.get(body) as IAstralSymbol
            assert(symbol.zodiacid != null)
        }
    }

    @Test
    fun testDurationProcessorProducesXRandomBirthCharts() {
        testProfile = generateRandomProfile()
        var highval = 0
        var lowval = Int.MAX_VALUE
        val testCount = 1000
        var averageSize = 0.0
        val loopstart = System.currentTimeMillis()
        var chart: IAstralChartSymbol
        for (i in 0 until testCount) {
            testProfile = generateRandomProfile()
            chart = mTestProcessor.produceAstrologicalChart(testProfile, Charts.ASTRAL_NATAL)
            val gsize: Int = chart.size()
            averageSize += gsize.toDouble()
            if (gsize > highval) highval = chart.size()
            if (chart.size() < lowval) lowval = chart.size()
        }
        val elapsedtime = (System.currentTimeMillis() - loopstart) / 1000
        averageSize /= testCount.toDouble()
        println("Chart size average: $averageSize")
        println("Chart size low: $lowval")
        println("Chart size high: $highval")
        println("Time to process $testCount charts ~ $elapsedtime seconds.")
    }
}