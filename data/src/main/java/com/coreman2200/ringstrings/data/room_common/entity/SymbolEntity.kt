package com.coreman2200.ringstrings.data.room_common.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

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

@Entity(tableName = "profile_symbol_data_table", primaryKeys = ["profileid", "chartid"])
data class SymbolEntity(
    val profileid: Int,
    val chartid: String,
    val groupid: String,
    val symbolid: String,
    val strata: String,
    val type: String, // SymbolStrata.symbolStrataFor(strata)
    val value: Double,
    val relations: List<String>
)

@Entity(tableName = "symbol_description_table")
data class SymbolDetailEntity(
    @PrimaryKey val id:String,
    val description: String,
    val qualities: List<String>
)