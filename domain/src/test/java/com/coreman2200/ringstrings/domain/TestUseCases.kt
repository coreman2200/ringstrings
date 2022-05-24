package com.coreman2200.ringstrings.domain

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.coreman2200.ringstrings.domain.input.entity.IProfileData
import com.coreman2200.ringstrings.domain.usecase.FetchProfileDataUc
import com.coreman2200.ringstrings.domain.usecase.ProfileUseCases
import com.coreman2200.ringstrings.domain.util.Outcome
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.coroutines.suspendCoroutine

/**
 * TestUseCases
 * description
 *
 * Created by Cory Higginbottom on 5/23/22
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
class TestUseCases {

    private val context: Context = ApplicationProvider.getApplicationContext<Application>()
    private var testProfile: IProfileData = MockDefaultDataBundles.testProfileBundleCoryH
    private val mockProfileRepo = MockProfileRepo(testProfile as ProfileData)
    private val profileUCs = ProfileUseCases(mockProfileRepo)

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `test a thing`() {
        val params = ProfileDataRequest(id = testProfile.id)
        val fetch = profileUCs.fetchProfileData(params)


         fetch(MainScope(), params) {
             runBlocking {
                 it.collect { resp ->
                     when (resp) {
                         is Outcome.Success -> {
                             assert(resp.data.isNotEmpty())
                             assert(resp.data[0].id == testProfile.id)
                             assert(resp.data[0].displayName == testProfile.displayName)
                         }
                         is Outcome.Error -> assert(false)
                         else -> assert(false)
                     }
                 }

             }
         }

    }

}

class MockProfileRepo(val testProfile:ProfileData): DomainLayerContract.Data.ProfileDataRepository<ProfileDataResponse> {
    override suspend fun fetchProfile(request: ProfileDataRequest): Outcome<ProfileDataResponse> {
        return Outcome.Success(ProfileDataResponse(profiles = flowOf(listOf(testProfile))))
    }

    override suspend fun searchProfiles(request: ProfileDataRequest): Outcome<ProfileDataResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun storeProfile(request: ProfileDataRequest): Outcome<ProfileDataResponse> {
        TODO("Not yet implemented")
    }

}