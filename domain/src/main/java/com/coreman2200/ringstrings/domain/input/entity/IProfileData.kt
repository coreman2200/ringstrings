package com.coreman2200.ringstrings.domain.input.entity

import com.coreman2200.ringstrings.domain.GeoLocation
import com.coreman2200.ringstrings.domain.GeoPlacement
import java.sql.Timestamp
import java.util.*

/**
 * IProfileData
 * description
 *
 * Created by Cory Higginbottom on 1/11/22
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */


interface IProfileData {
    val id: Int
    val name: List<String>
    val displayName: String
    val birthPlacement: GeoPlacement
    val currentPlacement: GeoPlacement?

    fun firstName(): String = name[0]
    fun middleName(): String = if (name.size > 2) name[1] else "" // TODO if name has more than 3 parts..?
    fun lastName(): String = name.last()
    fun fullName(): String = name.joinToString(separator = " ")
    fun birthDay(): Int = birthPlacement.date[Calendar.DAY_OF_MONTH]
    fun birthMonth(): Int = birthPlacement.date[Calendar.MONTH]
    fun birthYear(): Int = birthPlacement.date[Calendar.YEAR]
    fun birthDate():Date = birthPlacement.date.time
    fun birthLocation(): GeoLocation = birthPlacement.location
}