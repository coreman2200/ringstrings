package com.coreman2200.ringstrings.domain

import com.coreman2200.ringstrings.domain.input.entity.IProfileData
import com.coreman2200.ringstrings.domain.symbol.Charts
import com.coreman2200.ringstrings.domain.symbol.SymbolStrata
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.ICelestialBodySymbol
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.EntityStrata
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.grouped.TagSymbols
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ICompositeSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbol
import kotlinx.coroutines.flow.Flow
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
    val symbolMap: MutableMap<Int, ISymbol> = mutableMapOf()
    val instanceMap: MutableMap<String, MutableList<Int>> = mutableMapOf()

    val profileCharts = symbols
        .sortedBy { it.type }
        .groupBy { Pair<Int, String?>(it.profileid, it.chartid) }
    
    profileCharts.forEach {data ->
        // Do for each profile..
        
        data.value.forEach { symbolData ->
            val symbol = produceSymbol(symbolData) ?: return@forEach
            instanceMap.addInstance(symbol.id.toString(), symbolData.instanceid)
            symbolMap[symbolData.instanceid] = symbol

            symbolData.children.forEach {
                symbol.linkChildSymbol(it, symbolMap, instanceMap)
            }
            
        }

    }

     return symbolMap[symbols.last().instanceid]
}

private fun produceSymbol(symbolData: SymbolData):ISymbol? {
    val strata = SymbolStrata.realStrataFor(symbolData.strata)
    return strata.produce(symbolData)?.apply {
        profileid = symbolData.profileid
        chartid = Charts.valueOf(symbolData.chartid)
        detail = symbolData.details

        if (this is ICelestialBodySymbol) {
            isRetrograde = symbolData.flag
        }
    }
}

private fun MutableMap<String, MutableList<Int>>.addInstance(
    name:String,
    instanceid:Int
) {
    val list = this.getOrDefault(name, mutableListOf())
    if (list.contains(instanceid)) return
    list.add(instanceid)
    this[name] = list

}

private fun MutableMap<String, MutableList<Int>>.removeInstance(
    name:String,
    instanceid:Int
) {
    val list = this.getOrDefault(name, mutableListOf())
    list.remove(instanceid)
    this[name] = list

}

private fun ISymbol.linkChildSymbol(
    id: String,
    symbolMap: MutableMap<Int, ISymbol>,
    instanceMap: MutableMap<String, MutableList<Int>>
) {
    if (id.isEmpty() || this !is ICompositeSymbol<*>) return

    val instanceList = instanceMap[id]
    val instance = instanceList?.get(0) ?: return
    val child = symbolMap[instance]
    val group = this as ICompositeSymbol<ISymbol>
    child?.let {
        group.add(it)
    }

    if (instanceList.size > 1) {
        instanceMap.removeInstance(id, instance)
    }

}

data class SymbolData(
    val instanceid: Int = Random.nextInt(),
    val profileid: Int = 0,
    val chartid: String = "",
    val groupid: String = "",
    val symbolid: String = "",
    val name: String = "",
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
    val profiles:List<ProfileData> = emptyList(),
    val id: Int = 0,
    val query: String = ""
)

data class ProfileDataResponse(
    val profiles: List<ProfileData>
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
