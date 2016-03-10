package com.coreman2200.ringstrings.rsprovider;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;

import com.coreman2200.ringstrings.profiledata.IProfileDataBundle;
import com.coreman2200.ringstrings.profiledata.ProfileDataBundleAdapter;
import com.coreman2200.ringstrings.profiledata.TestDefaultDataBundles;
import com.coreman2200.ringstrings.protos.RingStringsAppSettings;
import com.coreman2200.ringstrings.protos.SymbolIDBundle;
import com.coreman2200.ringstrings.rsdisplay.activity.RingStringsActivity;
import com.coreman2200.ringstrings.rsprovider.dao.AbstractSymbolDAO;
import com.coreman2200.ringstrings.rsprovider.dao.AstralSymbolDAO;
import com.coreman2200.ringstrings.rsprovider.dao.ISymbolDAO;
import com.coreman2200.ringstrings.rsprovider.dao.NumberSymbolDAO;
import com.coreman2200.ringstrings.rsprovider.dao.ProfileSymbolDAO;
import com.coreman2200.ringstrings.symbol.astralsymbol.AstralStrata;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IAstralSymbol;
import com.coreman2200.ringstrings.symbol.chart.Charts;
import com.coreman2200.ringstrings.symbol.entitysymbol.EntityStrata;
import com.coreman2200.ringstrings.symbol.entitysymbol.Lights.ILightSymbol;
import com.coreman2200.ringstrings.symbol.entitysymbol.Lights.LightSymbolImpl;
import com.coreman2200.ringstrings.symbol.entitysymbol.Profile.IProfileSymbol;
import com.coreman2200.ringstrings.symbol.entitysymbol.Tags.TagSymbols;
import com.coreman2200.ringstrings.symbol.inputprocessor.ProfileInputProcessor;
import com.coreman2200.ringstrings.symbol.inputprocessor.entity.symboldef.SymbolDefFileHandlerImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.NumberStrata;
import com.coreman2200.ringstrings.symbol.symbolinterface.ISymbol;

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
import java.util.Collections;
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
@Config(constants = com.coreman2200.ringstrings.BuildConfig.class, sdk = 21)
public class TestRingStringsContentProvider {
    private Activity mTestActivity;
    private RingStringsContentProvider mProvider;
    private ContentResolver mContentResolver;
    private ShadowContentResolver mShadowContentResolver;
    private ProfileInputProcessor mProfileProcessor;
    private IProfileSymbol mProfileSymbol;
    private int mTestSize = 0;

    @Before
    public void setup() {
        mTestActivity = Robolectric.setupActivity(RingStringsActivity.class);
        mProvider = new RingStringsContentProvider();
        mContentResolver = mTestActivity.getContentResolver();
        mShadowContentResolver = Shadows.shadowOf(mContentResolver);
        SymbolDefFileHandlerImpl.createInstance(mTestActivity);
        RingStringsAppSettings settings = TestDefaultDataBundles.produceDefaultAppSettingsBundle(mTestActivity);
        IProfileDataBundle bundle = new ProfileDataBundleAdapter(TestDefaultDataBundles.testProfileBundleCoryH);
        mProfileProcessor = new ProfileInputProcessor(settings);
        mProfileSymbol = mProfileProcessor.produceProfile(bundle, EntityStrata.USER);
        mProvider.onCreate();
        ShadowContentResolver.registerProvider(RingStringsContract.AUTHORITY, mProvider);
    }

    //@Test
    public void testInsertForSymbol() {
        int testSize = 1;

        ILightSymbol symbol = produceLightSymbol(Charts.ASTRAL_NATAL);
        AstralSymbolDAO dao = AstralSymbolDAO.fromLightSymbol(symbol);
        mShadowContentResolver.insert(RingStringsContract.Symbols.CONTENT_URI, dao.getContentValues());

        assert (getLocalElemCountForUri(RingStringsContract.Symbols.CONTENT_URI) == testSize);
    }

    private ILightSymbol produceLightSymbol(Enum<? extends Enum<?>> id) {
        return mProfileSymbol.produceLightSymbolFor(getSymbol(id));
    }

