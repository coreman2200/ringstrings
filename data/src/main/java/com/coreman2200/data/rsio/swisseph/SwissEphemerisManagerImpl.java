package com.coreman2200.data.rsio.swisseph;

import android.location.Location;

import com.coreman2200.domain.profiledata.IProfileDataBundle;
import com.coreman2200.domain.protos.RingStringsAppSettings;
import com.coreman2200.domain.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.domain.symbol.astralsymbol.grouped.Houses;
import com.coreman2200.domain.symbol.astralsymbol.grouped.Zodiac;
import com.coreman2200.domain.symbol.astralsymbol.impl.CelestialBodySymbolImpl;
import com.coreman2200.domain.symbol.astralsymbol.impl.FictionalBodySymbolImpl;
import com.coreman2200.domain.symbol.astralsymbol.impl.HouseSymbolImpl;
import com.coreman2200.domain.symbol.astralsymbol.impl.ZodiacSymbolImpl;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.IAstralSymbol;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.IHouseSymbol;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.IZodiacSymbol;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

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
    private final int sweCalcFlags = SweConst.SEFLG_SWIEPH | SweConst.SEFLG_TOPOCTR | SweConst.SEFLG_TRUEPOS | SweConst.SEFLG_SPEED;//33040;
    private final int mAppliedHouseSystem = (int)'T'; // Polich/Page ("topocentric")
    private String mEphemerisFilesPath;
    private SwissEph mEphemeris;
    private SweDate mSwissephDate;
    private Location mGeoLocation;
    private StringBuffer mErrorOutputBuffer;

    private double armc; // "The ARMC (= sidereal time)"
    private double mCuspOffset;
    private double mCuspOrientationOffset;
    private double[] mCusp = new double[13];
    private double[] mBodyHousePlacements = new double[CelestialBodies.values().length];

    private HashMap<Enum<? extends Enum<?>>, IAstralSymbol> mProducedHouses;
    private HashMap<Enum<? extends Enum<?>>, IAstralSymbol> mProducedZodiac;
    private HashMap<Enum<? extends Enum<?>>, IAstralSymbol> mProducedBodies;


    public SwissEphemerisManagerImpl(RingStringsAppSettings.AstrologySettings astroSettings) {
        mEphemerisFilesPath = astroSettings.ephe_dir;
    }

    public void produceNatalAstralMappingsForProfile(IProfileDataBundle profile) {
        initSwisseph();
        setDate(profile.getBirthDate());
        setLocation(profile.getBirthLocation());
        astrSetZodiac();
        astrSetHouses();
        astrPlaceBodies();
        closeSwisseph();
    }
    public void produceCurrentAstralMappingsForProfile(IProfileDataBundle profile) {
        if (profile.getCurrentLocation() == null)
            return;

        initSwisseph();
        setDate((GregorianCalendar)GregorianCalendar.getInstance());
        setLocation(profile.getCurrentLocation());
        astrSetZodiac();
        astrSetHouses();
        astrPlaceBodies();
        closeSwisseph();
    }

    private void initSwisseph() {
        mEphemeris = new SwissEph(mEphemerisFilesPath);
        assert(mEphemeris != null);
        mErrorOutputBuffer = new StringBuffer();
    }

    private void astrSetZodiac() {
        mProducedZodiac = new HashMap<>();
        double slice = (360.0 / (double)Zodiac.values().length);
        for (Zodiac sign : Zodiac.values()) {
            mProducedZodiac.put(sign, new ZodiacSymbolImpl(sign, (sign.ordinal() * slice)));
        }
    }

    private void astrSetHouses() // TODO: Constants for these numerical values..
    {
        double[] ascmc = new double[10];
        mEphemeris.swe_houses(mSwissephDate.getJulDay(), 0, mGeoLocation.getLatitude(), mGeoLocation.getLongitude(), mAppliedHouseSystem, mCusp, ascmc);
        armc = ascmc[2];
        mCuspOrientationOffset = mCusp[4];
        mCuspOffset = (mCuspOrientationOffset / Houses.values().length); // TODO: 30 => Houses.values().length ?

        mProducedHouses = new HashMap<>();

        for (Houses house : Houses.values()) {
            double deg = mCusp[house.ordinal()+1]; // SwissEph.swe_houses() populates mCusp starting at mCusp[1]..
            mProducedHouses.put(house, new HouseSymbolImpl(house, deg));
        }
    }

    private void astrPlaceBodies() {
        double[] topoCalcOutput = calcTopographicHousePositions();
        double eclipticObliquity = topoCalcOutput[0];

        mProducedBodies = new HashMap<>();

        for (CelestialBodies body : CelestialBodies.values()) {
            if (body.isRealCelestialBody())
                calcPlacementForBody(body, eclipticObliquity);
            else
                createFictionalBody(body);
        }
    }

    private double[] calcTopographicHousePositions() {

        double[] topoCalcOutput = new double[6];
        double julianDay = mSwissephDate.getJulDay();

        double lon = mGeoLocation.getLongitude();
        double lat = mGeoLocation.getLatitude();
        double alt = mGeoLocation.getAltitude();

        //System.out.println("Setting topological position. lon:" + lon + ", lat:" + lat + ", alt:" + alt);
        mEphemeris.swe_set_topo(lon, lat, alt);
        mEphemeris.swe_calc_ut(julianDay, -1, sweCalcFlags, topoCalcOutput, mErrorOutputBuffer);
        assert (mErrorOutputBuffer.length() == 0);

        return topoCalcOutput;
    }

    private void calcPlacementForBody(CelestialBodies body, double eclipticObliquity) {

        double[] bodiesCalcOutput = new double[6];
        double julianDay = mSwissephDate.getJulDay();

        mEphemeris.swe_calc_ut(julianDay, body.getSwissephValue(), sweCalcFlags, bodiesCalcOutput, mErrorOutputBuffer);
        if (mErrorOutputBuffer.length() > 0)
            System.out.println(mErrorOutputBuffer.toString());

        assert (mErrorOutputBuffer.length() == 0);

        int bodyindex = body.ordinal();
        mBodyHousePlacements[bodyindex] = mEphemeris.swe_house_pos(armc, mGeoLocation.getLatitude(), eclipticObliquity, mAppliedHouseSystem, bodiesCalcOutput, mErrorOutputBuffer);
        assert (mErrorOutputBuffer.length() == 0);

        double longitudinalSpeed = bodiesCalcOutput[3];
        boolean retrograde = longitudinalSpeed < 0;

        CelestialBodySymbolImpl celbody = new CelestialBodySymbolImpl(body, bodiesCalcOutput[0]);
        mProducedBodies.put(body, celbody);
        celbody.setRetrograde(retrograde);

        addBodyToHouse(celbody);
        addBodyToZodiac(celbody);
    }

    private void addBodyToHouse(IAstralSymbol symbol) {
        Houses house = getHouseForPosition(symbol.getAstralSymbolDegree());
        IHouseSymbol housesymbol = (IHouseSymbol)mProducedHouses.get(house);
        housesymbol.addAstralSymbol(symbol.getAstralSymbolID(), symbol);
    }

    private Houses getHouseForPosition(double placement) {
        Houses house = Houses.values()[getHouseIndexForPosition(placement)];
        return house;
    }

    private int getHouseIndexForPosition(double placement) {
        double houseplacement = wrapDegree(placement - mCuspOrientationOffset)/30.0;
        return (int)(houseplacement % Houses.values().length);
    }

    private void addBodyToZodiac(IAstralSymbol symbol) {
        Zodiac sign = getZodiacSignForPosition(symbol.getAstralSymbolDegree());
        IZodiacSymbol signsymbol = (IZodiacSymbol)mProducedZodiac.get(sign);
        signsymbol.addAstralSymbol(symbol.getAstralSymbolID(), symbol);
    }

    private Zodiac getZodiacSignForPosition(double placement) {
        int index = (int)(placement/30.0);
        Zodiac sign = Zodiac.values()[index];
        return sign;
    }

    private void createFictionalBody(CelestialBodies body) {
        switch (body) {
            case ASCENDANT:
                produceAscendantSymbol();
                break;
            case MIDHEAVEN:
                produceMidheavenSymbol();
                break;
            case SOUTHNODE:
                produceSouthNodeSymbol();
                break;
            default:
                assert (!body.isRealCelestialBody());
        }
    }

    private void produceAscendantSymbol() {
        IAstralSymbol ascendant = new CelestialBodySymbolImpl(CelestialBodies.ASCENDANT, mCuspOrientationOffset);
        mProducedBodies.put(CelestialBodies.ASCENDANT, ascendant);
        IHouseSymbol housei = (IHouseSymbol)mProducedHouses.get(Houses.HOUSEI);
        housei.addAstralSymbol(CelestialBodies.ASCENDANT, ascendant);
        addBodyToZodiac(ascendant);
    }

    private void produceMidheavenSymbol() {
        double degree = wrapDegree(mCusp[10] + mCuspOrientationOffset);
        IAstralSymbol midheaven = new CelestialBodySymbolImpl(CelestialBodies.MIDHEAVEN, degree);
        mProducedBodies.put(CelestialBodies.MIDHEAVEN, midheaven);
        IHouseSymbol housex = (IHouseSymbol)mProducedHouses.get(Houses.HOUSEX);
        housex.addAstralSymbol(CelestialBodies.MIDHEAVEN, midheaven);
        addBodyToZodiac(midheaven);
    }

    private void produceSouthNodeSymbol() {
        IAstralSymbol nnode = mProducedBodies.get(CelestialBodies.NORTHNODE);
        double northNodeDegree = nnode.getAstralSymbolDegree();
        double degree = (northNodeDegree + 180.0) % 360.0;
        double nnodeHousePlacement = mBodyHousePlacements[CelestialBodies.NORTHNODE.ordinal()];
        mBodyHousePlacements[CelestialBodies.SOUTHNODE.ordinal()] = (nnodeHousePlacement + 6.0) % 12;

        FictionalBodySymbolImpl southnode = new FictionalBodySymbolImpl(CelestialBodies.SOUTHNODE, degree);
        mProducedBodies.put(CelestialBodies.SOUTHNODE, southnode);
        addBodyToHouse(southnode);
        addBodyToZodiac(southnode);
    }

    private double wrapDegree(double degree) {
        return (360.0 + degree) % 360.0;
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

        //System.out.println("Julian date for date " + date.getTime() + " : " + mSwissephDate.getJulDay());
    }

    private void setLocation(Location location) {
        mGeoLocation = location;
    }

    private void closeSwisseph() {
        mEphemeris.swe_close();
    }

    public double getCuspOffset() {
        return mCuspOrientationOffset;
    }
    public Map<Enum<? extends Enum<?>>, IAstralSymbol> getProducedZodiacMap() {
        return mProducedZodiac;
    }
    public Map<Enum<? extends Enum<?>>, IAstralSymbol> getProducedHouseMap() {
        return mProducedHouses;
    }
    public Map<Enum<? extends Enum<?>>, IAstralSymbol> getProducedCelestialBodyMap() {
        return mProducedBodies;
    }
}
