package com.coreman2200.ringstrings.data.room_common.entity

import androidx.room.*
import com.coreman2200.ringstrings.domain.SymbolDescription
import com.coreman2200.ringstrings.domain.SymbolDescriptionRequest
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.grouped.TagSymbols

/**
 * SymbolEntity
 * description
 *
 *
 * Created by Cory Higginbottom on 1/13/22
 * http://github.com/coreman2200
 *
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

@Entity(tableName = "profile_symbol_data_table", primaryKeys = ["profileid", "chartid", "symbolid", "instanceid"])
data class SymbolEntity(
    val instanceid: Int,
    val profileid: Int,
    val chartid: String,
    val groupid: String,
    val symbolid: String,
    val strata: String,
    val type: Int, // SymbolStrata.symbolStrataFor(strata).ordinal
    val value: Double,
    val flag: Boolean,
    val relations: List<String>,
    val children: List<String>
)

@Entity(tableName = "symbol_description_table")
data class SymbolDetailEntity(
    @PrimaryKey val id:String,
    val description: String,
    val qualities: List<String>
)

data class SymbolAndDetails(
    @Embedded
    val symbol: SymbolEntity,
    @Relation(
        parentColumn = "symbolid",
        entityColumn = "id"
    )
    val description: SymbolDetailEntity?
)

fun SymbolDetailEntity.toData() : SymbolDescription = SymbolDescription(
    id = id,
    description = description,
    qualities = qualities.map { TagSymbols.valueOf(TagSymbols.formatTag(it)) }
)

fun SymbolDescriptionRequest.toEntity() : SymbolDetailEntity = SymbolDetailEntity(
    id = symbolid,
    description = description,
    qualities = qualities
)

fun SymbolDescription.toEntity() : SymbolDetailEntity = SymbolDetailEntity(
    id = this.id,
    description = this.description,
    qualities = this.qualities.map { it.toString() }
)