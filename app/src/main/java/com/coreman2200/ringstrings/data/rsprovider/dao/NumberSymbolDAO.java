package com.coreman2200.ringstrings.data.rsprovider.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.coreman2200.ringstrings.data.protos.SymbolIDBundle;
import com.coreman2200.ringstrings.data.rsprovider.RingStringsContract;
import com.coreman2200.ringstrings.data.rsprovider.RingStringsDbHelper;
import com.coreman2200.ringstrings.domain.symbol.chart.NumerologicalChartImpl;
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.Lights.ILightSymbol;
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.Lights.LightSymbolImpl;
import com.coreman2200.ringstrings.domain.input.numerology.NumberSymbolInputProcessorImpl;
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.NumberStrata;
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.grouped.GroupedNumbers;
import com.coreman2200.ringstrings.domain.symbol.numbersymbol.interfaces.INumberSymbol;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * NumberSymbolDAO
 * DAO for number symbols (extends essentially to recognize "_value" attrs.
 *
 * Created by Cory Higginbottom on 2/21/16
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class NumberSymbolDAO extends AbstractSymbolDAO {

    private NumberSymbolDAO(ILightSymbol symbol) {
        super(symbol);

    }

    public static NumberSymbolDAO fromLightSymbol(ILightSymbol symbol) {
        return new NumberSymbolDAO(symbol);
    }

    public static NumberSymbolDAO fromCursor(RingStringsAppSettings settings, Cursor cursor) throws IOException {
        final String[] numberProjection = RingStringsContract.Symbols.PROJECTION_NUMSYMBOL;
        Map<String, Integer> projMap = new HashMap<>(numberProjection.length);
        for (String elem : numberProjection)
            projMap.put(elem, cursor.getInt(cursor.getColumnIndex(elem)));

        final SymbolIDBundle bundle = produceIdBundleWithMap(projMap, settings);
        final Integer value = projMap.get(RingStringsContract.Symbols._VALUE);
        final INumberSymbol symbol = produceNumberSymbol(value, bundle);
        final ILightSymbol ls = new LightSymbolImpl(bundle, symbol);

        return new NumberSymbolDAO(ls);
    }

    private static INumberSymbol produceNumberSymbol(Integer value, SymbolIDBundle bundle) {
        NumberStrata numstrata = NumberStrata.values()[bundle.type_id];

        switch (numstrata) {
            case GROUPEDNUMBERS:
                GroupedNumbers grouped = GroupedNumbers.values()[bundle.symbol_id];
                return new GroupedNumberSymbolsImpl(grouped);
            case CHARTEDNUMBERS:
                return new NumerologicalChartImpl();
            default:
                return getNumberSymbolFromValue(bundle.settings, value);
        }
    }

    private static INumberSymbol getNumberSymbolFromValue(RingStringsAppSettings settings, Integer value) {
        NumberSymbolInputProcessorImpl ip = new NumberSymbolInputProcessorImpl(settings);
        return ip.convertValueToNumberSymbol(value);
    }

    @Override
    public ContentValues getContentValues() {
        int value;
        INumberSymbol ns = (INumberSymbol)(mSymbol.getSymbol());
        if (ns.getNumberSymbolStrata().equals(NumberStrata.DERIVEDNUMBER))
            value = ((IDerivedNumberSymbol)ns).getDerivedSymbolsValue();
        else
            value = ns.value();

        ContentValues contentvalues = super.getContentValues();
        contentvalues.put(RingStringsDbHelper.COL_VALUE, value);

        return contentvalues;
    }
}
