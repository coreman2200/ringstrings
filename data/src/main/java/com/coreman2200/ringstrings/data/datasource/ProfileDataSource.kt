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
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface ProfileDataSource {

    companion object {
        const val PROFILE_DATA_SOURCE_TAG = "profileDataSource"
    }

    suspend fun fetchProfileData(request: ProfileDataRequest): ProfileDataResponse
    suspend fun fetchProfiles(request: ProfileDataRequest): ProfileDataResponse
    suspend fun searchProfiles(request: ProfileDataRequest): ProfileDataResponse
    suspend fun storeProfileData(request: ProfileDataRequest): ProfileDataResponse
}

class ProfileDatabaseSource @Inject constructor(val dao: ProfileDao) : ProfileDataSource {
    override suspend fun fetchProfileData(request: ProfileDataRequest): ProfileDataResponse {
        if (request.id == 0) return fetchProfiles(request)
        val profile = dao.get(request.id)
        return ProfileDataResponse(profiles = flowOf(listOf(profile.toData())) )
    }

    override suspend fun fetchProfiles(request: ProfileDataRequest): ProfileDataResponse {
        val profiles = dao.get()
        return ProfileDataResponse(profiles = profiles.map { it.toData() })
    }

    override suspend fun searchProfiles(request: ProfileDataRequest): ProfileDataResponse {
        if (request.query.isEmpty()) return fetchProfiles(request)
        val profiles = dao.search(request.query.sanitizeSearchQuery()).map { it.toData() }
        return ProfileDataResponse(profiles = profiles)
    }

    override suspend fun storeProfileData(request: ProfileDataRequest): ProfileDataResponse {
        return if (request.profiles.size == 1)
            storeSingle(request.profiles[0])
        else
            storeList(request.profiles)
    }

    private fun String?.sanitizeSearchQuery(): String {
        val query = this ?: ""
        return "*${query.replace(Regex.fromLiteral("\""), "\"\"")}*"
    }

    private suspend fun storeSingle(profile:ProfileData): ProfileDataResponse {
        val saved = dao.insert(profile.toEntity()).toInt()
        return ProfileDataResponse(profiles = flow {
            emit(listOf(ProfileData(id = saved)))
        })
    }
    private fun storeList(list:List<ProfileData>): ProfileDataResponse {
        dao.insertAll(list.map { it.toEntity() })
        return ProfileDataResponse(profiles = flow {
            emit(emptyList())
        })
    }
}

fun ProfileEntity.toData(): ProfileData = ProfileData(
    id = id,
    name = name,
    displayName = displayName,
    birthPlacement = birthPlacement.toData(),
    currentPlacement = currentPlacement?.toData(),
)

fun List<ProfileEntity>.toData(): List<ProfileData> = this.map { it.toData() }


fun ProfileData.toEntity(): ProfileEntity = ProfileEntity(
    id = id,
    name = name,
    displayName = displayName,
    birthPlacement = birthPlacement.toEntity(),
    currentPlacement = currentPlacement?.toEntity(),
)

fun List<ProfileData>.toEntity(): List<ProfileEntity> = this.map { it.toEntity() }

private fun PlacementEntity.toData(): GeoPlacement = GeoPlacement(
    location = location.toData(),
    timestamp = timestamp,
    timezone = timezone
)
private fun GeoPlacement.toEntity(): PlacementEntity = PlacementEntity(
    location = location.toEntity(),
    timestamp = timestamp,
    timezone = timezone
)
private fun GeoLocation.toEntity(): LocationEntity = LocationEntity(
    lat = lat,
    lon = lon,
    alt = alt
)
private fun LocationEntity.toData(): GeoLocation = GeoLocation(
    lat = lat,
    lon = lon,
    alt = alt
)