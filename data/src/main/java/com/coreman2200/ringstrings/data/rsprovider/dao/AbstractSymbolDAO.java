package com.coreman2200.ringstrings.data.rsprovider.dao;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import androidx.annotation.NonNull;

import com.coreman2200.ringstrings.data.protos.SymbolIDBundle;
import com.coreman2200.ringstrings.data.rsprovider.RingStringsContract;
import com.coreman2200.ringstrings.data.rsprovider.RingStringsDbHelper;
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.Lights.ILightSymbol;
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.grouped.TagSymbols;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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

public class AbstractSymbolDAO implements Serializable, ISymbolDAO {

    final protected ILightSymbol mSymbol;

    protected AbstractSymbolDAO(@NonNull ILightSymbol symbol) {
        mSymbol = symbol;
    }

    final public ILightSymbol getSymbol() {
        return mSymbol;
    }

    /**
     * Convenient method to get the objects data members in ContentValues object.
     * This will be useful for Content Provider operations,
     * which use ContentValues object to represent the data.

     */
    public ContentValues getContentValues() {
        SymbolIDBundle idbundle = mSymbol.getIdBundle();
        ContentValues values = new ContentValues();
        values.put(RingStringsDbHelper.COL_PROFILE, idbundle.profile_id);
        values.put(RingStringsDbHelper.COL_CHART, idbundle.chart_id);
        values.put(RingStringsDbHelper.COL_STRATA, idbundle.strata_id);
        values.put(RingStringsDbHelper.COL_TYPE, idbundle.type_id);
        values.put(RingStringsDbHelper.COL_SYMBOL, idbundle.symbol_id);
        values.put(RingStringsDbHelper.COL_SYMBOLDEF, idbundle.desc_id);
        values.put(RingStringsDbHelper.COL_SIZE, mSymbol.size());
        return values;
    }

    public final ArrayList<ContentProviderOperation> batchCreate() {

        return produceBatchCreateOperations(true);
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

    protected static SymbolIDBundle produceIdBundleWithMap(Map<String, Integer> map, RingStringsAppSettings settings) {
        return new SymbolIDBundle.Builder()
                .profile_id(map.get(RingStringsContract.Symbols._PROFILEID)) // ID for Parent Profile..
                .chart_id(map.get(RingStringsContract.Symbols._CHARTID))     // Chart Type ID..
                .strata_id(map.get(RingStringsContract.Symbols._STRATAID))   // General Symbol Strata ID..
                .type_id(map.get(RingStringsContract.Symbols._TYPEID))       // Specified Symbol<Type> Strata ID..
                .symbol_id(map.get(RingStringsContract.Symbols._SYMBOLID))   // Symbols own ID..
                .desc_id(map.get(RingStringsContract.Symbols._DESC_ID))      // Symbol Description ID..
                .settings(settings)                                          // AppSettings Bundle
                .build();
    }


    protected ArrayList<ContentProviderOperation> produceBatchCreateOperations(final boolean yieldAllowed) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        ops.add(produceContent(yieldAllowed));
        ops.addAll(getSymbolQualityOperations());

        return ops;
    }

    protected List<ContentProviderOperation> getSymbolQualityOperations() {
        Collection<TagSymbols> qualities = mSymbol.getQualities();
        List<ContentProviderOperation> ops = new ArrayList<>();

        SymbolIDBundle bundle = mSymbol.getIdBundle();
        for (TagSymbols qual : qualities) {

            QualityDAO dao = new QualityDAO(bundle, qual, mSymbol.getTagCount(qual));
            ops.add(dao.produceSymbolQualityTableContent());
        }

        return ops;
    }

}
