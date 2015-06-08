package com.coreman2200.ringstrings.swisseph;

import com.coreman2200.ringstrings.profile.IProfileTestLoc;
import com.coreman2200.ringstrings.symbol.ISymbol;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Houses;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Zodiac;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IAstralSymbol;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IGroupedAstralSymbols;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IHouseSymbol;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IZodiacSymbol;

import java.util.Map;


/**
 * ISwissEphemerisManager
 * Interface for utilizing swisseph library in RingStrings.
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

public interface ISwissEphemerisManager {
    void produceNatalAstralMappingsForProfile(IProfileTestLoc profile);
    void produceCurrentAstralMappingsForProfile(IProfileTestLoc profile);
    double getCuspOffset();
    Map<Enum<? extends Enum<?>>, IAstralSymbol> getProducedZodiacMap();
    Map<Enum<? extends Enum<?>>, IAstralSymbol> getProducedHouseMap();
    Map<Enum<? extends Enum<?>>, IAstralSymbol> getProducedCelestialBodyMap();
}
