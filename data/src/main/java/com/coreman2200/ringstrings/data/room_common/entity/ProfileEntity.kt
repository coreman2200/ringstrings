package com.coreman2200.ringstrings.data.room_common.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coreman2200.ringstrings.domain.GeoLocation

/**
 * ProfileEntity
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

@Entity(tableName = "user_profile_details_table")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: List<String>,
    @ColumnInfo(name = "display_name") val displayName: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @Embedded(prefix = "birth_") @ColumnInfo(name = "birth_placement") val birthPlacement: PlacementEntity?,
    @Embedded(prefix = "current_") @ColumnInfo(name = "current_placement") val currentPlacement: PlacementEntity?
)

@Entity(tableName = "user_profile_placement_details_table")
data class PlacementEntity(
    @Embedded val location: LocationEntity,
    val timestamp: Long,
    val timezone: String,
)

@Entity(tableName = "user_profile_placement_location_details_table")
data class LocationEntity(
    val lat: Double,
    val lon: Double,
    val alt: Double
)