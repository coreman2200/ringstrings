package com.coreman2200.ringstrings.swisseph;

import android.content.Context;
import android.support.annotation.NonNull;

import com.coreman2200.ringstrings.R;
import com.coreman2200.ringstrings.RSIO.FileHandlerImpl;

import java.io.File;

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

public class SwissephFileHandlerImpl extends FileHandlerImpl implements ISwissephFileHandler {
    private static SwissephFileHandlerImpl mInstance;
    private static final String _EPHEDIR = "ephe/";
    // Data files comprise Ephemeris for planetary, astroid, and moon datas between 1800 AD – 2399 AD
    private static final String _EPHEASTROIDDATA = "seas_18.se1";
    private static final String _EPHEMOONDATA = "semo_18.se1";
    private static final String _EPHEPLANETARYDATA = "sepl_18.se1";

    private SwissephFileHandlerImpl(Context context) {
        super(context);
        initEphemerisData();
    }

    static public SwissephFileHandlerImpl getInstance(@NonNull Context context) {
        if (mInstance == null || mInstance.mContext != context)
            mInstance = new SwissephFileHandlerImpl(context);

        return mInstance;
    }

    private void initEphemerisData() {
        if (!isEphemerisDataAvailable())
            moveEphemerisToDataStorage();
    }

    public boolean isEphemerisDataAvailable() {
        String[] filenames = {_EPHEASTROIDDATA, _EPHEMOONDATA, _EPHEPLANETARYDATA};

        for (String filename : filenames) {
            File file = getFileFromEpheDirectoryNamed(filename);
            if (!file.exists())
                return false;
        }
        return true;
    }

    private void moveEphemerisToDataStorage()
    {
        try
        {
            writeInputStreamToFile(getInputStreamToRawResource(R.raw.seas_18), getFileFromEpheDirectoryNamed(_EPHEASTROIDDATA));
            writeInputStreamToFile(getInputStreamToRawResource(R.raw.semo_18), getFileFromEpheDirectoryNamed(_EPHEMOONDATA));
            writeInputStreamToFile(getInputStreamToRawResource(R.raw.sepl_18), getFileFromEpheDirectoryNamed(_EPHEPLANETARYDATA));
        }
        catch (Exception localException)
        {
            throw localException;
        }
    }

    private File getFileFromEpheDirectoryNamed(String name) {
        return new File(getEphemerisPath() + name);
    }

    public final String getEphemerisPath()
    {
        String ephedir = getAbsolutePathWithAppend(_EPHEDIR);
        createDirectory(ephedir);
        return ephedir;
    }

}
