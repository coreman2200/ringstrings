package com.coreman2200.ringstrings.domain.input.astrology;

import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.domain.swisseph.ISwissEphemerisManager;
import com.coreman2200.ringstrings.domain.swisseph.SwissEphemerisManagerImpl;
import com.coreman2200.ringstrings.domain.symbol.chart.Charts;
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.Aspects;
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.AstralCharts;
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.impl.AspectedSymbols;
import com.coreman2200.ringstrings.domain.symbol.astralsymbol.interfaces.IAstralSymbol;
import com.coreman2200.ringstrings.domain.symbol.chart.AstrologicalChart;
import com.coreman2200.ringstrings.domain.input.AbstractInputProcessor;

import java.util.Collection;
import java.util.HashMap;
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

public class AstrologicalChartInputProcessorImpl extends AbstractInputProcessor {
    private IProfileDataBundle mUserProfile;
    private ISwissEphemerisManager mSwissephManager;
    private IChartedAstralSymbols mProcessedChart;

    public AstrologicalChartInputProcessorImpl(RingStringsAppSettings settings) {
        super(settings);
        mSwissephManager = new SwissEphemerisManagerImpl(settings.astro);
        assert (mSwissephManager != null);
    }

    public IChartedAstralSymbols produceAstrologicalChart(IProfileDataBundle profile, Charts type) {
        mUserProfile = profile;
        mSwissephManager.produceNatalAstralMappingsForProfile(mUserProfile);
        mProcessedChart = new AstrologicalChart(type, mSwissephManager.getCuspOffset());
        mProcessedChart.add(mSwissephManager.getProducedZodiacMap());
        mProcessedChart.add(mSwissephManager.getProducedHouseMap());
        mProcessedChart.add(mSwissephManager.getProducedCelestialBodyMap());
        calcAspects();

        return mProcessedChart;
    }

    private void calcAspects() {
        AspectedSymbols.AspectType aspectType = (mProcessedChart.getAstralSymbolID().equals(AstralCharts.CURRENT)) ?
                AspectedSymbols.AspectType.TRANSIT : AspectedSymbols.AspectType.ASPECT;

        Map<Enum<? extends Enum<?>>, IAstralSymbol> bodies = mSwissephManager.getProducedCelestialBodyMap();

        Collection<IAstralSymbol> comparelist = new LinkedList<>(bodies.values());

        HashMap<Enum<? extends Enum<?>>, IAstralSymbol> aspectList = new HashMap<>();

        for (Enum<? extends Enum<?>> body : bodies.keySet()) {
            if (!((CelestialBodies)body).isRealCelestialBody())
                continue;

            IAstralSymbol bodysymbol = bodies.get(body);
            comparelist.remove(bodysymbol);
            IAspectSymbol aspect = checkForAspects(bodysymbol, comparelist);

            if (aspect != null) {
                aspect.setType(aspectType);
                IListedAstralSymbols list = (IListedAstralSymbols)aspectList.get(aspect.getAstralSymbolID());

                if (list == null)
                    list = new ListedAstralSymbolsImpl(aspect.getAstralSymbolID());

                list.addAstralSymbol(aspect);
                aspectList.put(list.getAstralSymbolID(), list);
            }
        }
        mProcessedChart.add(aspectList);
    }

    private IAspectSymbol checkForAspects(IAstralSymbol body, Collection<IAstralSymbol> comparelist) {
        double orb = mAppSettings.astro.max_orb;
        double deg1 = body.degree();

        for (IAstralSymbol elem : comparelist) {
            double deg2 = elem.degree();
            double diff = Math.abs(deg2 - deg1);

            for (Aspects aspect : Aspects.values()) {
                if (aspect.checkValueWithinOrbOfAspect(diff, orb))
                    return new AspectedSymbols(aspect, body, elem);
            }
        }
        return null;
    }

}
