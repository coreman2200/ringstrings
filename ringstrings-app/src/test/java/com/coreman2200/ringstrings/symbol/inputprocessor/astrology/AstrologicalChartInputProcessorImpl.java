package com.coreman2200.ringstrings.symbol.inputprocessor.astrology;

import com.coreman2200.ringstrings.profile.IProfileTestLoc;
import com.coreman2200.ringstrings.swisseph.ISwissEphemerisManager;
import com.coreman2200.ringstrings.swisseph.SwissEphemerisManagerImpl;
import com.coreman2200.ringstrings.symbol.Charts;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Aspects;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.AstralCharts;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.ringstrings.symbol.astralsymbol.impl.AspectedSymbolsImpl;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IAstralSymbol;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IChartedAstralSymbols;
import com.coreman2200.ringstrings.symbol.chart.AstrologicalChartImpl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

/**
 * AstrologicalChartInputProcessorImpl
 * Processes Astrological full Chart per Swisseph Manager and aspect comparator.
 *
 * Created by Cory Higginbottom on 6/8/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class AstrologicalChartInputProcessorImpl {
    private IProfileTestLoc mUserProfile;
    private ISwissEphemerisManager mSwissephManager;
    private IChartedAstralSymbols mProcessedChart;

    public AstrologicalChartInputProcessorImpl(IProfileTestLoc profile, String ephedir) {
        mUserProfile = profile;
        mSwissephManager = new SwissEphemerisManagerImpl(ephedir);
        assert (mSwissephManager != null);
    }

    public IChartedAstralSymbols produceAstrologicalChart(Charts type) {
        mSwissephManager.produceNatalAstralMappingsForProfile(mUserProfile);
        mProcessedChart = new AstrologicalChartImpl(type, mSwissephManager.getCuspOffset());
        mProcessedChart.addAstralMappings(mSwissephManager.getProducedZodiacMap());
        mProcessedChart.addAstralMappings(mSwissephManager.getProducedHouseMap());
        mProcessedChart.addAstralMappings(mSwissephManager.getProducedCelestialBodyMap());
        calcAspects();

        return mProcessedChart;
    }

    private void calcAspects() {
        boolean setAsTransit = (mProcessedChart.getAstralSymbolID().equals(AstralCharts.CURRENT));

        Map<Enum<? extends Enum<?>>, IAstralSymbol> bodies = mSwissephManager.getProducedCelestialBodyMap();

        Collection<IAstralSymbol> comparelist = new LinkedList<>(bodies.values());

        for (Enum<? extends Enum<?>> body : bodies.keySet()) {
            if (!((CelestialBodies)body).isRealCelestialBody())
                continue;

            IAstralSymbol bodysymbol = bodies.get(body);
            comparelist.remove(bodysymbol);
            AspectedSymbolsImpl aspect = checkForAspects(bodysymbol, comparelist);

            if (aspect != null) {
                if (setAsTransit)
                    aspect.setType(AspectedSymbolsImpl.AspectType.TRANSIT);

                mProcessedChart.addAstralSymbol(aspect.getAstralSymbolID(), aspect);
            }
        }
    }

    private AspectedSymbolsImpl checkForAspects(IAstralSymbol body, Collection<IAstralSymbol> comparelist) {
        double orb = mUserProfile.getMaxOrb();
        double deg1 = body.getAstralSymbolDegree();

        for (IAstralSymbol elem : comparelist) {
            double deg2 = elem.getAstralSymbolDegree();
            double diff = Math.abs(deg2 - deg1);

            for (Aspects aspect : Aspects.values()) {
                if (aspect.checkValueWithinOrbOfAspect(diff, orb))
                    return new AspectedSymbolsImpl(aspect, body, elem);
            }
        }
        return null;
    }

}