    public ISymbol getSymbol(Enum<? extends Enum<?>> preferred) {

        Map<Enum<? extends Enum<?>>, ISymbol> symbolmap = mProfileSymbol.produceSymbol();
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
        ProfileSymbolDAO dao = new ProfileSymbolDAO(mProfileSymbol);
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
    public void testRawQueryByIdBundle() {
        testIncludeFullProfile();

        SymbolIDBundle idbundle = new SymbolIDBundle.Builder()
                .profile_id(mProfileSymbol.getIdBundle().profile_id)
                //.chart_id(Charts.ASTRAL_NATAL.ordinal())
                //.type_id()
                .build();

        Uri uri = RingStringsContract.Symbols.CONTENT_URI;
        String[] projection = RingStringsContract.Symbols.PROJECTION_ALL;
        String selection = selectionFromIdBundle(idbundle);

        System.out.println("Test Selection: " + selection);

        Cursor cursor = mShadowContentResolver.query(uri,
                projection,
                selection,
                selectionArgsFromIdBundle(idbundle),
                RingStringsContract.Symbols.SORT_ORDER_STRATA);

        if (cursor != null) {

            int count = cursor.getCount();
            boolean avail;
            System.out.println(String.format("%d found for %s..", count, uri.getLastPathSegment()));
            cursor.moveToFirst();
            do {
                printCursor(cursor, projection);
                avail = cursor.moveToNext();
            } while (avail);

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

    private String[] selectionArgsFromIdBundle(SymbolIDBundle bundle) {
        List<String> selectionArgs = new ArrayList<>();

        selectionArgs.add(String.valueOf(bundle.profile_id));

        if (bundle.chart_id != null)
            selectionArgs.add(String.valueOf(bundle.chart_id));

        if (bundle.strata_id != null)
            selectionArgs.add(String.valueOf(bundle.strata_id));


        if (bundle.type_id != null)
            selectionArgs.add(String.valueOf(bundle.type_id));


        if (bundle.symbol_id != null)
            selectionArgs.add(String.valueOf(bundle.symbol_id));

        String[] args = new String[selectionArgs.size()];
        return selectionArgs.toArray(args);

    }

    private String selectionFromIdBundle(SymbolIDBundle bundle) {
        StringBuilder selectionBuilder = new StringBuilder();

        selectionBuilder.append("WHERE " + RingStringsDbHelper.COL_PROFILE + " = ? ");

        if (bundle.chart_id != null)
            selectionBuilder.append("AND " + RingStringsDbHelper.COL_CHART + " = ? ");


        if (bundle.strata_id != null)
            selectionBuilder.append("AND " + RingStringsDbHelper.COL_STRATA + " = ? ");


        if (bundle.type_id != null)
            selectionBuilder.append("AND " + RingStringsDbHelper.COL_TYPE + " = ? ");


        if (bundle.symbol_id != null)
            selectionBuilder.append("AND " + RingStringsDbHelper.COL_SYMBOL + " = ? ");

        return selectionBuilder.toString();
    }


    //@Test
    public void testSymbolBatchInclude() {
        ArrayList<ContentProviderOperation> ops = getOperationsForProfile(mProfileSymbol, Suppression.NO_SUPPRESSION);

        System.out.println("Total operations: " + ops.size());

        try {
            mShadowContentResolver.applyBatch(RingStringsContract.AUTHORITY, ops);
        } catch (OperationApplicationException e) {
            System.out.println(e.getLocalizedMessage());
            assert (false);
        }



        int count = 0;
        count += getLocalElemCountForUri(RingStringsContract.Symbols.CONTENT_URI);
        /*count += getLocalElemCountForUri(RingStringsContract.Quality.CONTENT_URI);
        count += getLocalElemCountForUri(RingStringsContract.SymbolDescription.CONTENT_URI);
        count += getLocalElemCountForUri(RingStringsContract.SymbolQualities.CONTENT_URI);
        count += getLocalElemCountForUri(RingStringsContract.SymbolDefQualities.CONTENT_URI);*/

        assert (ops.size() == count);

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

    /*
    @Test
    public void testBatchIncludeSymbols() {
        mTestSize = 10;
        ArrayList<ContentProviderOperation> ops = new ArrayList<>(mTestSize);
        ArrayList<Symbol> symbols = generateTestSymbolsWithCount(mTestSize);

        for (int i = 0; i < mTestSize; i++ ) {
            ops.add(ContentProviderOperation.newInsert(RingStringsContract.Symbols.CONTENT_URI)
                    .withValues(new SymbolDAO(symbols.get(i)).getContentValues())
                    .withYieldAllowed(true)
                    .build());
        }

        try {
            mShadowContentResolver.applyBatch(RingStringsContract.AUTHORITY, ops);
        } catch (OperationApplicationException e) {
            System.out.println(e.getLocalizedMessage());
            assert (false);
        }

        assert (getLocalElemCountForUri(RingStringsContract.Symbols.CONTENT_URI) == mTestSize);

    }
     */

    //@Test
    public void testProfileSaves() {
        assert (mProfileSymbol.saveProfile());
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
