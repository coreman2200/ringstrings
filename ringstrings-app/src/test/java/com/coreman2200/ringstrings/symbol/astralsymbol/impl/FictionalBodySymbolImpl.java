package com.coreman2200.ringstrings.symbol.astralsymbol.impl;

import com.coreman2200.ringstrings.symbol.astralsymbol.AstralStrata;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IFictionalBodySymbol;

/**
 * FictionalBodySymbolImpl
 * Implementation for fictional celestial body symbols (vs 'real' bodies ie planets, asteroids..)
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

public class FictionalBodySymbolImpl extends CelestialBodySymbolImpl implements IFictionalBodySymbol {

    public FictionalBodySymbolImpl(CelestialBodies body, double degree) {
        super(body, degree);
    }

    public boolean checkInRetrogradeMotion() {
        return false;
    }
}
