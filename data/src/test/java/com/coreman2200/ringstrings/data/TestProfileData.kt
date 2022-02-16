package com.coreman2200.ringstrings.data

//import org.junit.jupiter.api.Test
import android.app.Application
import android.content.Context
import android.icu.text.TimeZoneFormat
import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.coreman2200.ringstrings.data.datasource.ProfileDatabaseSource
import com.coreman2200.ringstrings.data.datasource.toData
import com.coreman2200.ringstrings.data.datasource.toEntity
import com.coreman2200.ringstrings.data.room_common.RSDatabase
import com.coreman2200.ringstrings.data.room_common.dao.ProfileDao
import com.coreman2200.ringstrings.domain.*
import com.squareup.wire.internal.newMutableList
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * TestProfileData
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
class TestProfileData {
    private val profile = MockDefaultDataBundles.testProfileBundleCoryH
    private val context: Context = ApplicationProvider.getApplicationContext<Application>()

    private lateinit var profileDao: ProfileDao
    private lateinit var db: RSDatabase
    private lateinit var datasource: ProfileDatabaseSource

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
        profileDao = db.profileDao()
        datasource = ProfileDatabaseSource(profileDao)


        runBlocking {
            profileDao.insertAll(getMockPeopleData().map { it.toEntity() })

            db.compileStatement("INSERT INTO user_profile_details_table_fts(user_profile_details_table_fts) VALUES ('rebuild')")

        }

    }

    private fun getMockPeopleData(): List<ProfileData> {
        val fh = MockPeopleFileHandler(context)
        val list = newMutableList<ProfileData>()
        list.addAll(fh.getAllPeopleWithLatLon())
        list.add(profile)
        return list.sortedBy { it.displayName }
    }

    @Test
    fun `Assert db is prepopulated with Profile data`() {
        runBlocking {
            val list = getMockPeopleData()
            val response:List<ProfileData> = profileDao.get().firstOrNull()?.map { it.toData() } ?: emptyList()

            assert(response.isNotEmpty())
            assert(response.size == list.size)
        }
    }

    @Test
    fun `Assert can search Profiles by query`() {
        runBlocking {
            val list = getMockPeopleData()
            var response:List<ProfileData>
            val queryList = listOf("","Cor","Alb","Jol","A","B","C","D","E","F")
            queryList.forEach {
                response = datasource.searchProfiles(ProfileDataRequest(query = it)).first().profiles
                assert(response.isNotEmpty())
            }



            list.forEach { mock ->
                response = profileDao.search(mock.lastName()).first().map { it.toData() }
                assert(response.isNotEmpty())
                assert(response.find { it.displayName == it.displayName } != null)
            }
        }
    }

    @Test
    fun `Assert Profile Data can be retrieved individually`() {
        runBlocking {
            val list = getMockPeopleData().sortedByDescending { it.birthDate() }

            list.forEach {
                runBlocking {
                    val profile = profileDao.get(it.displayName).first()
                    assert(profile.isNotEmpty())

                    val data = profile[0].toData()
                    val zoneid = ZoneId.of(data.birthPlacement.timezone)
                    val zoned = ZonedDateTime.ofInstant(data.birthDate().toInstant(), zoneid)
                    val birthDate = zoned.format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
                    println("${data.id}: ${data.displayName} $birthDate")
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