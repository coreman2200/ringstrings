package com.coreman2200.data.repository.dao;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.database.Cursor;

import com.coreman2200.data.adapter.symbolid.SymbolIdBundleAdapter;
import com.coreman2200.domain.protos.SymbolIDBundle;
import com.coreman2200.data.repository.RingStringsContract;
import com.coreman2200.domain.symbol.astralsymbol.AstralStrata;
import com.coreman2200.domain.symbol.entitysymbol.Lights.ILightSymbol;
import com.coreman2200.presentation.symbol.profile.IProfileSymbol;
import com.coreman2200.presentation.symbol.tags.TagSymbols;
import com.coreman2200.domain.symbol.symbolinterface.ISymbol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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


    public static ProfileSymbolDAO fromCursor(Cursor cursor) throws IOException {

        final SymbolIDBundle bundle = SymbolIdBundleAdapter.fromCursor(cursor).getIdBundle();

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
