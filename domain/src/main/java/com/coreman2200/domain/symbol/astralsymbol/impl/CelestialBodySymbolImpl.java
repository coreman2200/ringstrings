package com.coreman2200.domain.symbol.astralsymbol.impl;

import com.coreman2200.domain.symbol.astralsymbol.AstralStrata;
import com.coreman2200.domain.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.ICelestialBodySymbol;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.IHouseSymbol;
import com.coreman2200.domain.symbol.astralsymbol.interfaces.IZodiacSymbol;

/**
 * CelestialBodySymbolImpl
 * Implementation for Celestial Body Symbols
 *
 * Created by Cory Higginbottom on 6/6/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class CelestialBodySymbolImpl extends AstralSymbolImpl implements ICelestialBodySymbol {
    private enum Motion {
        DIRECT,
        RETROGRADE,
        DIRECTION
    }


    private boolean mIsRetrograde;
    private IHouseSymbol mHouse;
    private IZodiacSymbol mSign;

    public CelestialBodySymbolImpl(CelestialBodies body, double degree) {
        super(body, degree);
    }

    public void setRetrograde(boolean retrograde) {
        mIsRetrograde = retrograde;
    }

    public boolean checkInRetrogradeMotion() {
        return mIsRetrograde;
    }

    public IHouseSymbol getHouse() {
        return  mHouse;
    }

    public void setHouse(IHouseSymbol house) {
        assert (house != null);
        mHouse = house;
    }

    public IZodiacSymbol getSign() {
        return mSign;
    }

    public void setSign(IZodiacSymbol sign) {
        assert (sign != null);
        mSign = sign;
    }

    @Override
    protected void setSymbolStrata() {
        mSymbolStrata = AstralStrata.ASTRALBODY;
    }

}
