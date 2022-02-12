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
class SwissephFileHandler() : // Inject application context
    FileHandler(),
    ISwissephFileHandler {

    constructor(context: Context):this() {
        this.context = context
    }

    private fun initEphemerisData() {
        if (!isEphemerisDataAvailable) moveEphemerisToDataStorage()
    }

    override val isEphemerisDataAvailable: Boolean
        get() {
            val filenames = arrayOf(_EPHEASTROIDDATA1, _EPHEMOONDATA1, _EPHEPLANETARYDATA1)
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
        val list = listOf(
            Pair(_EPHEASTROIDDATA1, R.raw.seas_18),
            Pair(_EPHEASTROIDDATA2, R.raw.seas_24),
            Pair(_EPHEMOONDATA1, R.raw.semo_18),
            Pair(_EPHEMOONDATA2, R.raw.semo_24),
            Pair(_EPHEPLANETARYDATA1, R.raw.sepl_18),
            Pair(_EPHEPLANETARYDATA2, R.raw.sepl_24),
        )

        try {
            list.forEach {
                writeInputStreamToFile(
                    getInputStreamForRawResource(it.second),
                    getFileFromEpheDirectoryNamed(
                        it.first
                    )
                )
            }

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
        private const val _EPHEASTROIDDATA1 = "seas_18.se1"
        private const val _EPHEMOONDATA1 = "semo_18.se1"
        private const val _EPHEPLANETARYDATA1 = "sepl_18.se1"
        private const val _EPHEASTROIDDATA2 = "seas_24.se1"
        private const val _EPHEMOONDATA2 = "semo_24.se1"
        private const val _EPHEPLANETARYDATA2 = "sepl_24.se1"
    }

    init {
        initEphemerisData()
    }
}
