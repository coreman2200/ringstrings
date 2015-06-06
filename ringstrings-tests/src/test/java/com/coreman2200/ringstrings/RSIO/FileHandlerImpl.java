package com.coreman2200.ringstrings.RSIO;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;

import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowContext;
import org.robolectric.shadows.ShadowResources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

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

abstract public class FileHandlerImpl {
    private final ShadowContext mContext;
    private final ShadowResources mResources;

    protected FileHandlerImpl(Context context) {
        mContext = Shadows.shadowOf(context);
        mResources = Shadows.shadowOf(mContext.getResources());
    }

    protected void writeInputStreamToFile(InputStream is, File file) {
        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bytearr = new byte[1024];

            while (true) {
                int i = is.read(bytearr);
                if (i <= 0) {
                    fos.close();
                    break;
                }
                fos.write(bytearr, 0, i);
            }
        }
        catch (Exception localException)
        {
            assert (false);
        }
    }

    protected InputStream getInputStreamToRawResource(int resource) {
        return mResources.openRawResource(resource);
    }

    protected void createDirectory(String directory)
    {
        File file = new File(directory);
        if (!checkIfFileDirectoryExists(file)) {
            System.out.println("createDirectory: " + directory);
            boolean made = file.mkdirs();
            assert (made);
        }
    }

    private boolean checkIfFileDirectoryExists(File dir) {
        return dir.isDirectory();
    }

    protected String getAbsolutePathWithAppend(String directory) {
        assert (!directory.substring(0,1).contentEquals(File.separator));
        final String datapath = mContext.getShadowApplication().getApplicationContext().getFilesDir().getAbsolutePath();
        return datapath + File.separator + directory;
    }
}
