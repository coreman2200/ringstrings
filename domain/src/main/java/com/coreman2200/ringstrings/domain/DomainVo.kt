package com.coreman2200.ringstrings.domain

import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbol
import java.util.*

/**
 * DomainVo
 * description
 *
 * Created by Cory Higginbottom on 3/21/22
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

data class SymbolVo(
    val name: String,
    val strata: String,
    val value: Double,
    val flag: Boolean,
    val profileid:Int,
    val chartid:String?,
    val groupid:String?,
    val detail:String?,
    val children:List<String>?,
    val related:List<String>,
    val qualities: List<String>
)

fun ISymbol.toSymbolVo():List<SymbolVo> {
    val symbols:List<ISymbol> = this.get()
    return symbols.map {
        SymbolVo(
            profileid = it.profileid,
            chartid = it.chartid.toString(),
            groupid = it.groupid.toString(),
            name = it.id.toString(),
            strata = it.strata.toString(),
            value = it.value(),
            flag = it.flag(),
            detail = it.detail?.description,
            qualities = it.qualities().toList().sortedBy { pair -> pair.second.size }.map { tags -> tags.first.toString() },
            children = it.get().map { child -> child.id.toString() },
            related = it.related.keys.map { child -> child.toString() }
        )
    }
}

data class ProfileInputVo(
    val id:Int,
    val name: List<String>,
    val displayName: String,
    val birthPlacement: GeoInputVo,
    val currentPlacement: GeoInputVo?
)

fun ProfileInputVo.toData() = ProfileData(
    id = id,
    name = name,
    displayName = displayName,
    birthPlacement = birthPlacement.toPlacement(),
    currentPlacement = currentPlacement?.toPlacement()
)

data class GeoInputVo(
    val lat:String,
    val lon:String,
    val alt:String,
    val date:Calendar
)

fun GeoInputVo.toPlacement(): GeoPlacement = GeoPlacement(
    location = GeoLocation(this.lat.toDouble(), this.lon.toDouble(), this.alt.toDouble()),
    timestamp = this.date.timeInMillis,
    timezone = this.date.timeZone.id
)

