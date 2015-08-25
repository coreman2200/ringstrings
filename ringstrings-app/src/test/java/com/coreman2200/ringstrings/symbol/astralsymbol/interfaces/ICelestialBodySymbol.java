package com.coreman2200.ringstrings.symbol.astralsymbol.interfaces;

import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Houses;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Zodiac;

/**
 * ICelestialBodySymbol
 * Specifies methods for all celestial body symbols.
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

public interface ICelestialBodySymbol extends IAstralSymbol {
    boolean checkInRetrogradeMotion();
    //CelestialBodies getCelestialBodySymbolID();
    IHouseSymbol getHouse();
    void setHouse(IHouseSymbol house);
    IZodiacSymbol getSign();
    void setSign(IZodiacSymbol sign);
    //Aspects getAspectWithBody(CelestialBodies body);
    //Aspects[] getAllAspects();
}
