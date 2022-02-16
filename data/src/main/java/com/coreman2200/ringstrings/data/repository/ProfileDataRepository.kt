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
package com.coreman2200.ringstrings.data.repository

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.left
import arrow.core.right
import com.coreman2200.ringstrings.data.datasource.ProfileDataSource
import com.coreman2200.ringstrings.domain.*
import kotlinx.coroutines.flow.*

object ProfileDataRepository :
    DomainLayerContract.Data.ProfileDataRepository<ProfileDataResponse> {

    lateinit var profileDataSource: ProfileDataSource

    override suspend fun fetchProfile(request: ProfileDataRequest): Either<Failure, ProfileDataResponse> {
        val response = profileDataSource.fetchProfileData(request = request)
        response.catch { Failure.NoData(it.localizedMessage ?: "No Data Found").left() }.collect()
        return response.firstOrNull()?.right() ?: run { Failure.NoData().left() }
    }

    override suspend fun searchProfiles(request: ProfileDataRequest): Either<Failure, ProfileDataResponse> {
        val response = profileDataSource.searchProfiles(request = request)
        response.catch { Failure.NoData(it.localizedMessage ?: "No Data Found").left() }.collect()
        return response.firstOrNull()?.right() ?: run { Failure.NoData().left() }
    }

    override suspend fun storeProfile(request: ProfileDataRequest): Either<Failure, ProfileDataResponse> {
        val response = profileDataSource.storeProfileData(request = request)
        response.catch { Failure.NoData(it.localizedMessage ?: "No Data Stored").left() }.collect()
        return response.firstOrNull()?.right() ?: run { Failure.NoData().left() }
    }
}
