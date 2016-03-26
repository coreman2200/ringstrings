package com.coreman2200.data.rsprovider;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;

import com.coreman2200.data.BuildConfig;
import com.coreman2200.data.activity.MainActivity;
import com.coreman2200.data.repository.RingStringsContract;
import com.coreman2200.data.adapter.symbolid.IQueryIdBundle;
import com.coreman2200.data.adapter.symbolid.QueryIdBundleAdapter;
import com.coreman2200.data.adapter.symbolid.SymbolIdBundleAdapter;
import com.coreman2200.domain.adapter.profiledata.IProfileDataBundle;
import com.coreman2200.domain.adapter.profiledata.ProfileDataBundleAdapter;
import com.coreman2200.domain.profiledata.MockDefaultDataBundles;
import com.coreman2200.domain.protos.LocalProfileDataBundle;
import com.coreman2200.domain.protos.RingStringsAppSettings;
import com.coreman2200.domain.protos.SymbolIDBundle;
import com.coreman2200.data.repository.dao.AstralSymbolDAO;
import com.coreman2200.data.repository.dao.ISymbolDAO;
import com.coreman2200.data.repository.dao.NumberSymbolDAO;
import com.coreman2200.data.repository.dao.ProfileSymbolDAO;
import com.coreman2200.domain.symbol.strata.AstralStrata;
import com.coreman2200.domain.symbol.chart.Charts;
import com.coreman2200.domain.symbol.strata.EntityStrata;
import com.coreman2200.domain.symbol.symbolinterface.ILightSymbol;
import com.coreman2200.domain.symbol.symbolinterface.IProfileSymbol;
import com.coreman2200.domain.symbol.tags.TagSymbols;
import com.coreman2200.data.processor.profile.ProfileInputProcessor;
import com.coreman2200.data.rsio.symboldef.SymbolDefFileHandlerImpl;
import com.coreman2200.domain.symbol.strata.NumberStrata;
import com.coreman2200.domain.symbol.symbolinterface.ISymbol;

