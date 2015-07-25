package com.coreman2200.ringstrings.symbol.profilemap;

import com.coreman2200.ringstrings.profile.IProfileTestLoc;
import com.coreman2200.ringstrings.symbol.AbstractRelationalSymbolMap;
import com.coreman2200.ringstrings.symbol.IChartedSymbols;
import com.coreman2200.ringstrings.symbol.RelatedSymbolMap;
import com.coreman2200.ringstrings.symbol.relationalmaps.Entities;

/**
 * AbstractProfileSymbolMap
 * Profile Symbol Map is a relational map for all symbols in one's available charts
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

public class AbstractProfileSymbolMap extends AbstractRelationalSymbolMap {
    private final Entities mEntityType;
    private RelatedSymbolMap<IChartedSymbols> mChartedSymbolMaps;
    protected IProfileTestLoc mProfile;

    protected AbstractProfileSymbolMap(Entities type, IProfileTestLoc profile) {
        super();
        mChartedSymbolMaps = new RelatedSymbolMap<>();
        mProfile = profile;
        mEntityType = type;
    }

    public void testGenerateLog() {
        System.out.println(name());
    }

    @Override
    public String name() { // TODO: testy
        return mProfile.getFirstName() + " " + mProfile.getMiddleName() + " " + mProfile.getLastName();
    }

    @Override
    public int size() {
        int size = 0;
        for (IChartedSymbols chart : mChartedSymbolMaps.getSortedSymbols(RelatedSymbolMap.SortOrder.ASCENDING)) {
            size = chart.size();
        }
        return size;
    }
}
