package com.coreman2200.data.repository.dao;

import android.content.ContentProviderOperation;
import android.content.ContentValues;

import com.coreman2200.domain.protos.SymbolIDBundle;
import com.coreman2200.data.repository.RingStringsContract;
import com.coreman2200.data.repository.RingStringsDbHelper;
import com.coreman2200.domain.symbol.symbolinterface.ILightSymbol;
import com.coreman2200.domain.symbol.symbolinterface.ITagSymbol;
import com.coreman2200.domain.symbol.tags.TagSymbols;

import java.util.ArrayList;
import java.util.List;

/**
 * QualityDAO
 * DAO for interacting with Quality table
 *
 * Created by Cory Higginbottom on 3/9/16
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class QualityDAO implements ISymbolDAO {
    private SymbolIDBundle mIdBundle;
    private ITagSymbol mTag;
    private int mCount;

    public QualityDAO(SymbolIDBundle bundle, ITagSymbol tag, int count) {
        mIdBundle = bundle;
        mTag = tag;
        mCount = count;
    }

    @Override
    public void add() throws Exception {

    }

    @Override
    public void delete() throws Exception {

    }

    @Override
    public ILightSymbol getSymbol() {
        return null;
    }

    @Override
    public List<ContentProviderOperation> batchCreate() {
        return produceBatchCreateOperations();
    }

    protected List<ContentProviderOperation> produceBatchCreateOperations() {
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        ops.add(produceQualityTableContent());
        ops.add(produceSymbolQualityTableContent());
        if (mIdBundle.desc_id != null)
            ops.add(produceDefQualityTableContent());

        return ops;
    }

    protected ContentProviderOperation produceQualityTableContent() {
        return ContentProviderOperation.newInsert(RingStringsContract.Quality.CONTENT_URI)
                .withValues(getContentValues())
                .withYieldAllowed(false)
                .build();
    }

    protected ContentProviderOperation produceSymbolQualityTableContent() {
        return ContentProviderOperation.newInsert(RingStringsContract.SymbolQualities.CONTENT_URI)
                .withValues(getSymbolQualityValues())
                .withYieldAllowed(false)
                .build();
    }

    protected ContentProviderOperation produceDefQualityTableContent() {
        return ContentProviderOperation.newInsert(RingStringsContract.SymbolDefQualities.CONTENT_URI)
                .withValues(getDefQualityContentValues())
                .withYieldAllowed(false)
                .build();
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(RingStringsDbHelper.COL_NAME, mTag.name());
        values.put(RingStringsDbHelper.COL_QUALITY, mTag.ordinal());
        values.put(RingStringsDbHelper.COL_PROFILE, mIdBundle.profile_id);
        values.put(RingStringsDbHelper.COL_COUNT, mCount);
        return values;
    }

    public ContentValues getSymbolQualityValues() {
        ContentValues values = getContentValues();
        values.remove(RingStringsDbHelper.COL_NAME);
        values.put(RingStringsDbHelper.COL_SYMBOL, mIdBundle.symbol_id);
        return values;
    }

    public ContentValues getDefQualityContentValues() {
        ContentValues values = new ContentValues();
        values.put(RingStringsDbHelper.COL_NAME, mTag.name());
        values.put(RingStringsDbHelper.COL_QUALITY, mTag.ordinal());
        values.put(RingStringsDbHelper.COL_PROFILE, mIdBundle.profile_id);
        values.put(RingStringsDbHelper.COL_SYMBOLDEF, mIdBundle.desc_id);
        return values;
    }


}
