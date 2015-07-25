package com.coreman2200.ringstrings.symbol;

import java.util.LinkedList;

/**
 * AbstractRelationalSymbolMap
 * Abstract for relational maps of chart symbols
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

abstract public class AbstractRelationalSymbolMap extends AbstractSymbol implements IRelationalSymbolMap {
    private RelatedSymbolMap<IChartedSymbols> mChartedSymbolMaps;

    protected AbstractRelationalSymbolMap() {
        super();
        mChartedSymbolMaps = new RelatedSymbolMap<>();
    }

    public void addChart(IChartedSymbols chart) {
        mChartedSymbolMaps.put(chart.getChartType(), chart);
    }

    public void addCharts(LinkedList<IChartedSymbols> charts) {
        for (IChartedSymbols chart : charts)
            this.addChart(chart);
    }

    @Override
    public int size() {
        int size = 0;
        for (IChartedSymbols chart : mChartedSymbolMaps.getSortedSymbols(RelatedSymbolMap.SortOrder.ASCENDING)) {
            size += chart.size();
        }
        return size;
    }
}
