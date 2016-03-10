package com.coreman2200.ringstrings.rsprovider.dao;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.database.Cursor;

import com.coreman2200.ringstrings.protos.RingStringsAppSettings;
import com.coreman2200.ringstrings.protos.SymbolIDBundle;
import com.coreman2200.ringstrings.rsprovider.RingStringsContract;
import com.coreman2200.ringstrings.symbol.astralsymbol.AstralStrata;
import com.coreman2200.ringstrings.symbol.entitysymbol.Lights.ILightSymbol;
import com.coreman2200.ringstrings.symbol.entitysymbol.Profile.IProfileSymbol;
import com.coreman2200.ringstrings.symbol.entitysymbol.Tags.TagSymbols;
import com.coreman2200.ringstrings.symbol.symbolinterface.ISymbol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProfileSymbolDAO
 * DAO for profile symbol
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

public class ProfileSymbolDAO extends AbstractSymbolDAO {
    private Map<RingStringsContract.Tables, ContentValues> mTableDataMap;
    private IProfileSymbol mProfile;
    private Map<TagSymbols, Integer> mQualityCountMap;

    public ProfileSymbolDAO(IProfileSymbol profile) {
        super(profile);
        mProfile = profile;
    }

    private List<ContentProviderOperation> getSymbolOperations() {
        List<ContentProviderOperation> ops = new ArrayList<>();
        Collection<ISymbol> symbols = mProfile.produceSymbol().values();
        for (ISymbol symbol : symbols) {
            ISymbolDAO dao = produceDAOForSymbol(symbol);
            ops.addAll(dao.batchCreate());
        }

        return ops;
    }

    private ISymbolDAO produceDAOForSymbol(ISymbol symbol) {
        ILightSymbol ls = mProfile.produceLightSymbolFor(symbol);
        return (AstralStrata.isAstralSymbol(symbol.symbolType())) ? AstralSymbolDAO.fromLightSymbol(ls) : NumberSymbolDAO.fromLightSymbol(ls);

    }

    protected List<ContentProviderOperation> getQualityTableOperations() {
        Collection<TagSymbols> qualities = mProfile.getQualities();
        List<ContentProviderOperation> ops = new ArrayList<>();

        SymbolIDBundle bundle = mProfile.getIdBundle();
        for (TagSymbols qual : qualities) {

            QualityDAO dao = new QualityDAO(bundle, qual, mSymbol.getTagCount(qual));
            ops.add(dao.produceQualityTableContent());
        }

        return ops;
    }


    public static ProfileSymbolDAO fromCursor(RingStringsAppSettings settings, Cursor cursor) throws IOException {

        List<String> projection = new ArrayList<>();
        projection.addAll(Arrays.asList(RingStringsContract.Symbols.PROJECTION_ALL));
        projection.remove(RingStringsContract.Symbols.SYMBOL_QUALITIES);
        Map<String, Integer> projMap = new HashMap<>(projection.size());
        for (String elem : projection) {
            projMap.put(elem, cursor.getInt(cursor.getColumnIndex(elem)));
        }
        final SymbolIDBundle bundle = produceIdBundleWithMap(projMap, settings);

        return null;
    }

    @Override
    protected ArrayList<ContentProviderOperation> produceBatchCreateOperations(final boolean yieldAllowed) {
        ArrayList<ContentProviderOperation> ops = super.produceBatchCreateOperations(yieldAllowed);
        ops.addAll(getSymbolOperations());
        ops.addAll(getQualityTableOperations());
        ops.addAll(getSymbolQualityOperations());

        return ops;
    }

}
