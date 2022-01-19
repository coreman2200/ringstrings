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

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.coreman2200.ringstrings.data.datastore.serializer.SettingsSerializer
import com.coreman2200.ringstrings.data.protos.AppSettings
import com.coreman2200.ringstrings.data.protos.AstrologySettings
import com.coreman2200.ringstrings.data.protos.NumerologySettings
import com.coreman2200.ringstrings.domain.AppSettingsRequest
import com.coreman2200.ringstrings.domain.AppSettingsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import javax.inject.Inject

interface SettingsDataSource {

    companion object {
        const val SETTINGS_DATA_SOURCE_TAG = "settingsDataSource"
    }

    suspend fun fetchSettingsData(request: AppSettingsRequest): AppSettingsResponse
    suspend fun updateSettingsData(request: AppSettingsRequest): AppSettingsResponse
}

class SettingsWireStore @Inject constructor(val context: Context) : SettingsDataSource {
    private val Context.settingsWireDataStore: DataStore<AppSettings> by dataStore(
        fileName = "AppSettings.pb",
        serializer = SettingsSerializer
    )
    override suspend fun fetchSettingsData(request: AppSettingsRequest): AppSettingsResponse {
        val flow: Flow<AppSettings> = context.settingsWireDataStore.data
        val app = flow.last()

        return AppSettingsResponse(true, toDomainAppSettings(app))
    }

    override suspend fun updateSettingsData(request: AppSettingsRequest): AppSettingsResponse {
        val data = context.settingsWireDataStore.updateData { settings ->
            settings.copy(
                astro = AstrologySettings(
                    max_orb = request.maxorb,
                    ephe_dir = request.ephedir
                ),
                num = NumerologySettings(
                    number_system = NumerologySettings.NumberSystemType.values()[request.numsystem]
                )
            )
        }

        return AppSettingsResponse(true, toDomainAppSettings(data))
    }

    fun toDomainAppSettings(data: AppSettings): com.coreman2200.ringstrings.domain.AppSettings {
        val astro = data.astro ?: AstrologySettings()
        val num = data.num ?: NumerologySettings()
        return com.coreman2200.ringstrings.domain.AppSettings(
            astro = com.coreman2200.ringstrings.domain.AstrologySettings(
                astro.max_orb,
                astro.ephe_dir
            ),
            num = com.coreman2200.ringstrings.domain.NumerologySettings(num.number_system.ordinal)
        )
    }
}
