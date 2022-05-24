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
import com.coreman2200.ringstrings.domain.DomainLayerContract.Data.Companion.PROFILE_REPOSITORY_TAG
import com.coreman2200.ringstrings.domain.util.Failure
import com.coreman2200.ringstrings.domain.util.Outcome
import com.coreman2200.ringstrings.domain.util.toSymbol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Named

const val FETCH_PROFILE_DATA_UC_TAG = "fetchProfileDataUc"

@ExperimentalCoroutinesApi
class FetchProfileDataUc @Inject constructor(
    @Named(PROFILE_REPOSITORY_TAG)
    private val profileDataRepository: @JvmSuppressWildcards DomainLayerContract.Data.ProfileDataRepository<ProfileDataResponse>
) : DomainLayerContract.Presentation.FlowUseCase<ProfileDataRequest, List<ProfileData>>() {

    override suspend fun run(params: ProfileDataRequest?): Flow<Outcome<List<ProfileData>>> {
        return flow {
            emit(Outcome.Loading)
            params?.let {
                    when  (val res = profileDataRepository.fetchProfile(request = params)) {
                        is Outcome.Success -> res.data.profiles.collectLatest { emit(Outcome.Success(it)) }
                        is Outcome.Error -> emit(res)
                        is Outcome.Loading -> emit(res)
                    }
                } ?: emit(Outcome.Error(Failure.InputParamsError()))
            }
            .catch { emit(Outcome.Error(Failure.InputParamsError(msg = it.localizedMessage ?: it.toString()))) }
            .flowOn(Dispatchers.IO)
    }
}
