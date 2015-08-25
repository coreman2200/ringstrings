package com.coreman2200.ringstrings.symbol.symbolinterface;

import com.coreman2200.ringstrings.symbol.chart.Charts;

/**
 * IChartedSymbols
 * Interface for all Charted Symbol objects
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

public interface IChartedSymbols extends IGroupedSymbols {
    void testGenerateLoggingsForFullChart();
    Charts getChartType();
    // TODO: save/load chart, rebuild chart
}
