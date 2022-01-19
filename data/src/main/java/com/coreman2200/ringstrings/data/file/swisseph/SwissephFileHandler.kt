package com.coreman2200.ringstrings.data.file.swisseph

import android.content.Context
import com.coreman2200.ringstrings.data.R
import com.coreman2200.ringstrings.data.file.FileHandler
import com.coreman2200.ringstrings.domain.SwissephDataRequest
import com.coreman2200.ringstrings.domain.SwissephDataResponse
import java.io.File
import java.lang.Exception

/**
 * SwissephFileHandlerImpl
 * Prepares swisseph files for use in app.
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
class SwissephFileHandler(context: Context) : // Inject application context
    FileHandler(context),
    ISwissephFileHandler {
    private fun initEphemerisData() {
        if (!isEphemerisDataAvailable) moveEphemerisToDataStorage()
    }

    override val isEphemerisDataAvailable: Boolean
        get() {
            val filenames = arrayOf(_EPHEASTROIDDATA, _EPHEMOONDATA, _EPHEPLANETARYDATA)
            for (filename in filenames) {
                val file = getFileFromEpheDirectoryNamed(filename)
                if (!file.exists()) return false
            }
            return true
        }

    override suspend fun fetchSwissephData(request: SwissephDataRequest): SwissephDataResponse =
        SwissephDataResponse(
            isAvailable = isEphemerisDataAvailable,
            path = ephemerisPath
        )

    private fun moveEphemerisToDataStorage() {
        try {
            writeInputStreamToFile(
                getInputStreamForRawResource(R.raw.seas_18),
                getFileFromEpheDirectoryNamed(
                    _EPHEASTROIDDATA
                )
            )
            writeInputStreamToFile(
                getInputStreamForRawResource(R.raw.semo_18),
                getFileFromEpheDirectoryNamed(
                    _EPHEMOONDATA
                )
            )
            writeInputStreamToFile(
                getInputStreamForRawResource(R.raw.sepl_18),
                getFileFromEpheDirectoryNamed(
                    _EPHEPLANETARYDATA
                )
            )
        } catch (localException: Exception) {
            throw localException
        }
    }

    private fun getFileFromEpheDirectoryNamed(name: String): File {
        return File(ephemerisPath + name)
    }

    override val ephemerisPath: String
        get() {
            val ephedir = getAbsolutePathWithAppend(_EPHEDIR)
            createDirectory(ephedir)
            return ephedir
        }

    companion object {
        private const val _EPHEDIR = "ephe/"

        // Data files comprise Ephemeris for planetary, astroid, and moon datas between 1800 AD – 2399 AD
        private const val _EPHEASTROIDDATA = "seas_18.se1"
        private const val _EPHEMOONDATA = "semo_18.se1"
        private const val _EPHEPLANETARYDATA = "sepl_18.se1"
    }

    init {
        initEphemerisData()
    }
}
