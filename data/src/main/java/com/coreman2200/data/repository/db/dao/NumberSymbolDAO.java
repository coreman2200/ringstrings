package com.coreman2200.data.repository.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.coreman2200.data.adapter.symbolid.SymbolIdBundleAdapter;
import com.coreman2200.presentation.rsdisplay.interactors.numerology.NumberSymbolInteractorImpl;
import com.coreman2200.domain.model.protos.RingStringsAppSettings;
import com.coreman2200.domain.model.protos.SymbolIDBundle;
import com.coreman2200.data.repository.db.RingStringsContract;
import com.coreman2200.data.repository.db.RingStringsDbHelper;
import com.coreman2200.domain.model.symbols.charts.NumerologicalChartImpl;
import com.coreman2200.domain.model.symbols.interfaces.ILightSymbol;
import com.coreman2200.domain.model.symbols.entities.lights.LightSymbolImpl;
import com.coreman2200.domain.model.symbols.strata.NumberStrata;
import com.coreman2200.domain.model.symbols.numbers.grouped.GroupedNumberSymbols;
import com.coreman2200.domain.model.symbols.numbers.impl.GroupedNumberSymbolsImpl;
import com.coreman2200.domain.model.symbols.numbers.interfaces.IDerivedNumberSymbol;
import com.coreman2200.domain.model.symbols.numbers.interfaces.INumberSymbol;

import java.io.IOException;

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

    public static NumberSymbolDAO fromCursor(Cursor cursor) throws IOException {
        final SymbolIDBundle bundle = SymbolIdBundleAdapter.fromCursor(cursor).getIdBundle();
        final Integer value = cursor.getInt(cursor.getColumnIndex(RingStringsContract.Symbols._VALUE));
        final INumberSymbol symbol = produceNumberSymbol(value, bundle);
        final ILightSymbol ls = new LightSymbolImpl(bundle, symbol);

        return new NumberSymbolDAO(ls);
    }

    private static INumberSymbol produceNumberSymbol(Integer value, SymbolIDBundle bundle) {
        NumberStrata numstrata = NumberStrata.values()[bundle.type_id];

        switch (numstrata) {
            case GROUPEDNUMBERS:
                GroupedNumberSymbols grouped = GroupedNumberSymbols.values()[bundle.symbol_id];
                return new GroupedNumberSymbolsImpl(grouped);
            case CHARTEDNUMBERS:
                return new NumerologicalChartImpl();
            default:
                return getNumberSymbolFromValue(bundle.settings, value);
        }
    }

    private static INumberSymbol getNumberSymbolFromValue(RingStringsAppSettings settings, Integer value) {
        NumberSymbolInteractorImpl ip = new NumberSymbolInteractorImpl(settings);
        return ip.convertValueToNumberSymbol(value);
    }

    @Override
    public ContentValues getContentValues() {
        int value;
        INumberSymbol ns = (INumberSymbol)(mSymbol.getSymbol());
        if (ns.getNumberSymbolStrata().equals(NumberStrata.DERIVEDNUMBER))
            value = ((IDerivedNumberSymbol)ns).getDerivedSymbolsValue();
        else
            value = ns.getNumberSymbolValue();

        ContentValues contentvalues = super.getContentValues();
        contentvalues.put(RingStringsDbHelper.COL_VALUE, value);

        return contentvalues;
    }
}
