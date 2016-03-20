package com.coreman2200.domain.symbol.astralsymbol.grouped;

/**
 * Aspects
 * Astrological Aspects
 *
 * Created by Cory Higginbottom on 5/29/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public enum Aspects {
    CONJUNCTION(0),
    SEMISEXTILE(30),
    SEXTILE(60),
    SQUARE(90),
    TRINE(120),
    QUINCUNX(150),
    OPPOSITION(180);

    final double mAspectDegree;

    Aspects(int degree) {
        mAspectDegree = degree;
    }

    public boolean checkValueWithinOrbOfAspect(double degree, double orb) {

        if (mAspectDegree == 0 &&  (360.0 - degree) < orb) {
            return true;
        }

        double diff = Math.abs(degree - mAspectDegree);

        boolean ret = diff <= orb;

        return ret;
    }

    public double aspectDegree() {
        return mAspectDegree;
    }


}
