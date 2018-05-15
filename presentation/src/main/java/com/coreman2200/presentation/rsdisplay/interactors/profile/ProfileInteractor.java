package com.coreman2200.presentation.rsdisplay.interactors.profile;

import com.coreman2200.presentation.rsdisplay.interactors.numerology.NumerologicalChartInteractor;
import com.coreman2200.domain.model.profiles.interfaces.IProfileDataBundle;
import com.coreman2200.domain.model.protos.RingStringsAppSettings;
import com.coreman2200.domain.model.symbols.charts.Charts;
import com.coreman2200.domain.model.symbols.strata.EntityStrata;
import com.coreman2200.domain.model.symbols.interfaces.IProfileSymbol;
import com.coreman2200.domain.model.symbols.entities.profile.LocalProfileSymbolImpl;
import com.coreman2200.domain.model.symbols.entities.profile.UserProfileSymbolImpl;
import com.coreman2200.presentation.rsdisplay.interactors.AbstractInteractor;
import com.coreman2200.presentation.rsdisplay.interactors.astrology.AstrologicalChartInteractorImpl;
import com.coreman2200.domain.model.symbols.interfaces.IChartedSymbols;

import java.util.Collection;
import java.util.LinkedList;

/**
 * ProfileInteractor
 * Produces relational symbol map for profile.
 *
 * Created by Cory Higginbottom on 6/10/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class ProfileInteractor extends AbstractInteractor {

    private IProfileDataBundle mProfileData;

    public ProfileInteractor(RingStringsAppSettings settings) {
        super(settings);
    }

    public IProfileSymbol produceProfile(IProfileDataBundle profile, EntityStrata type) {
        mProfileData = profile;

        switch (type) {
            case USER:
                return new UserProfileSymbolImpl(mProfileData, produceProfileCharts());
            default:
                return new LocalProfileSymbolImpl(mProfileData, produceProfileCharts());
        }
    }

    private Collection<IChartedSymbols> produceProfileCharts() {
        Collection<IChartedSymbols> mCharts = new LinkedList<>();
        AstrologicalChartInteractorImpl astroprocessor = new AstrologicalChartInteractorImpl(mAppSettings);
        mCharts.add(astroprocessor.produceAstrologicalChart(mProfileData, Charts.ASTRAL_NATAL));
        mCharts.add(astroprocessor.produceAstrologicalChart(mProfileData, Charts.ASTRAL_CURRENT));

        NumerologicalChartInteractor numberprocessor = new NumerologicalChartInteractor(mAppSettings);
        mCharts.add(numberprocessor.produceGroupedNumberSymbolsForProfile(mProfileData));

        return mCharts;
    }

}
