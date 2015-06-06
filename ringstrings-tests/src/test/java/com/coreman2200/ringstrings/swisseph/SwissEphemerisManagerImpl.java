package com.coreman2200.ringstrings.swisseph;

import android.annotation.TargetApi;
import android.text.format.Time;

import com.sun.istack.internal.NotNull;

import java.util.GregorianCalendar;

import swisseph.SweDate;
import swisseph.SwissEph;

/**
 * SwissEphemerisManagerImpl
 * Implementation of SwissEphemerisManager.
 *
 * Created by Cory Higginbottom on 6/5/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class SwissEphemerisManagerImpl implements ISwissEphemerisManager {
    private final SwissEph mEphemeris;
    private SweDate mSwissephDate;
    private ShadowLocation mGeoLocation;
    private double mMaxOrb = 3.0; // Cleanup..

    public SwissEphemerisManagerImpl(String ephepath) {
        mEphemeris = new SwissEph(ephepath);
    }

    public void setDate(@NotNull GregorianCalendar date) {
        if (mSwissephDate == null) {
            mSwissephDate = new SweDate();
            mSwissephDate.setCalendarType(SweDate.SE_GREG_CAL, false);
        }

        mSwissephDate.setJulDay(getJulianDayForDate(date));
    }

    public void setLocation(@NotNull Location location) {
            mGeoLocation = location;
    }



    @TargetApi(21)
    private long getJulianDayForDate(GregorianCalendar date) {
        return Time.getJulianDay(date.getTimeInMillis(), date.getTimeZone().getRawOffset());
    }

    public void setMaxOrb(double orb)
    {
        if (orb >= 0.0D)
            mMaxOrb = orb;
    }

    private void closeSwisseph()
    {
        mEphemeris.swe_close();

    }
}
