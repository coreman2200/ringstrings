/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.coreman2200.ringstrings.domain.usecase

import com.coreman2200.ringstrings.domain.*
import com.coreman2200.ringstrings.domain.DomainLayerContract.Data.Companion.EPHEMERIS_REPOSITORY_TAG
import com.coreman2200.ringstrings.domain.util.Failure
import com.coreman2200.ringstrings.domain.util.Outcome
import javax.inject.Inject
import javax.inject.Named

const val FETCH_SWISSEPH_DATA_UC_TAG = "fetchSwissephDataUc"

class FetchSwissephDataUc @Inject constructor(
    @Named(EPHEMERIS_REPOSITORY_TAG)
    private val swissephDataRepository: @JvmSuppressWildcards DomainLayerContract.Data.SwissephDataRepository<SwissephDataResponse>
) : DomainLayerContract.Presentation.UseCase<SwissephDataRequest, SwissephDataResponse> {

    override suspend fun run(params: SwissephDataRequest?): Outcome<SwissephDataResponse> =
        params?.let {
            swissephDataRepository.fetchSwisseph(request = params)
        } ?: run {
            Outcome.Error(Failure.InputParamsError())
        }
}
