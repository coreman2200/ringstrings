package com.coreman2200.ringstrings.swisseph;

import com.coreman2200.ringstrings.profile.IProfileTestLoc;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Houses;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Zodiac;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IAstralSymbol;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.ICelestialBodySymbol;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IHouseSymbol;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IZodiacSymbol;

import org.robolectric.shadows.ShadowLocation;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import swisseph.SweConst;
import swisseph.SweDate;
import swisseph.SwissEph;

/**
 * SwissEphemerisManagerImpl
 * The SwissEphemerisManager serves as an access point to the swisseph lib to calculate the necessary
 * components of an Astrological chart.
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
    private final int mAppliedHouseSystem = (int)'T'; // Polich/Page ("topocentric")
    private SwissEph mEphemeris;
    private SweDate mSwissephDate;
    private ShadowLocation mGeoLocation;

    private double armc; // TODO: "The ARMC (= sidereal time)"
    private double mCuspOffset;
    private double[] mCusp = new double[13];

    private HashMap<Houses, IHouseSymbol> mProducedHouses;
    private HashMap<Zodiac, IZodiacSymbol> mProducedZodiac;
    private HashMap<CelestialBodies, IAstralSymbol> mProducedBodies;


    public SwissEphemerisManagerImpl(String ephepath) {
        mEphemerisFilesPath = ephepath;
    }

    public void testAstrGetHousesForProfile(IProfileTestLoc profile) {
        initSwisseph();
        setDate(profile.getBirthDate());
        setLocation(profile.getBirthLocation());
        astrGetHouses();
        closeSwisseph();
    }

    public void testAstrPlaceHousesForProfile(IProfileTestLoc profile) {
        initSwisseph();
        setDate(profile.getBirthDate());
        setLocation(profile.getBirthLocation());
        astrGetHouses();
        astrPlacePlanets();
        closeSwisseph();
    }

    private void initSwisseph() {
        mEphemeris = new SwissEph(mEphemerisFilesPath);
        assert(mEphemeris != null);
    }

    private void astrGetHouses()
    {
        double[] arrayOfHouses = new double[10];
        mEphemeris.swe_houses(mSwissephDate.getJulDay(), 0, mGeoLocation.getLatitude(), mGeoLocation.getLongitude(), mAppliedHouseSystem, mCusp, arrayOfHouses);
        armc = arrayOfHouses[2];
        mCuspOffset = (mCusp[4] / 30.0);

        mProducedHouses = new HashMap<>();

        System.out.println("armc: " + armc + " offset:" + mCuspOffset);
    }

    private void astrPlacePlanets() {
        double[] topoCalcOutput = calcTopographicHousePositions();
        double eclipticObliquity = topoCalcOutput[0];

        mProducedBodies = new HashMap<>();

        for (CelestialBodies body : CelestialBodies.values()) {
            if (body.isRealCelestialBody())
                calcPlacementForBody(body, eclipticObliquity);
        }
    }

    private double[] calcTopographicHousePositions() {
        StringBuffer reterrbuff = new StringBuffer();
        double[] topoCalcOutput = new double[6];
        double julianDay = mSwissephDate.getJulDay();

        double lon = mGeoLocation.getLongitude();
        double lat = mGeoLocation.getLatitude();
        double alt = mGeoLocation.getAltitude();

        System.out.println("Setting topological position. lon:" + lon + ", lat:" + lat + ", alt:" + alt);
        mEphemeris.swe_set_topo(lon, lat, alt);
        mEphemeris.swe_calc_ut(julianDay, -1, sweCalcFlags, topoCalcOutput, reterrbuff);
        assert (reterrbuff.length() == 0);

        return topoCalcOutput;
    }

    private void calcPlacementForBody(CelestialBodies body, double eclipticObliquity) {
        StringBuffer reterrbuff = new StringBuffer();
        double[] bodiesCalcOutput = new double[6];
        double julianDay = mSwissephDate.getJulDay();
        double degInHouse, degInSign, cuspstart, longitudinalSpeed;

        System.out.print("Calculating position of " + body.toString() + "..");
        mEphemeris.swe_calc_ut(julianDay, body.getSwissephValue(), sweCalcFlags, bodiesCalcOutput, reterrbuff);
        assert (reterrbuff.length() == 0);

        double housePlacement = mEphemeris.swe_house_pos(armc, mGeoLocation.getLatitude(), eclipticObliquity, mAppliedHouseSystem, bodiesCalcOutput, reterrbuff);
        assert (reterrbuff.length() == 0);

        cuspstart = (int)(bodiesCalcOutput[0]/30.0); // Sign..
        degInSign = (float)(bodiesCalcOutput[0] % 30.0);
        degInHouse = (float)(bodiesCalcOutput[0] - mCusp[((int)housePlacement)]);
        longitudinalSpeed = bodiesCalcOutput[3];

        Houses house = getHouseForAtPosition(housePlacement);

        boolean retrograde = longitudinalSpeed < 0;

        if (retrograde)
            System.out.print("(retrograde)");
        System.out.print(" in " + house.toString() + "..");
        System.out.println(" under " + Zodiac.values()[(int) cuspstart] + "..");
    }

    private Houses getHouseForAtPosition(double placement) {
        int houseindex = getHouseIndexForPosition(placement);
        return Houses.values()[houseindex];
    }

    private int getHouseIndexForPosition(double placement) {
        int houseindex = (int)(placement - mCuspOffset) + Houses.values().length;
        return houseindex % Houses.values().length;
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

    private void closeSwisseph() {
        mEphemeris.swe_close();

    }
}
