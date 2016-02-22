package com.coreman2200.ringstrings.rsprovider.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.coreman2200.ringstrings.protos.RingStringsAppSettings;
import com.coreman2200.ringstrings.protos.SymbolIDBundle;
import com.coreman2200.ringstrings.rsprovider.RingStringsContract;
import com.coreman2200.ringstrings.rsprovider.RingStringsDbHelper;
import com.coreman2200.ringstrings.symbol.SymbolStrata;
import com.coreman2200.ringstrings.symbol.chart.Charts;
import com.coreman2200.ringstrings.symbol.chart.NumerologicalChartImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.entity.symboldef.SymbolDefFileHandlerImpl;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.NumberSymbolInputProcessorImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.NumberStrata;
import com.coreman2200.ringstrings.symbol.numbersymbol.grouped.GroupedNumberSymbols;
import com.coreman2200.ringstrings.symbol.numbersymbol.impl.GroupedNumberSymbolsImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.IDerivedNumberSymbol;
import com.coreman2200.ringstrings.symbol.numbersymbol.interfaces.INumberSymbol;

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

public class NumberSymbolDAO extends AbstractSymbolDAO<INumberSymbol> {

    private NumberSymbolDAO(INumberSymbol symbol, SymbolIDBundle idbundle) {
        super(symbol, idbundle);

    }

    public static NumberSymbolDAO fromNumberSymbolWithIds(INumberSymbol symbol, SymbolIDBundle idbundle) {
        return new NumberSymbolDAO(symbol, idbundle);
    }

    public static NumberSymbolDAO fromCursor(RingStringsAppSettings settings, Cursor cursor) throws IOException {
        final String[] numberProjection = RingStringsContract.Symbols.PROJECTION_NUMSYMBOL;
        Map<String, Integer> projMap = new HashMap<>(numberProjection.length);
        for (String elem : numberProjection)
            projMap.put(elem, cursor.getInt(cursor.getColumnIndex(elem)));

        final SymbolIDBundle bundle = produceIdBundleWithMap(projMap, settings);
        final Integer value = projMap.get(RingStringsContract.Symbols._VALUE);
        final INumberSymbol symbol = produceNumberSymbol(value, bundle);

        return new NumberSymbolDAO(symbol, bundle);
    }

    private static SymbolIDBundle produceIdBundleWithMap(Map<String, Integer> map, RingStringsAppSettings settings) {
        return new SymbolIDBundle.Builder()
                .profile_id(map.get(RingStringsContract.Symbols._PROFILEID))
                .chart_id(map.get(RingStringsContract.Symbols._CHARTID))
                .strata_id(map.get(RingStringsContract.Symbols._STRATAID))
                .type_id(map.get(RingStringsContract.Symbols._TYPEID))
                .symbol_id(map.get(RingStringsContract.Symbols._SYMBOLID))
                .desc_id(map.get(RingStringsContract.Symbols._DESC_ID))
                .settings(settings)
                .build();
    }

    private static INumberSymbol produceNumberSymbol(Integer value, SymbolIDBundle bundle) {
        NumberStrata numstrata = NumberStrata.values()[bundle.strata_id];

        switch (numstrata) {
            case GROUPEDNUMBERS:
                GroupedNumberSymbols grouped = GroupedNumberSymbols.values()[bundle.type_id];
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
        if (mSymbol.getNumberSymbolStrata().equals(NumberStrata.DERIVEDNUMBER))
            value =  ((IDerivedNumberSymbol)mSymbol).getDerivedSymbolsValue();
        else
            value = mSymbol.getNumberSymbolValue();

        ContentValues contentvalues = super.getContentValues();
        contentvalues.put(RingStringsDbHelper.COL_VALUE, value);
        return contentvalues;
    }
}
