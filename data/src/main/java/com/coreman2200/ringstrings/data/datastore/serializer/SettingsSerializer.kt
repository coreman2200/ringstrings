package com.coreman2200.ringstrings.data.datastore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.coreman2200.ringstrings.data.protos.AppSettings
import com.coreman2200.ringstrings.data.protos.AstrologySettings
import com.coreman2200.ringstrings.data.protos.NumerologySettings
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * SimpleObjectSerializer
 * description
 *
 * Created by Cory Higginbottom on 1/13/22
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

object SettingsSerializer : Serializer<AppSettings> {

    override suspend fun readFrom(input: InputStream): AppSettings {
        return if (input.available() != 0) try {
            AppSettings.ADAPTER.decode(input)
        } catch (exception: IOException) {
            throw CorruptionException("Cannot read proto", exception)
        } else {
            AppSettings()
        }
    }

    override suspend fun writeTo(t: AppSettings, output: OutputStream) {
        AppSettings.ADAPTER.encode(output, t)
    }

    override val defaultValue: AppSettings
        get() = AppSettings(
            astro = AstrologySettings(),
            num = NumerologySettings()
        )
}