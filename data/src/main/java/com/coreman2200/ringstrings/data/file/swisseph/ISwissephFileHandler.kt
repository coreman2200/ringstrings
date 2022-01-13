package com.coreman2200.ringstrings.data.file.swisseph

import com.coreman2200.ringstrings.data.datasource.SwissephDataSource
import com.coreman2200.ringstrings.data.file.IFileHandler

/**
 * ISwissephFileHandler
 * Handles swisseph-specific files necessary for app.
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
interface ISwissephFileHandler : IFileHandler, SwissephDataSource {
    val ephemerisPath: String
    val isEphemerisDataAvailable: Boolean


}
