package com.coreman2200.ringstrings.data.file.details

import android.content.Context
import com.coreman2200.ringstrings.data.R
import com.coreman2200.ringstrings.data.file.FileHandler
import com.coreman2200.ringstrings.data.room_common.entity.SymbolDetailEntity
import com.coreman2200.ringstrings.domain.SwissephDataRequest
import com.coreman2200.ringstrings.domain.SwissephDataResponse
import com.coreman2200.ringstrings.domain.SymbolDescription
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.squareup.wire.internal.newMutableList
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import javax.inject.Inject

/**
 * SwissephFileHandlerImpl
 * Prepares swisseph files for use in app.
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
class SymbolDetailFileHandler() : // Inject application context
    FileHandler() {

    constructor(context: Context):this() {
        this.context = context
    }

    data class FileDetailModel (
        @SerializedName("Name")
        val name: String,
        @SerializedName("Description")
        val description: String,
        @SerializedName("Qualities")
        val qualities:List<String>
        )

    fun getAllDescriptions():List<SymbolDetailEntity> {
        val json : String?
        val descs : MutableList<SymbolDetailEntity> = newMutableList()

        try {
            val inputStream: InputStream = getInputStreamForRawResource(R.raw.symboldefs)
            json = inputStream.bufferedReader().use { it.readText() }

            val gson = GsonBuilder().create()
            var listType = object: TypeToken<Map<String, FileDetailModel>>() {}.type
            val map:Map<String,FileDetailModel> = gson.fromJson(json, listType)
            val list = map.map { SymbolDetailEntity(
                id = it.key,
                description = it.value.description,
                qualities = it.value.qualities
            ) }
            descs.addAll(list)
        } catch (e: IOException) {

        }
        return descs
    }

}
