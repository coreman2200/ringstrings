package com.coreman2200.ringstrings.data

import android.app.Application
import android.content.Context
import android.os.Build
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
import com.coreman2200.ringstrings.domain.input.numerology.NumerologicalChartProcessor
import com.coreman2200.ringstrings.domain.swisseph.ISwissEphemerisManager
import com.coreman2200.ringstrings.domain.swisseph.SwissEphemerisManager
import com.coreman2200.ringstrings.domain.symbol.Charts
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.Houses
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl.HouseSymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralChartSymbol
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.EntityStrata
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.grouped.TagSymbols
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.impl.GroupedProfilesSymbol
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.impl.ProfileSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.IChartedSymbols
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ICompositeSymbol
import com.squareup.wire.internal.newMutableList
import com.squareup.wire.internal.newMutableMap
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
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
    private val numsettings: NumerologySettings = MockDefaultDataBundles.produceDefaultAppSettingsBundle(context).num
    private val swisseph: ISwissEphemerisManager = SwissEphemerisManager(astsettings)

    private lateinit var chart: IChartedSymbols<*>
    private lateinit var mockProfileSymbol: ProfileSymbol
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

        mockProfileSymbol = getAllProfileCharts(profile)

        runBlocking {
            detailDao.insertAll(getDescriptions())
        }

    }

    private suspend fun initStoreAllChartSymbols():List<SymbolData> {
        val list:List<SymbolData> = mockProfileSymbol.getAll().map { it.toData() }
        datasource.storeSymbolData(SymbolStoreRequest(data = list))
        return list
    }

    private fun getWellKnownPeopleProfiles(): GroupedProfilesSymbol {
        val fh = MockPeopleFileHandler(context)
        val list = fh.getAllPeopleWithLatLon().map { data ->
            ProfileSymbol(data.id, data.displayName).apply {
                add(getAstralNatalChart(data))
            }
        }
        return GroupedProfilesSymbol().apply {
            add(list)
        }
    }

    private suspend fun initStoreProfileGroup(group:GroupedProfilesSymbol):List<SymbolData> {
        val list:List<SymbolData> = group.getAll().map { it.toData() }
        datasource.storeSymbolData(SymbolStoreRequest(data = list))
        return list
    }


    private fun getAllProfileCharts(profile:ProfileData): ProfileSymbol {
        val profileSymbol = ProfileSymbol(profile.id, profile.displayName)
        profileSymbol.add(getAstralNatalChart(profile))
        profileSymbol.add(getAstralCurrentChart(profile))
        profileSymbol.add(getNumberChart(profile))
        return profileSymbol
    }

    private fun getAstralNatalChart(profile:ProfileData): IChartedSymbols<*> {
        val testProcessor = AstrologicalChartInputProcessor(astsettings)
        return testProcessor.produceAstrologicalChart(profile, Charts.ASTRAL_NATAL)
    }

    private fun getAstralCurrentChart(profile:ProfileData): IChartedSymbols<*> {
        val testProcessor = AstrologicalChartInputProcessor(astsettings)
        return testProcessor.produceAstrologicalChart(profile, Charts.ASTRAL_CURRENT)
    }

    private fun getNumberChart(profile:ProfileData): IChartedSymbols<*> {
        val testProcessor = NumerologicalChartProcessor(profile, numsettings)
        return testProcessor.produceGroupedNumberSymbolsForProfile()
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
            val list = initStoreAllChartSymbols()

            val ss = SymbolData(
                profileid = chart.profileid
            )

            runBlocking {
                val response = datasource.fetchSymbolData(SymbolDataRequest(ss))
                assert(response.symbols.isNotEmpty())
                val diff = list.map { "${it.symbolid}|${it.profileid}|${it.chartid}|${it.instanceid}" }
                    .minus(response.symbols.map { "${it.symbolid}|${it.profileid}|${it.chartid}|${it.instanceid}" })
                diff.forEach { println(it) }
                assert(diff.isEmpty())
                assert(list.size == response.symbols.size)
            }
        }
    }

    @Test
    fun `Assert Symbol Data has descriptive qualities`() {
        runBlocking {
            val list = initStoreAllChartSymbols()

            val ss = SymbolData(
                profileid = chart.profileid
            )

            runBlocking {
                val response = datasource.fetchSymbolData(SymbolDataRequest(ss))
                assert(response.symbols.isNotEmpty())
                val qualityMap = newMutableMap<TagSymbols, Int>()
                val qualitySymbolMap = newMutableMap<TagSymbols, MutableList<String>>()
                response.symbols.forEach{ symbol ->
                    val details = symbol.details
                    assert(details != null)
                    assert(details?.description?.isNotEmpty()?:false)
                    val qualities = details?.qualities ?: emptyList()
                    qualities.forEach {

                        qualityMap[it] = qualityMap.getOrDefault(it,0) + 1
                        val qslist = qualitySymbolMap.getOrDefault(it, newMutableList())
                        qslist.add(symbol.symbolid)
                        qualitySymbolMap[it] = qslist
                    }
                }

                assert(qualityMap.isNotEmpty())
                qualityMap.toList().sortedByDescending { it.second } .forEach { println("${it.first}: ${it.second}") }
                qualitySymbolMap.toList().sortedByDescending { it.second.size } .forEach { println("${it.first}: ${it.second.joinToString(", ")}") }

            }
        }
    }


    @Test
    fun `Assert Symbol Data can be retrieved by group`() {
        runBlocking {
            val list = initStoreAllChartSymbols()

            Houses.values().forEach { house ->
                val ss = SymbolData(
                    profileid = chart.profileid,
                    chartid = chart.chartid.toString(),
                    groupid = house.toString()
                )
                val zz = chart.get(house) as HouseSymbol

                runBlocking {
                    val response = datasource.fetchSymbolData(SymbolDataRequest(ss))
                    val children = zz.get()
                    print("${ss.groupid} ~ ${children.size} expected elems: ")
                    children.forEach { print("${it.name}, ") }
                    println()
                    assert(response.symbols.size == children.size)

                }
            }
        }
    }

    @Test
    fun `Assert Symbol Data can be reconstructed into Domain Symbol Model groupings`() {
        runBlocking {
            val list = initStoreAllChartSymbols()
            val verify = list.sortedBy { it.type }.map{ "${it.symbolid}(${"%.2f".format(it.value)}): ${it.profileid} | ${it.chartid} | ${it.strata} | children(${it.children.size}): ${it.children.joinToString()}" }

            val ss = SymbolData(
                profileid = chart.profileid,
                chartid = chart.name
            )

            runBlocking {
                val response = datasource.fetchSymbolData(SymbolDataRequest(ss))
                assert(response.symbols.isNotEmpty())
                val symbol = response.toSymbol() as IAstralChartSymbol
                assert(symbol.size() == chart.size())
                val complete = symbol.getAll().map { it.toData() } .sortedBy { it.type }.map{ "${it.symbolid}(${"%.2f".format(it.value)}): ${it.profileid} | ${it.chartid} | ${it.strata} | children(${it.children.size}): ${it.children.joinToString()}" }
                complete.forEach {
                    println(it)
                    assert(verify.contains(it))
                }
            }
        }
    }

    @Test
    fun `Assert Symbol Qualities are aggregated for symbol groupings`() {
        runBlocking {
            val list = initStoreAllChartSymbols()

            val ss = SymbolData(
                profileid = profile.id
            )

            runBlocking {
                val response = datasource.fetchSymbolData(SymbolDataRequest(ss))
                assert(response.symbols.isNotEmpty())
                val symbol = response.toSymbol() as ICompositeSymbol<*>
                val qualites = symbol.qualities()
                assert(qualites.isNotEmpty())
                qualites.forEach { println("${it.key}(${it.value.size} elems): ${it.value.distinct().joinToString()}") }
            }
        }
    }

    @Test
    fun `Assert all Persons can be read from file`() {
        val profiles = getWellKnownPeopleProfiles()
        profiles.get().forEach {
            val qualities = it.qualities().keys.take(10) // TODO Will always be empty..
            println("${it.name}: Qualities: ${qualities.joinToString()}")
        }
    }

    @Test
    fun `Assert Persons Symbol Qualities are aggregated for Profile groupings`() {
        runBlocking {
            val group = getWellKnownPeopleProfiles()
            initStoreProfileGroup(group)

            val ss = SymbolData(
                profileid = 0,
                strata = EntityStrata.SOCIAL.toString(),
                children = group.get().map { it.profileid.toString() }
            )

            runBlocking {
                val response = datasource.fetchSymbolData(SymbolDataRequest(ss))
                assert(response.symbols.isNotEmpty())
                val symbol = response.toSymbol() as GroupedProfilesSymbol
                symbol.get().forEach {
                    val qualities = it.qualities().keys.take(10)
                    println("${it.name}: Qualities: ${qualities.joinToString()} ")
                }

            }
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}