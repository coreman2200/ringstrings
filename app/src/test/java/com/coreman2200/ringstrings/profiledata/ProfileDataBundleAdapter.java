package com.coreman2200.ringstrings.profiledata;

import android.location.Location;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * ProfileDataBundleAdapter
 * Adapter produces model of profile data from bundle that conforms to IProfileDataBundle interface.
 *
 * Created by Cory Higginbottom on 2/19/16
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class ProfileDataBundleAdapter implements IProfileDataBundle {
    private LocalProfileDataBundle mProfileData;
    private GregorianCalendar mBirthDate;
    private Location mBirthplace;
    private Location mCurrentLoc;

    public ProfileDataBundleAdapter(LocalProfileDataBundle bundle) {
        mProfileData = bundle;
        if (mProfileData.birth_placement != null) {
            mBirthDate = getDateFromPlacement(mProfileData.birth_placement);
            mBirthplace = getLocationFromPlacement(mProfileData.birth_placement);
        }

        if (mProfileData.recent_placement != null)
            mCurrentLoc = getLocationFromPlacement(mProfileData.recent_placement);
    }

    private GregorianCalendar getDateFromPlacement(LocalProfileDataBundle.Placement placement) {
        GregorianCalendar date = new GregorianCalendar(TimeZone.getTimeZone(placement.timezone));
        date.setTimeInMillis(placement.timestamp);
        return date;
    }

    private Location getLocationFromPlacement(LocalProfileDataBundle.Placement placement) {
        Location loc = new Location(mProfileData.full_name.toString());
        loc.setLatitude(placement.geo.latitude);
        loc.setLongitude(placement.geo.longitude);
        loc.setAltitude(placement.geo.altitude);

        return loc;
    }

    public String getFirstName() {
        final String retval = mProfileData.full_name.segments.get(0);
        return (retval != null) ? retval : "";
    }

    public String getMiddleName(){
        final String retval = mProfileData.full_name.segments.get(1);
        return (retval != null) ? retval : "";
    }

    public String getLastName()  { // TODO: If more than 3 names...?
        final String retval = mProfileData.full_name.segments.get(2);
        return (retval != null) ? retval : "";
    }

    public GregorianCalendar getBirthDate() {
        return mBirthDate;
    }
    public int getBirthDay() { return mBirthDate.get(Calendar.DAY_OF_MONTH); }
    public int getBirthMonth() { return mBirthDate.get(Calendar.MONTH)+1; }
    public int getBirthYear() { return mBirthDate.get(Calendar.YEAR); }

    public Location getBirthLocation() {
        return mBirthplace;
    }

    public Location getCurrentLocation() {
        return mCurrentLoc;
    }

    @Override
    public int getProfileId() {
        return mProfileData.profile_id;
    }
}
