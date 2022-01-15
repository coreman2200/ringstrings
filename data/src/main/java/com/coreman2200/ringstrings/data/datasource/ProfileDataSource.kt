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
package com.coreman2200.ringstrings.data.datasource

import com.coreman2200.ringstrings.data.room_common.dao.ProfileDao
import com.coreman2200.ringstrings.data.room_common.entity.LocationEntity
import com.coreman2200.ringstrings.data.room_common.entity.PlacementEntity
import com.coreman2200.ringstrings.data.room_common.entity.ProfileEntity
import com.coreman2200.ringstrings.domain.*
import kotlinx.coroutines.flow.last
import javax.inject.Inject

interface ProfileDataSource {

    companion object {
        const val PROFILE_DATA_SOURCE_TAG = "profileDataSource"
    }

    suspend fun fetchProfileData(request: ProfileDataRequest): ProfileDataResponse
    suspend fun storeProfileData(request: ProfileDataRequest): ProfileDataResponse

}

class ProfileDatabaseSource @Inject constructor(val dao: ProfileDao) : ProfileDataSource {
    override suspend fun fetchProfileData(request: ProfileDataRequest): ProfileDataResponse {
        val profile = dao.get (request.id).last()
        return ProfileDataResponse(profile = profile.toData())
    }

    override suspend fun storeProfileData(request: ProfileDataRequest): ProfileDataResponse {
        val saved = dao.insert(request.toEntity())
        return ProfileDataResponse(profile = ProfileData(id = saved))
    }

    private fun ProfileEntity.toData() : ProfileData = ProfileData(
        id = id,
        name = name,
        displayName = displayName,
        fullName = fullName,
        birthPlacement = birthPlacement.toData(),
        currentPlacement = currentPlacement?.toData(),

    )

    private fun ProfileDataRequest.toEntity() : ProfileEntity = ProfileEntity(
        id = id,
        name = name,
        displayName = displayName,
        fullName = fullName,
        birthPlacement = birthPlacement.toEntity(),
        currentPlacement = currentPlacement?.toEntity(),

        )

    private fun PlacementEntity.toData() : GeoPlacement = GeoPlacement(
        location = location.toData(),
        timestamp = timestamp,
        timezone = timezone
    )
    private fun GeoPlacement.toEntity() : PlacementEntity = PlacementEntity(
        location = location.toEntity(),
        timestamp = timestamp,
        timezone = timezone
    )
    private fun GeoLocation.toEntity() : LocationEntity = LocationEntity(
        lat = lat,
        lon = lon,
        alt = alt
    )
    private fun LocationEntity.toData() : GeoLocation = GeoLocation(
        lat = lat,
        lon = lon,
        alt = alt
    )

}