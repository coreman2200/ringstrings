package com.coreman2200.ringstrings.swisseph;

import com.coreman2200.ringstrings.profile.IProfileTestLoc;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Houses;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Zodiac;

import org.robolectric.shadows.ShadowLocation;

import java.util.Calendar;
import java.util.GregorianCalendar;

import swisseph.SweConst;
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
    private final int sweCalcFlags = SweConst.SEFLG_SWIEPH | SweConst.SEFLG_TRUEPOS | SweConst.SEFLG_SPEED;//33040;
    private final String mEphemerisFilesPath;
    private SwissEph mEphemeris;
    private SweDate mSwissephDate;
    private ShadowLocation mGeoLocation;
    private double mMaxOrb = 3.0; // Cleanup..

    private double armc; // TODO: "The ARMC (= sidereal time)"
    private double mCuspOffset;

    private final int mAppliedHouseSystem = (int)'T'; // Polich/Page ("topocentric")
    private double[] mCusp = new double[13];
    private double[] mLongitudinalSpeeds = new double[CelestialBodies.values().length];

    public SwissEphemerisManagerImpl(String ephepath) {
        mEphemerisFilesPath = ephepath;
    }

    private void initSwisseph() {
        mEphemeris = new SwissEph(mEphemerisFilesPath);
        assert(mEphemeris != null);
    }

    public void testAstrGetHousesForProfile(IProfileTestLoc profile) {
        initSwisseph();
        setDate(profile.getBirthDate());
        setLocation(profile.getBirthLocation());
        astrGetHouses();
        for (double cusp : mCusp)
            System.out.println("cusp: " + cusp);

        closeSwisseph();
    }

    private void astrGetHouses()
    {
        double[] arrayOfHouses = new double[10];
        mEphemeris.swe_houses(mSwissephDate.getJulDay(), 0, mGeoLocation.getLatitude(), mGeoLocation.getLongitude(), mAppliedHouseSystem, mCusp, arrayOfHouses);
        armc = arrayOfHouses[2];
        mCuspOffset = (mCusp[4] / 30.0);
        System.out.println("armc: " + armc + " offset:" + mCuspOffset);
    }

    public void testAstrPlaceHousesForProfile(IProfileTestLoc profile) {
        initSwisseph();
        setDate(profile.getBirthDate());
        setLocation(profile.getBirthLocation());
        astrGetHouses();
        astrPlacePlanets();
        closeSwisseph();
    }

    private void astrPlacePlanets()
    {
        StringBuffer reterrbuff = new StringBuffer();
        CelestialBodies[] celestialBodyValues = CelestialBodies.values();
        int celestialBodyCount = CelestialBodies.values().length;
        double[] housePosForBodies = new double[celestialBodyCount];

        /*  arrayOfDouble2 && arrayOfDouble3 on swe_calc.. output param receives...
            xx[0]:   longitude
            xx[1]:   latitude
            xx[2]:   distance in AU
            xx[3]:   speed in longitude (degree / day)
            xx[4]:   speed in latitude (degree / day)
            xx[5]:   speed in distance (AU / day)
         */
        // output array for individual celestial bodies/elems
        double[] bodiesCalcOutput = new double[6];
        // output array for orientation.
        double[] topoCalcOutput = new double[6];
        double julianDay = mSwissephDate.getJulDay();

        double lon = mGeoLocation.getLongitude();
        double lat = mGeoLocation.getLatitude();
        double alt = mGeoLocation.getAltitude();
        System.out.println("Setting topological position. lon:" + lon + ", lat:" + lat + ", alt:" + alt);
        mEphemeris.swe_set_topo(lon, lat, alt);
        mEphemeris.swe_calc_ut(julianDay, -1, sweCalcFlags, topoCalcOutput, reterrbuff);
        if (reterrbuff.length() > 0)
            throw new RuntimeException(reterrbuff.toString());

        double eclipticObliquity = topoCalcOutput[0];
        float degInHouse, degInSign;
        double cuspstart;


        for (int i = 0; i < celestialBodyCount-2; i++) { // for all non-fictitious bodies TODO: cleanup
            System.out.print("Calculating position of " + celestialBodyValues[i].toString() + "..");
            mEphemeris.swe_calc_ut(julianDay, celestialBodyValues[i].getSwissephValue(), sweCalcFlags, bodiesCalcOutput, reterrbuff);
            assert (reterrbuff.length() == 0);



            housePosForBodies[i] = mEphemeris.swe_house_pos(armc, mGeoLocation.getLatitude(), eclipticObliquity, mAppliedHouseSystem, bodiesCalcOutput, reterrbuff);
            assert (reterrbuff.length() == 0);

            cuspstart = (int)(bodiesCalcOutput[0]/30.0); // Sign..
            degInSign = (float)(bodiesCalcOutput[0] % 30.0);
            degInHouse = (float)(bodiesCalcOutput[0] - mCusp[((int)housePosForBodies[i])]);
            mLongitudinalSpeeds[i] = bodiesCalcOutput[3];


            int houseindex = (int)(housePosForBodies[i] - mCuspOffset) + 12;
            System.out.print(" in " + Houses.values()[houseindex % 12] + "..");
            System.out.println(" under " + Zodiac.values()[(int) cuspstart] + "..");

            System.out.print("degInSign: " + degInSign);
            System.out.print(" degInHouse: " + degInHouse);
            System.out.println(" retrograde: " + ((!celestialBodyValues[i].equals(CelestialBodies.NORTHNODE) && mLongitudinalSpeeds[i] < 0) ? "yes" : "no"));
        }
    }

    private void setDate(GregorianCalendar date) {
        if (mSwissephDate == null) {
            mSwissephDate = new SweDate();
            mSwissephDate.setCalendarType(SweDate.SE_GREG_CAL, false);
        }

        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);

        mSwissephDate.setDate(year, month, day, (hour + minute/60.0));

        System.out.println("Julian date for date " + date.getTime() + " : " + mSwissephDate.getJulDay());
    }

    private void setLocation(ShadowLocation location) {
        mGeoLocation = location;
    }

    public void setMaxOrb(double orb) {
        if (orb >= 0.0D)
            mMaxOrb = orb;
    }

    private void closeSwisseph() {
        mEphemeris.swe_close();

    }
}
