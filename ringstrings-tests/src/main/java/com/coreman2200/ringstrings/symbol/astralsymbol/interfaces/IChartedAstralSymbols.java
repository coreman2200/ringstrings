package com.coreman2200.ringstrings.symbol.astralsymbol.interfaces;

import com.coreman2200.ringstrings.symbol.IChartedSymbols;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Houses;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Zodiac;

import java.util.Map;

/**
 * IChartedAstralSymbols
 * Interface for charted astral symbols
 *
 * Created by Cory Higginbottom on 6/7/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public interface IChartedAstralSymbols extends IGroupedAstralSymbols, IChartedSymbols {
    void addAstralMappings(Map<Enum<? extends Enum<?>>, IAstralSymbol> map);
    void testGenerateLoggingsForFullChart();
    // TODO:
    //Houses getHouseForBody(CelestialBodies body);
    //Zodiac getSignForBody(CelestialBodies body);
}
