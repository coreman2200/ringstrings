package com.coreman2200.ringstrings.data.rsprovider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * RingStringsContentProvider
 * RingStringContentProvider stores symbols and enables faster loading/querying of profile
 * symbol groupings.
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

public class RingStringsContentProvider  extends ContentProvider {

    // helper constants for use with the UriMatcher
    private static final int SYMBOL_LIST = 1;
    private static final int SYMBOL_ID = 2;
    private static final int QUALITY_LIST = 4;
    private static final int QUALITY_ID = 5;
    private static final int SYMBOLDEF_ID = 7;
    private static final int SYMBOLDEF_LIST = 8;
    private static final int DEFQUALITY_ID = 9;
    private static final int DEFQUALITY_LIST  = 10;
    private static final int SYMBOLQUALITY_ID = 11;
    private static final int SYMBOLQUALITY_LIST  = 12;
    private static final UriMatcher URI_MATCHER;

    private RingStringsDbHelper mHelper = null;
    private final ThreadLocal<Boolean> mIsInBatchMode = new ThreadLocal<Boolean>();

    // prepare the UriMatcher
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(RingStringsContract.AUTHORITY, "symbol", SYMBOL_LIST);
        URI_MATCHER.addURI(RingStringsContract.AUTHORITY, "symbol/#", SYMBOL_ID);
        URI_MATCHER.addURI(RingStringsContract.AUTHORITY, "quality", QUALITY_LIST);
        URI_MATCHER.addURI(RingStringsContract.AUTHORITY, "quality/#", QUALITY_ID);
        URI_MATCHER.addURI(RingStringsContract.AUTHORITY, "symboldef", SYMBOLDEF_LIST);
        URI_MATCHER.addURI(RingStringsContract.AUTHORITY, "symboldef/#", SYMBOLDEF_ID);
        URI_MATCHER.addURI(RingStringsContract.AUTHORITY, "symboldef_quality", DEFQUALITY_LIST);
        URI_MATCHER.addURI(RingStringsContract.AUTHORITY, "symboldef_quality/#", DEFQUALITY_ID);
        URI_MATCHER.addURI(RingStringsContract.AUTHORITY, "symbol_quality", SYMBOLQUALITY_LIST);
        URI_MATCHER.addURI(RingStringsContract.AUTHORITY, "symbol_quality/#", SYMBOLQUALITY_ID);
    }


    @Override
    public boolean onCreate() {
        mHelper = new RingStringsDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case SYMBOL_ID:
                return RingStringsContract.Symbols.CONTENT_ITEM_TYPE;
            case SYMBOL_LIST:
                return RingStringsContract.Symbols.CONTENT_TYPE;
            case QUALITY_ID:
                return RingStringsContract.Quality.CONTENT_ITEM_TYPE;
            case QUALITY_LIST:
                return RingStringsContract.Quality.CONTENT_TYPE;
            case SYMBOLDEF_ID:
                return RingStringsContract.SymbolDescription.CONTENT_ITEM_TYPE;
            case SYMBOLDEF_LIST:
                return RingStringsContract.SymbolDescription.CONTENT_TYPE;
            case DEFQUALITY_ID:
                return RingStringsContract.SymbolDefQualities.CONTENT_ITEM_TYPE;
            case DEFQUALITY_LIST:
                return RingStringsContract.SymbolDefQualities.CONTENT_TYPE;
            case SYMBOLQUALITY_ID:
                return RingStringsContract.SymbolQualities.CONTENT_ITEM_TYPE;
            case SYMBOLQUALITY_LIST:
                return RingStringsContract.SymbolQualities.CONTENT_TYPE;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        if (URI_MATCHER.match(uri) != SYMBOL_LIST
                && URI_MATCHER.match(uri) != QUALITY_LIST
                && URI_MATCHER.match(uri) != SYMBOLDEF_LIST
                && URI_MATCHER.match(uri) != DEFQUALITY_LIST
                && URI_MATCHER.match(uri) != SYMBOLQUALITY_LIST) {
            throw new IllegalArgumentException(
                    "Unsupported URI for insertion: " + uri);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();

        long id;


        if (URI_MATCHER.match(uri) == SYMBOL_LIST) {
            id = db.insert(RingStringsDbSchema.TBL_SYMBOLS, null, values);

        } else if (URI_MATCHER.match(uri) == QUALITY_LIST) {
            id = db.insert(RingStringsDbSchema.TBL_QUALITIES, null, values);

        } else if (URI_MATCHER.match(uri) == SYMBOLDEF_LIST) {
            id = db.insert(RingStringsDbSchema.TBL_SYMBOLDEFS, null, values);

        } else if (URI_MATCHER.match(uri) == DEFQUALITY_LIST) {
            id = db.insert(RingStringsDbSchema.TBL_DEFQUAL_JUNCT, null, values);

        } else if (URI_MATCHER.match(uri) == SYMBOLQUALITY_LIST) {
            id = db.insert(RingStringsDbSchema.TBL_SYMBOLQUAL_JUNCT, null, values);

        } else {
            // this insertWithOnConflict is a special case; CONFLICT_REPLACE
            // means that an existing entry which violates the UNIQUE constraint
            // on the item_id column gets deleted. That is this INSERT behaves
            // nearly like an UPDATE. Though the new row has a new primary key.
            // See how I mentioned this in the Contract class.

            id = db.insertWithOnConflict(uri.getLastPathSegment(), null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }

        try {
            return getUriForId(id, uri);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }

    }

    private Uri getUriForId(long id, Uri uri) throws SQLException {
        if (id > 0) {
            Uri itemUri = ContentUris.withAppendedId(uri, id);
            if (!isInBatchMode()) {
                // notify all listeners of changes and return itemUri:
                getContext().
                        getContentResolver().
                        notifyChange(itemUri, null);
            }
            return itemUri;
        }
        // s.th. went wrong:
        throw new SQLException("Problem while inserting into uri: " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = mHelper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        boolean useAuthorityUri = false;
        switch (URI_MATCHER.match(uri)) {
            case SYMBOL_LIST:
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = RingStringsContract.Symbols.SORT_ORDER_STRATA;
                }
                Cursor cursor = db.rawQuery(symbolQuery(projection, selection, sortOrder), selectionArgs);

                // if we want to be notified of any changes:
                if (useAuthorityUri) {
                    cursor.setNotificationUri(getContext().getContentResolver(), RingStringsContract.CONTENT_URI);
                }
                else {
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                }
                return cursor;

            case SYMBOL_ID:
                builder.setTables(RingStringsDbSchema.TBL_SYMBOLS);
                // limit query to one row at most:
                builder.appendWhere(RingStringsContract.Symbols._SYMBOLID + " = "
                        + uri.getLastPathSegment());
                break;
            case QUALITY_LIST:
                builder.setTables(RingStringsDbSchema.TBL_QUALITIES);
                break;
            case QUALITY_ID:
                builder.setTables(RingStringsDbSchema.TBL_QUALITIES);
                // limit query to one row at most:
                builder.appendWhere(RingStringsDbSchema.TBL_QUALITIES + "." + RingStringsContract.Quality._QUALITY_ID + " = " + uri.getLastPathSegment());
                break;
            case SYMBOLDEF_LIST:
                builder.setTables(RingStringsDbSchema.TBL_SYMBOLDEFS);
                break;
            case SYMBOLDEF_ID:
                builder.setTables(RingStringsDbSchema.TBL_SYMBOLDEFS);
                // limit query to one row at most:
                builder.appendWhere(RingStringsDbSchema.TBL_SYMBOLDEFS + "." + RingStringsContract.SymbolDescription._DESC_ID + " = " + uri.getLastPathSegment());
                break;
            case SYMBOLQUALITY_LIST:
                builder.setTables(RingStringsDbSchema.TBL_SYMBOLQUAL_JUNCT);
                break;
            case SYMBOLQUALITY_ID:
                builder.setTables(RingStringsDbSchema.TBL_SYMBOLQUAL_JUNCT);
                // limit query to one row at most:
                builder.appendWhere(RingStringsDbSchema.TBL_SYMBOLQUAL_JUNCT + "." + RingStringsContract.Quality._QUALITY_ID + " = " + uri.getLastPathSegment());
                break;
            case DEFQUALITY_LIST:
                builder.setTables(RingStringsDbSchema.TBL_DEFQUAL_JUNCT);
                break;
            case DEFQUALITY_ID:
                builder.setTables(RingStringsDbSchema.TBL_DEFQUAL_JUNCT);
                // limit query to one row at most:
                builder.appendWhere(RingStringsDbSchema.TBL_DEFQUAL_JUNCT + "." + RingStringsContract.SymbolDescription._DESC_ID + " = " + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        Cursor cursor = builder.query(db, projection, selection, selectionArgs,
                null, null, sortOrder);
        // if we want to be notified of any changes:
        if (useAuthorityUri) {
            cursor.setNotificationUri(getContext().getContentResolver(), RingStringsContract.CONTENT_URI);
        }
        else {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    private String symbolQuery(String[] projection, String selection, String sortOrder) {
        StringBuilder selectionBuilder = new StringBuilder();

        //String projString = (projection != null) ? Strings.join(projection).with(", ") : "*";
        //System.out.println(projString);

        selectionBuilder.append("SELECT * FROM ");
        selectionBuilder.append(RingStringsDbSchema.LEFT_OUTER_JOIN_STATEMENT + " \n");
        if (selection != null && !selection.isEmpty())
            selectionBuilder.append(selection + " \n");
        selectionBuilder.append("ORDER BY " + sortOrder);

        System.out.println(selectionBuilder.toString());
        return selectionBuilder.toString();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = mHelper.getWritableDatabase();
        int delCount = 0;
        switch (URI_MATCHER.match(uri)) {
            case SYMBOL_LIST:
                delCount = db.delete(RingStringsDbSchema.TBL_SYMBOLS, selection, selectionArgs);
                break;
            case SYMBOL_ID:
                String idStr = uri.getLastPathSegment();
                String where = RingStringsContract.Symbols._ID + " = " + idStr;
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                delCount = db.delete(RingStringsDbSchema.TBL_SYMBOLS, where, selectionArgs);
                break;
            default:
                // no support for deleting not-geosettings
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        // notify all listeners of changes:
        if (delCount > 0 && !isInBatchMode()) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return delCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        SQLiteDatabase db = mHelper.getWritableDatabase();
        int updateCount = 0;
        switch (URI_MATCHER.match(uri)) {
            case SYMBOL_LIST:
                updateCount = db.update(RingStringsDbSchema.TBL_SYMBOLS, values, selection,
                        selectionArgs);
                break;
            case SYMBOL_ID:
                String idStr = uri.getLastPathSegment();
                String where = RingStringsContract.Symbols._ID + " = " + idStr;
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                updateCount = db.update(RingStringsDbSchema.TBL_SYMBOLS, values, where,
                        selectionArgs);
                break;
            default:
                // no support for updating photos!
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        // notify all listeners of changes:
        if (updateCount > 0 && !isInBatchMode()) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updateCount;
    }

    @Override
    public ContentProviderResult[] applyBatch(
            ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        mIsInBatchMode.set(true);
        // the next line works because SQLiteDatabase
        // uses a thread local SQLiteSession object for
        // all manipulations
        db.beginTransaction();
        try {
            final ContentProviderResult[] retResult = super.applyBatch(operations);
            db.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(RingStringsContract.CONTENT_URI, null);
            return retResult;
        }
        finally {
            mIsInBatchMode.remove();
            db.endTransaction();
        }
    }

    private boolean isInBatchMode() {
        return mIsInBatchMode.get() != null && mIsInBatchMode.get();
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode)
            throws FileNotFoundException {

        if (URI_MATCHER.match(uri) != QUALITY_ID || URI_MATCHER.match(uri) != SYMBOLDEF_ID) {
            throw new IllegalArgumentException(
                    "URI invalid. Use an id-based URI only.");
        }
        return openFileHelper(uri, mode);
    }

}