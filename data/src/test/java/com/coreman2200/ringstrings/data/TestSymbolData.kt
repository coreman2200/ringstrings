package com.coreman2200.ringstrings.data

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.coreman2200.ringstrings.data.datasource.SymbolDatabaseSource
import com.coreman2200.ringstrings.data.file.details.SymbolDetailFileHandler
import com.coreman2200.ringstrings.data.room_common.RSDatabase
import com.coreman2200.ringstrings.data.room_common.dao.SymbolDao
import com.coreman2200.ringstrings.data.room_common.dao.SymbolDescriptionDao
import com.coreman2200.ringstrings.data.room_common.entity.SymbolDetailEntity
import com.coreman2200.ringstrings.domain.*
import com.coreman2200.ringstrings.domain.input.astrology.AstrologicalChartInputProcessor
import com.coreman2200.ringstrings.domain.swisseph.ISwissEphemerisManager
import com.coreman2200.ringstrings.domain.swisseph.SwissEphemerisManager
import com.coreman2200.ringstrings.domain.symbol.Charts
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralChartSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.IChartedSymbols
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
//import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
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

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(maxSdk = Build.VERSION_CODES.R, minSdk = Build.VERSION_CODES.R)
class TestSymbolData {
    private val profile = MockDefaultDataBundles.testProfileBundleCoryH
    private val context: Context = ApplicationProvider.getApplicationContext<Application>()
    private val astsettings: AstrologySettings = MockDefaultDataBundles.produceDefaultAppSettingsBundle(context).astro
    private val swisseph: ISwissEphemerisManager = SwissEphemerisManager(astsettings)

    private lateinit var chart: IChartedSymbols<*>
    private lateinit var symbolDao: SymbolDao
    private lateinit var detailDao: SymbolDescriptionDao
    private lateinit var db: RSDatabase
    private lateinit var datasource: SymbolDatabaseSource

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            context, RSDatabase::class.java)
            .setTransactionExecutor(mainCoroutineRule.testDispatcher.asExecutor())
            .setQueryExecutor(mainCoroutineRule.testDispatcher.asExecutor())
            .allowMainThreadQueries()
            .build()
        symbolDao = db.symbolDao()
        detailDao = db.detailDao()
        datasource = SymbolDatabaseSource(symbolDao)
        val testProcessor = AstrologicalChartInputProcessor(astsettings, swisseph)
        chart = testProcessor.produceAstrologicalChart(profile, Charts.ASTRAL_NATAL)

        runBlocking {
            detailDao.insertAll(getDescriptions())
        }

    }

    private fun getDescriptions():List<SymbolDetailEntity> {
        val fh = SymbolDetailFileHandler(context)
        return fh.getAllDescriptions().toList()
    }

    @Test
    fun `Assert db is prepopulated with symbol descriptions`() {
        runBlocking {
            val list = chart.get().map { it.toData() }

            list.forEach {
                val detail = detailDao.getSymbolDescription(it.symbolid).first()
                assert(detail.description.isNotEmpty())
                println("${detail.id}: ${detail.description}")
            }
        }
    }

    @Test
    fun `Assert all descriptions can be read from file`() {
        val fh = SymbolDetailFileHandler(context)
        val list = fh.getAllDescriptions().toTypedArray()
        list.forEach {
            println("${it.id}: ${it.description}")
            assert(it.description.isNotEmpty())
        }
    }

    @Test
    fun `Assert Symbol Data can be inserted and retrieved individually`() {
        runBlocking {
            val list = chart.get().map { it.toData() }
            //datasource.storeSymbolData(SymbolStoreRequest(data = list))
            list.forEach {
                datasource.storeSymbolData(SymbolStoreRequest(data = listOf(it)))

                val ss = SymbolData(
                    profileid = it.profileid,
                    chartid = it.chartid,
                    symbolid = it.symbolid
                )

                runBlocking {
                    val response = datasource.fetchSymbolData(SymbolDataRequest(ss))
                    assert(response.symbols.isNotEmpty())
                    assert(response.symbols[0].symbolid == ss.symbolid)
                    println("${response.symbols[0].symbolid} was found..")
                }
            }

        }
    }

    @Test
    fun `Assert Symbol Data can be inserted and retrieved collectively`() {
        runBlocking {
            val list = chart.get().map { it.toData() }
            datasource.storeSymbolData(SymbolStoreRequest(data = list))

            val ss = SymbolData(
                profileid = chart.profileid
            )

            runBlocking {
                val response = datasource.fetchSymbolData(SymbolDataRequest(ss))
                assert(response.symbols.isNotEmpty())
                val diff = list.map { "${it.symbolid}|${it.profileid}|${it.chartid}|${it.instanceid}" }
                    .minus(response.symbols.map { "${it.symbolid}|${it.profileid}|${it.chartid}|${it.instanceid}" })
                //val diff = list.minus(response.symbols)
                diff.forEach { println(it) }
                assert(diff.isEmpty())
                assert(list.size == response.symbols.size)
            }
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()

        //Dispatchers.resetMain()
    }
}