import org.assertj.core.util.Strings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TestRingStringsContentProvider
 * Tests for Symbol Content Provider
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

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TestRingStringsContentProvider {
    private Activity mTestActivity;
    private RingStringsContentProvider mProvider;
    private ContentResolver mContentResolver;
    private ShadowContentResolver mShadowContentResolver;
    private ProfileInputProcessor mProfileProcessor;
    private int mTestSize = 0;

    @Before
    public void setup() {
        mTestActivity = Robolectric.setupActivity(MainActivity.class);
        mProvider = new RingStringsContentProvider();
        mContentResolver = mTestActivity.getContentResolver();
        mShadowContentResolver = Shadows.shadowOf(mContentResolver);
        SymbolDefFileHandlerImpl.createInstance(mTestActivity);

        produceTestSubject(MockDefaultDataBundles.testProfileBundleCoryH);
        mProvider.onCreate();
        ShadowContentResolver.registerProvider(RingStringsContract.AUTHORITY, mProvider);
    }

    private IProfileSymbol produceTestSubject(LocalProfileDataBundle profile) {
        if (profile == null)
            profile = MockDefaultDataBundles.generateRandomProfile();
        IProfileDataBundle bundle = new ProfileDataBundleAdapter(profile);
        RingStringsAppSettings settings = MockDefaultDataBundles.produceDefaultAppSettingsBundle();
        mProfileProcessor = new ProfileInputProcessor(settings);
        return mProfileProcessor.produceProfile(bundle, EntityStrata.PROFILE);
    }

    //@Test
    public void testInsertForSymbol() {
        mTestSize = 1;
        IProfileSymbol profile = produceTestSubject(null);
        ILightSymbol symbol = produceLightSymbol(profile, Charts.ASTRAL_NATAL);
        AstralSymbolDAO dao = AstralSymbolDAO.fromLightSymbol(symbol);
        mShadowContentResolver.insert(RingStringsContract.Symbols.CONTENT_URI, dao.getContentValues());

        assert (getLocalElemCountForUri(RingStringsContract.Symbols.CONTENT_URI) == mTestSize);
    }

    private ILightSymbol produceLightSymbol(IProfileSymbol profile, Enum<? extends Enum<?>> id) {
        return profile.produceLightSymbolFor(getSymbol(profile, id));
    }

    public ISymbol getSymbol(IProfileSymbol profile, Enum<? extends Enum<?>> preferred) {

        Map<Enum<? extends Enum<?>>, ISymbol> symbolmap = profile.produceSymbol();
        System.out.println("Symbol Map size: " + symbolmap.size());

        if (preferred != null) {
            ISymbol symbol = symbolmap.get(preferred);
            if (symbol != null)
                return symbol;
            else
                assert false;
        }

        int index = (int)(Math.random()*symbolmap.size());
        return symbolmap.get(symbolmap.keySet().toArray()[index]);
    }

    //@Test
    public void testIncludeFullProfile() {
        ProfileSymbolDAO dao = new ProfileSymbolDAO(produceTestSubject(MockDefaultDataBundles.testProfileBundleCoryH));
        ArrayList<ContentProviderOperation> ops = dao.batchCreate();


        System.out.println("Total operations: " + ops.size());

        try {
            mShadowContentResolver.applyBatch(RingStringsContract.AUTHORITY, ops);
        } catch (OperationApplicationException e) {
            System.out.println(e.getLocalizedMessage());
            assert (false);
        }

        int count = 0;
        count += getLocalElemCountForUri(RingStringsContract.Symbols.CONTENT_URI);
        count += getLocalElemCountForUri(RingStringsContract.Quality.CONTENT_URI);
        count += getLocalElemCountForUri(RingStringsContract.SymbolDescription.CONTENT_URI);
        count += getLocalElemCountForUri(RingStringsContract.SymbolQualities.CONTENT_URI);
        count += getLocalElemCountForUri(RingStringsContract.SymbolDefQualities.CONTENT_URI);

        assert (ops.size() == count);
    }

    @Test
    public void testIncludeMultipleProfiles() {
        mTestSize = 20;
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        for (int i = 0; i < mTestSize; i++) {
            ProfileSymbolDAO dao = new ProfileSymbolDAO(produceTestSubject(null));
            ops.addAll(dao.batchCreate());
        }


        System.out.println("Total operations: " + ops.size());

        try {
            mShadowContentResolver.applyBatch(RingStringsContract.AUTHORITY, ops);
        } catch (OperationApplicationException e) {
            System.out.println(e.getLocalizedMessage());
            assert (false);
        }

        int count = 0;
        count += getLocalElemCountForUri(RingStringsContract.Symbols.CONTENT_URI);
        count += getLocalElemCountForUri(RingStringsContract.Quality.CONTENT_URI);
        count += getLocalElemCountForUri(RingStringsContract.SymbolDescription.CONTENT_URI);
        count += getLocalElemCountForUri(RingStringsContract.SymbolQualities.CONTENT_URI);
        count += getLocalElemCountForUri(RingStringsContract.SymbolDefQualities.CONTENT_URI);

        assert (ops.size() == count);
    }

    @Test
    public void testRawQueryByIdBundle() {
        testIncludeFullProfile();
        IProfileSymbol profile = produceTestSubject(MockDefaultDataBundles.testProfileBundleCoryH);

        SymbolIDBundle idbundle = new SymbolIDBundle.Builder()
                .profile_id(profile.getIdBundle().profile_id)
                .chart_id(Charts.ASTRAL_CURRENT.ordinal())
                //.strata_id(SymbolStrata.GROUP.ordinal())
                .build();

        IQueryIdBundle querybundle = new QueryIdBundleAdapter(idbundle);
        System.out.println("QueryBundle Selection: " + querybundle.getSelection());
        System.out.println("QueryBundle Args: " + Strings.join(querybundle.getSelectionArgs()).with(", "));

        Uri uri = RingStringsContract.Symbols.CONTENT_URI;
        String[] projection = RingStringsContract.Symbols.PROJECTION_ALL;

        Cursor cursor = mShadowContentResolver.query(uri,
                projection,
                querybundle.getSelection(),
                querybundle.getSelectionArgs(),
                RingStringsContract.Symbols.SORT_ORDER_STRATA);

        if (cursor != null) {

            int count = cursor.getCount();
            boolean avail;
            System.out.println(String.format("%d found for %s..", count, uri.getLastPathSegment()));
            avail = cursor.moveToFirst();
            while (avail) {
                SymbolIdBundleAdapter.fromCursor(cursor).printSymbol();
                //printCursor(cursor, projection);
                avail = cursor.moveToNext();
            }

            cursor.close();
        }

    }

    private void printCursor(Cursor cursor, String[] projection) {
        String qualities = "";

        for (String elem : projection) {
            if (elem.equals(RingStringsContract.Symbols.SYMBOL_QUALITIES)) {
                qualities = cursor.getString(cursor.getColumnIndex(elem));

            } else {
                int type = cursor.getType(cursor.getColumnIndex(elem));

                if (type == Cursor.FIELD_TYPE_INTEGER) {
                    int id = cursor.getInt(cursor.getColumnIndex(elem));
                    System.out.print(elem + ": " + id + " - ");
                } else if (type == Cursor.FIELD_TYPE_STRING) {
                    String field = cursor.getString(cursor.getColumnIndex(elem));
                    System.out.print(elem + ": " + field + " - ");
                }
            }
        }

        if (!qualities.equals("")) {
            System.out.print("SymbolQualities: ");
            printTags(qualities);
        }
    }

    private void printTags(String qualities) {
        List<TagSymbols> taglist = getSymbolQualityTags(qualities);
        Map<TagSymbols, Integer> tagmap = getTagCountMapForList(taglist);

        for (TagSymbols tag : tagmap.keySet()) {
            System.out.print(tag.toString() + "(" + tagmap.get(tag) + ")");
            System.out.print(", ");
        }
        System.out.println();
    }

    private List<TagSymbols> getSymbolQualityTags(String qualities) {
        String[] array = qualities.split(", ");
        List<TagSymbols> tags = new ArrayList<>();
        TagSymbols[] alltags = TagSymbols.values();
        for (String elem : array) {
            tags.add(alltags[Integer.parseInt(elem)]);
        }
        return tags;
    }

    private Map<TagSymbols, Integer> getTagCountMapForList(List<TagSymbols> tags) {
        Map<TagSymbols, Integer> tagCountMap = new HashMap<>();

        for (TagSymbols tag : tags)
            addQuality(tagCountMap, tag);

        return tagCountMap;
    }

    private final void addQuality(Map<TagSymbols, Integer> map, TagSymbols tag) {
        Integer current = map.get(tag);
        if (current == null)
            map.put(tag, 1);
        else
            map.put(tag, current+1);
    }

    enum Suppression {
        NO_SUPPRESSION,
        SUPPRESS_ASTRAL_SYMBOLS,
        SUPPRESS_NUMBER_SYMBOLS
    }

    private ArrayList<ContentProviderOperation> getOperationsForProfile(IProfileSymbol profile, Suppression suppress) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        Collection<ISymbol> symbols = profile.produceSymbol().values();

        for (ISymbol ss : symbols ) {
            switch (suppress) {
                case NO_SUPPRESSION:
                    ops.addAll(produceDAOForSymbol(profile, ss).batchCreate());
                    break;
                case SUPPRESS_NUMBER_SYMBOLS:
                    if (AstralStrata.isAstralSymbol(ss.symbolType()))
                        ops.addAll(produceDAOForSymbol(profile, ss).batchCreate());

                    break;
                case SUPPRESS_ASTRAL_SYMBOLS:
                    if (NumberStrata.isNumberSymbol(ss.symbolType()))
                        ops.addAll(produceDAOForSymbol(profile, ss).batchCreate());

            }

        }

        return ops;
    }

    private ISymbolDAO produceDAOForSymbol(IProfileSymbol parent, ISymbol symbol) {
        ILightSymbol ls = parent.produceLightSymbolFor(symbol);
        return (AstralStrata.isAstralSymbol(symbol.symbolType())) ? AstralSymbolDAO.fromLightSymbol(ls) : NumberSymbolDAO.fromLightSymbol(ls);

    }

    //@Test
    public void testProfileSaves() {
        IProfileSymbol profile = produceTestSubject(null);
        assert (profile.saveProfile());
    }

    private int getLocalElemCountForUri(final Uri uri) {

        // Get symbols from local
        int count = 0;
        Cursor curSymbols = mShadowContentResolver.query(uri, null, null, null, null);

        if (curSymbols != null) {
            count = curSymbols.getCount();
            curSymbols.close();
        }

        System.out.println(String.format("%d found for %s..", count, uri.getLastPathSegment()));
        return count;
    }

}
