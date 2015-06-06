package com.coreman2200.ringstrings.symbol;

/**
 * AbstractChart
 * Abstraction for charts. So far there are the astro- and numero-ones, but I anticipate derivative
 * charts.
 *
 * Created by Cory Higginbottom on 5/28/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

abstract public class AbstractChart implements IChartedSymbols {
    private final Charts mChartType;
    public AbstractChart(Charts charttype) {
        super();
        mChartType = charttype;

    }
}
