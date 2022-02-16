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

    fun fetchProfileData(request: ProfileDataRequest): Flow<ProfileDataResponse>
    fun fetchProfiles(request: ProfileDataRequest): Flow<ProfileDataResponse>
    fun searchProfiles(request: ProfileDataRequest): Flow<ProfileDataResponse>
    suspend fun storeProfileData(request: ProfileDataRequest): Flow<ProfileDataResponse>
}

class ProfileDatabaseSource @Inject constructor(val dao: ProfileDao) : ProfileDataSource {
    override fun fetchProfileData(request: ProfileDataRequest): Flow<ProfileDataResponse> {
        if (request.id == 0) return fetchProfiles(request)
        return dao.get(request.id).map { ProfileDataResponse(profiles = listOf(it.toData())) }
    }

    override fun fetchProfiles(request: ProfileDataRequest): Flow<ProfileDataResponse> {
        return dao.get().map { list -> ProfileDataResponse(profiles = list.map { it.toData()}) }
    }

    override fun searchProfiles(request: ProfileDataRequest): Flow<ProfileDataResponse> {
        if (request.query.isNullOrEmpty()) return fetchProfiles(request)
        val profiles = dao.search(request.query.sanitizeSearchQuery())
        return profiles.map { profileList ->
            ProfileDataResponse(profiles = profileList.map { it.toData() })
        }
    }

    private fun String?.sanitizeSearchQuery(): String {
        val query = this ?: ""
        return "*${query.replace(Regex.fromLiteral("\""), "\"\"")}*"
    }

    override suspend fun storeProfileData(request: ProfileDataRequest): Flow<ProfileDataResponse> {
        return if (request.profiles.size == 1)
            storeSingle(request.profiles[0])
        else
            storeList(request.profiles)
    }

    private suspend fun storeSingle(profile:ProfileData): Flow<ProfileDataResponse> {
        val saved = dao.insert(profile.toEntity()).toInt()
        return flow {
            emit(ProfileDataResponse(profiles = listOf(ProfileData(id = saved))))
        }
    }
    private fun storeList(list:List<ProfileData>): Flow<ProfileDataResponse> {
        dao.insertAll(list.map { it.toEntity() })
        return flow {
            emit(ProfileDataResponse(profiles = emptyList()))
        }
    }
}

fun ProfileEntity.toData(): ProfileData = ProfileData(
    id = id,
    name = name,
    displayName = displayName,
    birthPlacement = birthPlacement.toData(),
    currentPlacement = currentPlacement?.toData(),

    )

fun ProfileData.toEntity(): ProfileEntity = ProfileEntity(
    id = id,
    name = name,
    displayName = displayName,
    birthPlacement = birthPlacement.toEntity(),
    currentPlacement = currentPlacement?.toEntity(),

    )

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