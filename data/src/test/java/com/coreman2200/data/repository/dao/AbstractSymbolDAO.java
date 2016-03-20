package com.coreman2200.data.repository.dao;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.coreman2200.data.entity.protos.SymbolIDBundle;
import com.coreman2200.data.repository.RingStringsContract;
import com.coreman2200.data.repository.RingStringsDbHelper;
import com.coreman2200.domain.symbol.entitysymbol.Lights.ILightSymbol;
import com.coreman2200.domain.symbol.entitysymbol.Tags.TagSymbols;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        // TODO: ContentValuesIdBundleAdapter..
        SymbolIDBundle idbundle = mSymbol.getIdBundle();
        ContentValues values = new ContentValues();
        values.put(RingStringsDbHelper.COL_PROFILE, idbundle.profile_id);
        values.put(RingStringsDbHelper.COL_CHART, idbundle.chart_id);
        values.put(RingStringsDbHelper.COL_STRATA, idbundle.strata_id);
        values.put(RingStringsDbHelper.COL_TYPE, idbundle.type_id);
        values.put(RingStringsDbHelper.COL_SYMBOL, idbundle.symbol_id);
        values.put(RingStringsDbHelper.COL_SYMBOLDEF, idbundle.desc_id);
        values.put(RingStringsDbHelper.COL_NAME, idbundle.name);

        // TODO:
        //values.put(RingStringsDbHelper.COL_SYMBOLCHILDREN, SymbolIdBundleAdapter.produceListString(idbundle.symbol_children));
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
