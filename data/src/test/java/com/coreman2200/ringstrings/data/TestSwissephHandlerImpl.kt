package com.coreman2200.ringstrings.data

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.test.core.app.ApplicationProvider
import arrow.core.Either
import com.coreman2200.ringstrings.data.datasource.SwissephDataSource
import com.coreman2200.ringstrings.data.repository.SwissephDataRepository
import com.coreman2200.ringstrings.domain.DomainLayerContract
import com.coreman2200.ringstrings.data.file.swisseph.ISwissephFileHandler
import com.coreman2200.ringstrings.data.file.swisseph.SwissephFileHandler
import com.coreman2200.ringstrings.domain.SwissephDataRequest
import com.coreman2200.ringstrings.domain.SwissephDataResponse
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * TestSwissephHandlerImpl
 * Let's see if we can connect successfully to swisseph
 *
 * Created by Cory Higginbottom on 5/29/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class TestSwissephHandlerImpl {

    @Test
    fun `When 'fetchSwisseph' is invoked and the data-source returns data -- A 'SwissephResponse' instance is returned`() =
        runTest {
            // given
            val mockSwissephDataSource: SwissephDataSource = mock {
                onBlocking { fetchSwissephData(request = any()) }.doReturn(getDummySwissephResponse())
            }
            val repository: DomainLayerContract.Data.SwissephDataRepository<SwissephDataResponse> =
                SwissephDataRepository.apply { swissephDataSource = mockSwissephDataSource }
            // when
            val response = repository.fetchSwisseph(request = getDummySwissephRequest())
            // then
            Assert.assertTrue(response.isRight() && (response as? Either.Right<SwissephDataResponse>) != null)
        }

    private fun getDummySwissephRequest(): SwissephDataRequest = SwissephDataRequest()

    private fun getDummySwissephResponse(): SwissephDataResponse {
        val context: Context = ApplicationProvider.getApplicationContext<Application>()
        val swissephHandler = SwissephFileHandler(context)
        return SwissephDataResponse(swissephHandler.ephemerisPath,swissephHandler.isEphemerisDataAvailable)
    }

    @Test
    fun testShadowResourcesCanProduceCorrectResourceNames() {
        val context: Context = ApplicationProvider.getApplicationContext<Application>()
        val resources: Resources = context.resources
        val filename1 = resources.getResourceName(R.raw.seas_18)
        val filename2 = resources.getResourceName(R.raw.semo_18)
        val filename3 = resources.getResourceName(R.raw.sepl_18)
        assert(filename1.isNotEmpty())
        assert(filename2.isNotEmpty())
        assert(filename3.isNotEmpty())
    }

    @Test
    fun testInitializingSwisseph() {
        val context: Context = ApplicationProvider.getApplicationContext<Application>()
        val swissephHandler: ISwissephFileHandler = SwissephFileHandler(context)

        assert(!swissephHandler.ephemerisPath.isEmpty())
        assert(swissephHandler.isEphemerisDataAvailable)
    }
}