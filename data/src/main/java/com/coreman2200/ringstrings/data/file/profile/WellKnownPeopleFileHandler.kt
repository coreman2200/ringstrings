package com.coreman2200.ringstrings.data.file.profile

import android.content.Context
import com.coreman2200.ringstrings.data.R
import com.coreman2200.ringstrings.data.file.FileHandler
import com.coreman2200.ringstrings.data.room_common.entity.LocationEntity
import com.coreman2200.ringstrings.data.room_common.entity.PlacementEntity
import com.coreman2200.ringstrings.data.room_common.entity.ProfileEntity
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.squareup.wire.internal.newMutableList
import java.io.InputStream
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * MockPeopleFileHandler
 * Prepares well known people files for use in app.
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
class WellKnownPeopleFileHandler() : // Inject application context
    FileHandler() {

    constructor(context: Context):this() {
        this.context = context
    }

    data class FilePeopleJson(
        val rec: List<FilePeopleModel>
    )

    data class FilePeopleModel (
        @SerializedName("name")
        val name: String,
        @SerializedName("dt")
        val timestamp: String,
        @SerializedName("delta")
        val timeOffset:String,
        @SerializedName("lat")
        val latitude: String,
        @SerializedName("lon")
        val longitude: String,
        @SerializedName("place")
        val place:String,
        @SerializedName("sex")
        val gender:String
        )

    private fun generateProfileId(): Int = 1 + (Math.random() * (2 shl 20)).toInt()

    private fun FilePeopleModel.toProfile(): ProfileEntity = ProfileEntity(
        id = generateProfileId(),
        name = this.name.split(" "),
        displayName = this.name,
        birthPlacement = PlacementEntity(
            location = LocationEntity(
                lat = convertLatString(this.latitude),
                lon = convertLonString(this.longitude),
                alt = 0.0 // TODO ??
            ),
            timestamp = convertDTString(this.timestamp),
            timezone = ZoneOffset.ofHours(this.timeOffset.substring(0 until 3).toInt()).id
        ),
        currentPlacement = null

    )

    private fun convertDTString(text:String):Long {
        val format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val time = LocalDateTime.parse(text,format)
        return time.toInstant(ZoneOffset.UTC).toEpochMilli()

    }

    private fun convertLatString(text:String):Double = formatCoord(text, 2)
    private fun convertLonString(text:String):Double = formatCoord(text, 3)

    private fun formatCoord(text: String, digits: Int): Double {
        if (text.isEmpty()) return 0.0

        val hemisphere = hemisMulti(text.last())
        return StringBuilder(text.substring(0 until text.lastIndex))
            .insert(digits, ".")
            .replaceFirst(Regex("^0"), "")
            .toDouble() * hemisphere
    }

    private fun hemisMulti(char: Char):Int = when (char) {
        'N','E' -> 1
        'S','W' -> -1
        else -> 0
    }

    fun getAllPeopleWithLatLon():List<ProfileEntity> {
        val json : String?
        val profiles : MutableList<ProfileEntity> = newMutableList()

        val inputStream: InputStream = getInputStreamForRawResource(R.raw.wellknownpeople_bank)
        json = inputStream.bufferedReader().use { it.readText() }
        profiles.run {
            val gson = GsonBuilder().create()
            val listType = object: TypeToken<FilePeopleJson>() {}.type
            val people: FilePeopleJson = gson.fromJson(json, listType)
            val emptyPair = Pair("","")
            val list = people.rec.filterNot { Pair(it.latitude, it.longitude) == emptyPair }.map { it.toProfile() }
            addAll(list)
        }

        return profiles.sortedBy { it.displayName }
    }

}
