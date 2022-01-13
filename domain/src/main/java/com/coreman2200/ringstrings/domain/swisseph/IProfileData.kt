package com.coreman2200.ringstrings.domain.swisseph

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
    val fullName: String
    val birthPlacement: GeoPlacement
    val currentPlacement: GeoPlacement?

    fun firstName(): String = name[0]
    fun middleName(): String = name[1] // TODO if name has more than 3 parts..?
    fun lastName(): String = name[2]

    fun getBirthDay(): Int = birthPlacement.date[Calendar.DAY_OF_MONTH]
    fun getBirthMonth(): Int = birthPlacement.date[Calendar.MONTH]
    fun getBirthYear(): Int = birthPlacement.date[Calendar.YEAR]
}