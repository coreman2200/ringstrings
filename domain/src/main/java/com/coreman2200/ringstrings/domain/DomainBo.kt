package com.coreman2200.ringstrings.domain

import com.coreman2200.ringstrings.domain.input.entity.IProfileData
import com.coreman2200.ringstrings.domain.symbol.Charts
import com.coreman2200.ringstrings.domain.symbol.SymbolStrata
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.ICelestialBodySymbol
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.grouped.TagSymbols
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ICompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbol
import java.sql.Timestamp
import java.util.*
import kotlin.random.Random

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
    val maxorb:Double = 2.0,
    val ephedir:String? = ""
)

data class NumerologySettings(
    val numsystem:Int = 1
)

// Symbols
// _PROFILEID, _CHARTID, _STRATAID, _TYPEID, _SYMBOLID, _VALUE, _DESC
data class SymbolDataRequest(
    val data:SymbolData
)

data class SymbolStoreRequest(
    val data:List<SymbolData>
)

data class SymbolDataResponse(
    val symbols:List<SymbolData>
)

fun SymbolDataResponse.toSymbol(): ISymbol? {
    val symbolMap: MutableMap<String, ISymbol> = mutableMapOf()
    val data = symbols.sortedBy { it.type }

    data.forEach { symbolData ->
        val strata = SymbolStrata.realStrataFor(symbolData.strata)
        val children = symbolData.children
        val symbol = strata.produce(symbolData)
        if (symbol != null) {
            symbol.profileid = symbolData.profileid
            symbol.chartid = Charts.valueOf(symbolData.chartid)
            symbolMap[symbol.name] = symbol

            if (symbol is ICelestialBodySymbol) { symbol.isRetrograde = symbolData.flag }

            children.forEach {
                if (it.isEmpty()) return@forEach
                val grouped = symbol as ICompositeSymbol<ISymbol>
                val child = symbolMap[it]
                if (child != null) {
                    grouped.add(child)
                }
            }

        }
    }

    return symbolMap[data.last().symbolid]
}

data class SymbolData(
    val instanceid: Int = Random.nextInt(),
    val profileid: Int = 0,
    val chartid: String = "",
    val groupid: String = "",
    val symbolid: String = "",
    val strata: String = "",
    val type: Int = 0, // SymbolStrata.symbolStrataFor(strata).ordinal
    val value: Double = 0.0,
    val flag: Boolean = false,
    val relations: List<String> = emptyList(),
    val children: List<String> = emptyList(),
    val details: SymbolDescription? = null
)

data class SymbolDescriptionRequest(
    val symbolid: String,
    val description: String = "",
    val qualities: List<String> = emptyList()
)

data class SymbolDescriptionResponse(
    val description:SymbolDescription
)

data class SymbolDescription(
    val id:String,
    val description: String = "",
    val qualities: List<TagSymbols> = emptyList()
)

// Profile
data class ProfileDataRequest(
    val id: Int,
    val name: List<String>,
    val displayName: String,
    val fullName: String,
    val birthPlacement: GeoPlacement,
    val currentPlacement: GeoPlacement?
)

data class ProfileDataResponse(
    val profile:ProfileData
)

data class ProfileData(
    override val id: Int,
    override val name: List<String> = emptyList(),
    override val displayName: String = name.firstOrNull() + " " + name.lastOrNull(),
    override val birthPlacement: GeoPlacement = GeoPlacement(),
    override val currentPlacement: GeoPlacement? = GeoPlacement()
) : IProfileData


data class GeoLocation(
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val alt: Double = 0.0
 )

data class GeoPlacement(
    val location: GeoLocation = GeoLocation(),
    val timestamp: Long = 0,
    val timezone: String = "",
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
