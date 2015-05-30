package com.coreman2200.ringstrings.symbol.chart;

import com.coreman2200.ringstrings.symbol.numbersymbol.GroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.NumberStrata;
import com.coreman2200.ringstrings.symbol.numbersymbol.impl.GroupedNumberSymbolsImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.IChartedNumberSymbols;

/**
 * NumerologicalChartImpl
 * Describes entire numerological chart as groups of numerological symbols.
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

public class NumerologicalChartImpl extends GroupedNumberSymbolsImpl implements IChartedNumberSymbols {
    public NumerologicalChartImpl() {
        super(GroupedNumberSymbols.CHART);
    }

    @Override
    protected void setNumberStrata() {
        this.numberSymbolStrata = NumberStrata.CHARTEDNUMBERS;
    }
}
