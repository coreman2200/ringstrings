package com.coreman2200.ringstrings.domain.swisseph

import com.coreman2200.ringstrings.domain.AstrologySettings

import com.coreman2200.ringstrings.domain.GeoLocation
import com.coreman2200.ringstrings.domain.GeoPlacement
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.CelestialBodies
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.Houses
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.Zodiac
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl.CelestialBodySymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl.HouseSymbol
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl.ZodiacSymbol
import com.coreman2200.ringstrings.domain.symbol.symbolinterface.ISymbolID
import swisseph.SweConst
import swisseph.SweDate
import swisseph.SwissEph
import java.util.*

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
class SwissEphemerisManager(
    val settings: AstrologySettings
) : ISwissEphemerisManager {
    private val sweCalcFlags: Int =
        SweConst.SEFLG_SWIEPH or SweConst.SEFLG_TOPOCTR or SweConst.SEFLG_TRUEPOS or SweConst.SEFLG_SPEED // 33040;
    private val mAppliedHouseSystem = 'T'.code // Polich/Page ("topocentric")
    private var ephemeris: SwissEph = SwissEph(settings.ephedir)
    private var errorBuf: StringBuffer = StringBuffer()
    private var armc = // "The ARMC (= sidereal time)"
        0.0
    private var mCuspOffset = 0.0
    override var cuspOffset = 0.0
        private set
    private val mCusp = DoubleArray(13)
    private val bodyHousePlacements = DoubleArray(CelestialBodies.values().size)
    private val houses: MutableMap<ISymbolID, HouseSymbol> = mutableMapOf()
    private val zodiac: MutableMap<ISymbolID, ZodiacSymbol> = mutableMapOf()
    private val bodies: MutableMap<ISymbolID, CelestialBodySymbol> = mutableMapOf()

    override fun producedZodiacMap(): Map<ISymbolID, ZodiacSymbol> = zodiac

    override fun producedHouseMap(): Map<ISymbolID, HouseSymbol> = houses

    override fun producedCelestialBodyMap(): Map<ISymbolID, CelestialBodySymbol> = bodies

    override fun produceNatalAstralMappingsForProfile(profile: IProfileData) {
        val placement = profile.birthPlacement
        produceAstralMappings(placement, placement.date)
    }

    override fun produceCurrentAstralMappingsForProfile(profile: IProfileData) {
        val placement = profile.currentPlacement ?: return

        produceAstralMappings(placement)
    }

    private fun produceAstralMappings(placement: GeoPlacement, cal: Calendar = GregorianCalendar.getInstance()) {
        initSwisseph()
        val date = getSweDate(cal)
        astrSetZodiac()
        astrSetHouses(placement.location, date)
        astrPlaceBodies(placement.location, date)
        closeSwisseph()
    }

    private fun initSwisseph() {
        ephemeris = SwissEph(settings.ephedir)
        errorBuf = StringBuffer()
    }

    private fun astrSetZodiac() {
        zodiac.clear()
        val slice = 360.0 / Zodiac.values().size.toDouble()
        for (sign in Zodiac.values()) {
            zodiac[sign] = ZodiacSymbol(sign, sign.ordinal * slice)
        }
    }

    private fun astrSetHouses(loc: GeoLocation, epheDate: SweDate) { // TODO: Constants for these numerical values..
        val ascmc = DoubleArray(10)
        ephemeris.swe_houses(
            epheDate.getJulDay(),
            0,
            loc.lat,
            loc.lon,
            mAppliedHouseSystem,
            mCusp,
            ascmc
        )
        armc = ascmc[2]
        cuspOffset = mCusp[4]
        mCuspOffset = cuspOffset / Houses.values().size // TODO: 30 => Houses.values().length ?
        houses.clear()
        // SwissEph.swe_houses() populates mCusp starting at mCusp[1]..
        Houses.values().forEach { houses[it] = HouseSymbol(it, mCusp[it.ordinal + 1]) }
    }

    private fun astrPlaceBodies(loc: GeoLocation, epheDate: SweDate) {
        val topoCalcOutput = calcTopographicHousePositions(loc, epheDate)
        val eclipticObliquity = topoCalcOutput[0]
        bodies.clear()
        CelestialBodies.values().forEach {
            if (it.isRealCelestialBody)
                calcPlacementForBody(
                    loc,
                    epheDate,
                    it,
                    eclipticObliquity
                )
            else createFictionalBody(it)
        }
    }

    private fun calcTopographicHousePositions(loc: GeoLocation, epheDate: SweDate): DoubleArray {
        val topoCalcOutput = DoubleArray(6)
        val julianDay: Double = epheDate.getJulDay()
        val lon = loc.lon
        val lat = loc.lat
        val alt = loc.alt

        // System.out.println("Setting topological position. lon:" + lon + ", lat:" + lat + ", alt:" + alt);
        ephemeris.swe_set_topo(lon, lat, alt)
        ephemeris.swe_calc_ut(julianDay, -1, sweCalcFlags, topoCalcOutput, errorBuf)
        assert(errorBuf.isEmpty())
        return topoCalcOutput
    }

    private fun calcPlacementForBody(loc: GeoLocation, epheDate: SweDate, body: CelestialBodies, eclipticObliquity: Double) {
        val bodiesCalcOutput = DoubleArray(6)
        val julianDay: Double = epheDate.getJulDay()
        ephemeris.swe_calc_ut(
            julianDay,
            body.swissephValue,
            sweCalcFlags,
            bodiesCalcOutput,
            errorBuf
        )

        if (errorBuf.isNotEmpty()) println(errorBuf.toString())

        assert(errorBuf.isEmpty())
        val bodyindex: Int = body.ordinal
        bodyHousePlacements[bodyindex] = ephemeris.swe_house_pos(
            armc,
            loc.lat,
            eclipticObliquity,
            mAppliedHouseSystem,
            bodiesCalcOutput,
            errorBuf
        )
        assert(errorBuf.isEmpty())
        val longitudinalSpeed = bodiesCalcOutput[3]
        val retrograde = longitudinalSpeed < 0
        val celbody = CelestialBodySymbol(body, bodiesCalcOutput[0])
        bodies[body] = celbody
        celbody.setRetrograde(retrograde)
        addBodyToHouse(celbody)
        addBodyToZodiac(celbody)
    }

    private fun addBodyToHouse(symbol: CelestialBodySymbol) {
        val house = getHouseForPosition(symbol.degree)
        val housesymbol: HouseSymbol = houses.getValue(house)
        housesymbol.add(symbol)
    }

    private fun getHouseForPosition(placement: Double): Houses {
        return Houses.values()[getHouseIndexForPosition(placement)]
    }

    private fun getHouseIndexForPosition(placement: Double): Int {
        val houseplacement = wrapDegree(placement - cuspOffset) / 30.0
        return (houseplacement % Houses.values().size).toInt()
    }

    private fun addBodyToZodiac(symbol: CelestialBodySymbol) {
        val sign: Zodiac = getZodiacSignForPosition(symbol.degree)
        val signsymbol: ZodiacSymbol = zodiac.getValue(sign)
        signsymbol.add(symbol)
    }

    private fun getZodiacSignForPosition(placement: Double): Zodiac {
        val index = (placement / 30.0).toInt()
        return Zodiac.values().get(index)
    }

    private fun createFictionalBody(body: CelestialBodies) {
        when (body) {
            CelestialBodies.ASCENDANT -> produceAscendantSymbol()
            CelestialBodies.MIDHEAVEN -> produceMidheavenSymbol()
            CelestialBodies.SOUTHNODE -> produceSouthNodeSymbol()
            else -> assert(!body.isRealCelestialBody)
        }
    }

    private fun produceAscendantSymbol() {
        val ascendant = CelestialBodySymbol(CelestialBodies.ASCENDANT, cuspOffset)
        bodies[CelestialBodies.ASCENDANT] = ascendant
        val housei: HouseSymbol = houses.getValue(Houses.HOUSEI)
        housei.add(ascendant)
        addBodyToZodiac(ascendant)
    }

    private fun produceMidheavenSymbol() {
        val degree = wrapDegree(mCusp[10] + cuspOffset)
        val midheaven = CelestialBodySymbol(CelestialBodies.MIDHEAVEN, degree)
        bodies[CelestialBodies.MIDHEAVEN] = midheaven
        val housex: HouseSymbol = houses.getValue(Houses.HOUSEX)
        housex.add(midheaven)
        addBodyToZodiac(midheaven)
    }

    private fun produceSouthNodeSymbol() {
        val nnode: CelestialBodySymbol = bodies.getValue(CelestialBodies.NORTHNODE)
        val northNodeDegree: Double = nnode.degree
        val degree = (northNodeDegree + 180.0) % 360.0
        val nnodeHousePlacement = bodyHousePlacements[CelestialBodies.NORTHNODE.ordinal]
        bodyHousePlacements[CelestialBodies.SOUTHNODE.ordinal] = (nnodeHousePlacement + 6.0) % 12
        val southnode = CelestialBodySymbol(CelestialBodies.SOUTHNODE, degree)
        bodies[CelestialBodies.SOUTHNODE] = southnode
        addBodyToHouse(southnode)
        addBodyToZodiac(southnode)
    }

    private fun wrapDegree(degree: Double): Double {
        return (360.0 + degree) % 360.0
    }

    private fun getSweDate(date: Calendar): SweDate {
        val year = date[Calendar.YEAR]
        val month = date[Calendar.MONTH] + 1
        val day = date[Calendar.DAY_OF_MONTH]
        val hour = date[Calendar.HOUR_OF_DAY]
        val minute = date[Calendar.MINUTE]
        val epheDate = SweDate()
        epheDate.setDate(year, month, day, hour + minute / 60.0)
        return epheDate

        // System.out.println("Julian date for date " + date.getTime() + " : " + mSwissephDate.getJulDay());
    }

    private fun closeSwisseph() {
        ephemeris.swe_close()
    }
}
