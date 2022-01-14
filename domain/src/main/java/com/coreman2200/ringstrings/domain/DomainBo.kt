package com.coreman2200.ringstrings.domain

import com.coreman2200.ringstrings.domain.swisseph.IProfileData
import com.coreman2200.ringstrings.domain.symbol.ISymbolStrata
import com.coreman2200.ringstrings.domain.symbol.SymbolStrata
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID
import java.sql.Timestamp
import java.util.*

/**
 * DomainBo
 * description
 *
 * Created by Cory Higginbottom on 1/8/22
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

// Settings

data class AppSettingsRequest(
    val maxorb:Double = 0.0,
    val ephedir:String = "",
    val numsystem:Int = 0
)

data class AppSettingsResponse(
    val success:Boolean,
    val settings:AppSettings
)

data class AppSettings(
    val astro:AstrologySettings,
    val num:NumerologySettings
)

data class AstrologySettings(
    val maxorb:Double,
    val ephedir:String
)

data class NumerologySettings(
    val numsystem:Int
)

// Symbols
// _PROFILEID, _CHARTID, _STRATAID, _TYPEID, _SYMBOLID, _VALUE, _DESC
data class SymbolDataRequest(
    val profileid: Int,
    val chartid: String,
    val groupid: String,
    val symbolid: String,
    val strata: String,
    val type: String, // SymbolStrata.symbolStrataFor(strata)
    val id: String,
    val value: Double
)

data class SymbolDataResponse(
    val symbols:List<SymbolData>
)

data class SymbolData(
    val profileid: Int,
    val chartid: String,
    val groupid: String,
    val symbolid: String,
    val strata: String,
    val type: String, // SymbolStrata.symbolStrataFor(strata)
    val value: Double,
    val relations: List<String>,
    val description: SymbolDescription
)

data class SymbolDescriptionRequest(
    val symbolid: String
)

data class SymbolDescriptionResponse(
    val symbolid:String,
    val description:SymbolDescription
)

data class SymbolDescription(
    val id:String,
    val description: String,
    val qualities: List<String>
)

// Profile
data class ProfileDataRequest(
    val id: Int
)

data class ProfileDataResponse(
    val profile:ProfileData
)

data class ProfileData(
    override val id: Int,
    override val name: List<String>,
    override val displayName: String,
    override val fullName: String,
    override val birthPlacement: GeoPlacement,
    override val currentPlacement: GeoPlacement?
) : IProfileData


data class GeoLocation(
    val lat: Double,
    val lon: Double,
    val alt: Double
)

data class GeoPlacement(
    val location: GeoLocation,
    val timestamp: Long,
    val timezone: String,
) {
    val date: Calendar = fromTimestamp(timestamp, timezone)
    private fun fromTimestamp(millis: Long, tz: String): Calendar {
        val zone: TimeZone = TimeZone.getTimeZone(tz)
        val stamp = Timestamp(millis)
        val cal: Calendar = GregorianCalendar.getInstance(zone)
        cal.time = stamp
        return cal
    }
}

// Swiss Ephemeris files
class SwissephDataRequest

data class SwissephDataResponse(
    val path: String,
    val isAvailable: Boolean
)

sealed class Failure(var msg: String = "n/a") {
    class InputParamsError(msg: String = "Parameters cannot be null") : Failure(msg = msg)
    class ServerError(msg: String = "Server error") : Failure(msg = msg)
    class NoData(msg: String = "No data") : Failure(msg = msg)
    // class NoConnection(msg: String = "No connection") : Failure(msg = msg)
    class Unknown(msg: String = "Unknown error") : Failure(msg = msg)
}
