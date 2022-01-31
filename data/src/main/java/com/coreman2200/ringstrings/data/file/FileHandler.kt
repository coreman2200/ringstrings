package com.coreman2200.ringstrings.data.file

import android.content.Context
import android.content.res.Resources
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.Exception

/**
 * FileHandlerImpl
 * General file handling for app.
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
abstract class FileHandler protected constructor(protected val mContext: Context) {
    private val mResources: Resources
    protected fun writeInputStreamToString(`is`: InputStream): String? {
        try {
            val buffer = ByteArray(`is`.available())
            while (`is`.read(buffer) != -1);
            return String(buffer)
        } catch (localException: Exception) {
            localException.printStackTrace()
        }
        return null
    }

    protected fun writeInputStreamToFile(`is`: InputStream, file: File?) {
        try {
            val fos = FileOutputStream(file)
            val bytearr = ByteArray(1024)
            while (true) {
                val i = `is`.read(bytearr)
                if (i <= 0) {
                    fos.close()
                    break
                }
                fos.write(bytearr, 0, i)
            }
        } catch (localException: Exception) {
        }
    }

    protected fun getInputStreamForRawResource(resource: Int): InputStream {
        return mResources.openRawResource(resource)
    }

    protected fun createDirectory(directory: String) {
        val file = File(directory)
        if (!checkIfFileDirectoryExists(file)) {
            println("createDirectory: $directory")
            val made = file.mkdirs()
        }
    }

    private fun checkIfFileDirectoryExists(dir: File): Boolean {
        return dir.isDirectory
    }

    protected fun getAbsolutePathWithAppend(directory: String): String {
        val datapath = mContext.filesDir.absolutePath
        return datapath + File.separator + directory
    }

    init {
        mResources = mContext.resources
    }
}