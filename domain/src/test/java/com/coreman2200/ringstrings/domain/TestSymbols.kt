package com.coreman2200.ringstrings.domain

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.coreman2200.ringstrings.domain.input.astrology.AstrologicalChartInputProcessor
import com.coreman2200.ringstrings.domain.input.entity.IProfileData
import com.coreman2200.ringstrings.domain.input.numerology.NumerologicalChartProcessor
import com.coreman2200.ringstrings.domain.swisseph.ISwissEphemerisManager
import com.coreman2200.ringstrings.domain.swisseph.SwissEphemerisManager
import com.coreman2200.ringstrings.domain.symbol.Charts
import com.coreman2200.ringstrings.domain.symbol.SymbolStrata
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.AstralStrata
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralChartSymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.NumberStrata
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberChartSymbol
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.IChartedSymbols
import com.coreman2200.ringstrings.domain.util.toData
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*
import kotlin.system.measureTimeMillis

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
    private val astsettings: AstrologySettings = MockDefaultDataBundles.produceDefaultAppSettingsBundle(context).astro
    private val numsettings: NumerologySettings = MockDefaultDataBundles.produceDefaultAppSettingsBundle(context).num
    private val swisseph: ISwissEphemerisManager = SwissEphemerisManager(astsettings)
    private var testProfile: IProfileData = MockDefaultDataBundles.testProfileBundleCoryH
    //private var testProcessor: InputProcessor? = null
    private var chart:IChartedSymbols<*>? = null

    @Test
    fun `Assert Astrological Chart Processor produces charted symbols`() {
        val testProcessor = AstrologicalChartInputProcessor(astsettings, swisseph)
        chart = testProcessor.produceAstrologicalChart(testProfile,Charts.ASTRAL_NATAL)
        val test = chart!! as IAstralChartSymbol
        assert(test.chartid == test.id)
        assert(test.profileid != 0)
        val symbols: List<IAstralSymbol> = test.get() as List<IAstralSymbol>
        assert(symbols.isNotEmpty())
        symbols.sortedByDescending { (it.strata as AstralStrata).ordinal }.forEach { iSymbol -> println("${iSymbol.name}: ${iSymbol.houseid} | ${iSymbol.zodiacid} | ${iSymbol.groupid} | related: ${iSymbol.get().joinToString { it.name }}") }
        test.producedCelestialBodyMap().map { "[${it.key}](${it.value.degree}): ${it.value.profileid} | ${it.value.chartid} | ${it.value.groupid} | ${it.value.id} | ${it.value.strata} " }.forEach { println(it ) }

    }

    @Test
    fun `Test Astrological symbols resolve to storeable data`() {
        val testProcessor = AstrologicalChartInputProcessor(astsettings, swisseph)
        chart = testProcessor.produceAstrologicalChart(testProfile,Charts.ASTRAL_NATAL)
        val test = chart!! as IAstralChartSymbol
        val symbols: List<IAstralSymbol> = test.get() as List<IAstralSymbol>

        val data = symbols.map { it.toData() }
        data.forEach { println(it) }
    }

    @Test
    fun `Test time it takes to produce X charts`() {
        val count = 100
        val charts:MutableList<IAstralChartSymbol> = mutableListOf()
        val testProcessor = AstrologicalChartInputProcessor(astsettings, swisseph)
        val executionTime = measureTimeMillis {
            for (i in 1..count) {
                testProfile = MockDefaultDataBundles.generateRandomProfile()
                println("${testProfile.fullName()} ~ ${testProfile.birthDate()}")
                charts.add(testProcessor.produceAstrologicalChart(testProfile,Charts.ASTRAL_NATAL))
            }
        }
        println("Time to complete ${charts.size} charts: ${executionTime/1000.0} seconds")
    }

    @Test
    fun `Assert chart symbols each have a symbolstrata`() {
        val testProcessor = AstrologicalChartInputProcessor(astsettings, swisseph)
        chart = testProcessor.produceAstrologicalChart(testProfile,Charts.ASTRAL_NATAL)
        val test = chart!! as IAstralChartSymbol
        val symbols: List<IAstralSymbol> = test.get() as List<IAstralSymbol>
        assert(symbols.isNotEmpty())
        symbols.forEach {
            val symbolStrata = SymbolStrata.symbolStrataFor(it.strata)
            assert(symbolStrata != SymbolStrata.NONE )
            println("${it.strata} -> $symbolStrata")
        }
    }

    @Test
    fun `Assert Numerological Chart Processor produces charted symbols`() {
        val testProcessor = NumerologicalChartProcessor(testProfile,numsettings)
        chart = testProcessor.produceGroupedNumberSymbolsForProfile()
        val test = chart!! as INumberChartSymbol
        assert(test.chartid == test.id)
        assert(test.profileid != 0)
        val symbols: List<INumberSymbol> = test.get() as List<INumberSymbol>
        assert(symbols.isNotEmpty())
        symbols.sortedByDescending { (it.strata as NumberStrata).ordinal }.forEach { iSymbol -> println("${iSymbol.name}: ${iSymbol.groupid} | related: ${iSymbol.get().joinToString { it.name }}") }

    }

    @Test
    fun `Test Numerological symbols resolve to storeable data`() {
        val testProcessor = NumerologicalChartProcessor(testProfile,numsettings)
        chart = testProcessor.produceGroupedNumberSymbolsForProfile()
        val test = chart!! as INumberChartSymbol
        val symbols: List<INumberSymbol> = test.get() as List<INumberSymbol>

        val data = symbols.map { it.toData() }
        data.forEach { println(it) }
    }

}