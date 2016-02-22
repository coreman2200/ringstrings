package com.coreman2200.ringstrings.rsprovider.dao;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.coreman2200.ringstrings.protos.SymbolIDBundle;
import com.coreman2200.ringstrings.rsprovider.RingStringsContract;
import com.coreman2200.ringstrings.rsprovider.RingStringsDbHelper;
import com.coreman2200.ringstrings.symbol.symbolinterface.ISymbol;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AbstractSymbolDAO
 * Base dao for symbol objects (subclass per symbol type)
 *
 * Created by Cory Higginbottom on 2/20/16
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class AbstractSymbolDAO<T extends ISymbol> implements Serializable, ISymbolDAO<T> {
    // _ID, _PROFILEID, _CHARTID, _STRATAID, _TYPEID, _SYMBOLID, _DESC_ID, _SIZE

    final protected T mSymbol;
    protected SymbolIDBundle mIdBundle;

    protected AbstractSymbolDAO(@NonNull T symbol, @NonNull SymbolIDBundle idbundle) {
        mSymbol = symbol;
        mIdBundle = idbundle;
    }

    final public T getSymbol() {
        return mSymbol;
    }

    /**
     * Convenient method to get the objects data members in ContentValues object.
     * This will be useful for Content Provider operations,
     * which use ContentValues object to represent the data.

     */
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(RingStringsDbHelper.COL_PROFILE, mIdBundle.profile_id);
        values.put(RingStringsDbHelper.COL_CHART, mIdBundle.chart_id);
        values.put(RingStringsDbHelper.COL_STRATA, mIdBundle.strata_id);
        values.put(RingStringsDbHelper.COL_TYPE, mIdBundle.type_id);
        values.put(RingStringsDbHelper.COL_SYMBOL, mIdBundle.symbol_id);
        values.put(RingStringsDbHelper.COL_DESC, mIdBundle.desc_id);
        values.put(RingStringsDbHelper.COL_SIZE, mSymbol.size());
        return values;
    }

    public void add() throws Exception {

    }

    public void delete() throws Exception {

    }

    protected ContentProviderOperation produceContent(final boolean yieldAllowed) {
        return ContentProviderOperation.newInsert(RingStringsContract.Symbols.CONTENT_URI)
                .withValues(getContentValues())
                .withYieldAllowed(yieldAllowed)
                .build();
    }

    protected final List<ContentProviderOperation> produceBatchCreateOperations(final boolean yieldAllowed) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        ops.add(produceContent(yieldAllowed));

        return ops;
    }

}
