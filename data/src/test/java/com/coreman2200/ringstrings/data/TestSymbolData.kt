package com.coreman2200.ringstrings.data

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.coreman2200.ringstrings.data.datasource.SymbolDatabaseSource
import com.coreman2200.ringstrings.data.room_common.RSDatabase
import com.coreman2200.ringstrings.data.room_common.dao.SymbolDao
import com.coreman2200.ringstrings.domain.*
import com.coreman2200.ringstrings.domain.input.astrology.AstrologicalChartInputProcessor
import com.coreman2200.ringstrings.domain.swisseph.ISwissEphemerisManager
import com.coreman2200.ringstrings.domain.swisseph.SwissEphemerisManager
import com.coreman2200.ringstrings.domain.symbol.Charts
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralChartSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.IChartedSymbols
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

/**
 * TestSymbolData
 * description
 *
 * Created by Cory Higginbottom on 1/31/22
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

@RunWith(RobolectricTestRunner::class)
class TestSymbolData {
    private val profile = MockDefaultDataBundles.testProfileBundleCoryH
    private val context: Context = ApplicationProvider.getApplicationContext<Application>()
    private val astsettings: AstrologySettings = MockDefaultDataBundles.produceDefaultAppSettingsBundle(context).astro
    private val swisseph: ISwissEphemerisManager = SwissEphemerisManager(astsettings)

    private lateinit var chart: IChartedSymbols<*>
    private lateinit var symbolDao: SymbolDao
    private lateinit var db: RSDatabase
    private lateinit var datasource: SymbolDatabaseSource

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RSDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        symbolDao = db.symbolDao()
        datasource = SymbolDatabaseSource(symbolDao)
        val testProcessor = AstrologicalChartInputProcessor(astsettings, swisseph)
        chart = testProcessor.produceAstrologicalChart(profile, Charts.ASTRAL_NATAL)
    }



    @Test
    fun insertSymbolsSavesData() {
        runBlocking {
            val list = chart.get().map { it.toData() }
            datasource.storeSymbolData(SymbolDataRequest(data = list))

            val ss = SymbolData(
                profileid = chart.profileid,
                chartid = chart.name
            )
            val response = datasource.fetchSymbolData(SymbolDataRequest(listOf(ss)))
            assert(response.symbols.isNotEmpty())
            assert(response.symbols.size == list.size)
        }
    }

    @Test
    fun getRetrievesData() {

    }


    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}