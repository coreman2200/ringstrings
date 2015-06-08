package com.coreman2200.ringstrings.symbol.astralsymbol.impl;

import com.coreman2200.ringstrings.symbol.astralsymbol.AstralStrata;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.ICelestialBodySymbol;

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
    private boolean mIsRetrograde;

    public CelestialBodySymbolImpl(CelestialBodies body, double degree) {
        super(body, degree);
        mAstralStrata = AstralStrata.ASTRALBODY;
    }

    public void setRetrograde(boolean retrograde) {
        mIsRetrograde = retrograde;
    }

    public boolean checkInRetrogradeMotion() {
        return mIsRetrograde;
    }



}
