package com.coreman2200.domain.model.symbols.maps.comparator;

import com.coreman2200.domain.model.symbols.astrals.grouped.Aspects;
import com.coreman2200.domain.model.symbols.astrals.interfaces.IAstralSymbol;

import java.util.Map;

/**
 * AspectComparatorImpl
 * Comparator produces aspects/transits between astral symbols. (compares degree
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

public class AspectComparatorImpl extends SymbolComparatorImpl<IAstralSymbol> {
    private double mMaxOrb;

    public AspectComparatorImpl(double maxorb) {
        mMaxOrb = maxorb;
    }

    @Override
    public int compare(Map.Entry<Enum<? extends Enum<?>>, IAstralSymbol> o1, Map.Entry<Enum<? extends Enum<?>>, IAstralSymbol> o2) {
        double degreeDiff = o2.getValue().getAstralSymbolDegree() - o1.getValue().getAstralSymbolDegree();
        return (checkForAspects(degreeDiff)) ? 1 : -1;
    }

    private boolean checkForAspects(double degree) {
        for (Aspects aspect : Aspects.values()) {
            if (aspect.checkValueWithinOrbOfAspect(degree, mMaxOrb))
                return true;
        }

        return false;
    }
}
