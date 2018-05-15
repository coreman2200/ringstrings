package com.coreman2200.presentation.rsdisplay.interactors.astrology;

import com.coreman2200.domain.model.profiles.interfaces.IProfileDataBundle;
import com.coreman2200.domain.model.protos.RingStringsAppSettings;
import com.coreman2200.data.rsio.swisseph.ISwissEphemerisManager;
import com.coreman2200.data.rsio.swisseph.SwissEphemerisManagerImpl;
import com.coreman2200.domain.model.symbols.astrals.impl.ListedAstralSymbolsImpl;
import com.coreman2200.domain.model.symbols.astrals.interfaces.IAspectSymbol;
import com.coreman2200.domain.model.symbols.astrals.interfaces.IListedAstralSymbols;
import com.coreman2200.domain.model.symbols.charts.Charts;
import com.coreman2200.domain.model.symbols.astrals.grouped.Aspects;
import com.coreman2200.domain.model.symbols.astrals.grouped.AstralCharts;
import com.coreman2200.domain.model.symbols.astrals.grouped.CelestialBodies;
import com.coreman2200.domain.model.symbols.astrals.impl.AspectedSymbolsImpl;
import com.coreman2200.domain.model.symbols.astrals.interfaces.IAstralSymbol;
import com.coreman2200.domain.model.symbols.astrals.interfaces.IChartedAstralSymbols;
import com.coreman2200.domain.model.symbols.charts.AstrologicalChartImpl;
import com.coreman2200.presentation.rsdisplay.interactors.AbstractInteractor;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * AstrologicalChartInteractorImpl
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

public class AstrologicalChartInteractorImpl extends AbstractInteractor {
    private IProfileDataBundle mUserProfile;
    private ISwissEphemerisManager mSwissephManager;
    private IChartedAstralSymbols mProcessedChart;

    public AstrologicalChartInteractorImpl(RingStringsAppSettings settings) {
        super(settings);
        mSwissephManager = new SwissEphemerisManagerImpl(settings.astro);
        assert (mSwissephManager != null);
    }

    public IChartedAstralSymbols produceAstrologicalChart(IProfileDataBundle profile, Charts type) {
        mUserProfile = profile;
        mSwissephManager.produceNatalAstralMappingsForProfile(mUserProfile);
        AstralCharts astralChart = (type.equals(Charts.ASTRAL_CURRENT)) ? AstralCharts.CURRENT : AstralCharts.NATAL;
        mProcessedChart = new AstrologicalChartImpl(astralChart, mSwissephManager.getCuspOffset());
        mProcessedChart.addAstralMappings(mSwissephManager.getProducedZodiacMap());
        mProcessedChart.addAstralMappings(mSwissephManager.getProducedHouseMap());
        mProcessedChart.addAstralMappings(mSwissephManager.getProducedCelestialBodyMap());
        calcAspects();

        return mProcessedChart;
    }

    private void calcAspects() {
        AspectedSymbolsImpl.AspectType aspectType = (mProcessedChart.getAstralSymbolID().equals(AstralCharts.CURRENT)) ?
                AspectedSymbolsImpl.AspectType.TRANSIT : AspectedSymbolsImpl.AspectType.ASPECT;

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
        mProcessedChart.addAstralMappings(aspectList);
    }

    private IAspectSymbol checkForAspects(IAstralSymbol body, Collection<IAstralSymbol> comparelist) {
        double orb = mAppSettings.astro.max_orb;
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
