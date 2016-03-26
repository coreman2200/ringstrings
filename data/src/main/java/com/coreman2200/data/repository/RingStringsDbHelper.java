package com.coreman2200.data.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * RingStringsDbHelper
 * Helper class for RingStrings sql (local) db handling
 *
 * Created by Cory Higginbottom on 12/14/15
 */

public class RingStringsDbHelper extends SQLiteOpenHelper {
    private static final String NAME = RingStringsDbSchema.DB_NAME;
    private static final int VERSION = 1;

    public static final String TBL_SYMBOLS = RingStringsDbSchema.TBL_SYMBOLS;
    public static final String TBL_QUALITIES = RingStringsDbSchema.TBL_QUALITIES;
    public static final String TBL_SYMBOLDEFS = RingStringsDbSchema.TBL_SYMBOLDEFS;
    public static final String TBL_DEFQUAL_JUNCT = RingStringsDbSchema.TBL_DEFQUAL_JUNCT;
    public static final String TBL_SYMBOLQUAL_JUNCT = RingStringsDbSchema.TBL_SYMBOLQUAL_JUNCT;

    public static final String COL_ID = RingStringsDbSchema.COL_ID;
    public static final String COL_PROFILE = RingStringsDbSchema.COL_PROFILE;
    public static final String COL_CHART = RingStringsDbSchema.COL_CHART;
    public static final String COL_STRATA = RingStringsDbSchema.COL_STRATA;
    public static final String COL_TYPE = RingStringsDbSchema.COL_TYPE;
    public static final String COL_SYMBOL = RingStringsDbSchema.COL_SYMBOL;
    public static final String COL_QUALITY = RingStringsDbSchema.COL_QUALITY;
    public static final String COL_SYMBOLDEF = RingStringsDbSchema.COL_SYMBOLDEF;
    public static final String COL_MESSAGE = RingStringsDbSchema.COL_MESSAGE;
    public static final String COL_NAME = RingStringsDbSchema.COL_NAME;
    public static final String COL_DESC = RingStringsDbSchema.COL_DESC;
    public static final String COL_DEGREE = RingStringsDbSchema.COL_DEGREE;
    public static final String COL_VALUE = RingStringsDbSchema.COL_VALUE;
    public static final String COL_SIZE = RingStringsDbSchema.COL_SIZE;
    public static final String COL_COUNT = RingStringsDbSchema.COL_COUNT;
    public static final String COL_SYMBOLQUALITIES = RingStringsDbSchema.COL_SYMBOLQUALITIES;
    public static final String COL_SYMBOLCHILDREN = RingStringsDbSchema.COL_SYMBOLCHILDREN;

    public static final String SYMBOL_JOIN = RingStringsDbSchema.LEFT_OUTER_JOIN_STATEMENT;

    public RingStringsDbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RingStringsDbSchema.DDL_CREATE_TBL_SYMBOLS);
        db.execSQL(RingStringsDbSchema.DDL_CREATE_TBL_QUALITIES);
        db.execSQL(RingStringsDbSchema.DDL_CREATE_TBL_SYMBOLDEFS);
        db.execSQL(RingStringsDbSchema.DDL_CREATE_TBL_SYMBOLQUALITY_JUNCT);
        db.execSQL(RingStringsDbSchema.DDL_CREATE_TBL_DEFQUAL_JUNCT);
        db.execSQL(RingStringsDbSchema.DDL_CREATE_TRIGGER_DEL_SYMBOLS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RingStringsDbSchema.DDL_DROP_TBL_SYMBOLS);
        db.execSQL(RingStringsDbSchema.DDL_DROP_TBL_QUALITY);
        db.execSQL(RingStringsDbSchema.DDL_DROP_TBL_SYMBOLDEF);
        db.execSQL(RingStringsDbSchema.DDL_DROP_TBL_DEFQUAL_JUNCT);
        db.execSQL(RingStringsDbSchema.DDL_DROP_TBL_SYMBOLQUALITY_JUNCT);
        db.execSQL(RingStringsDbSchema.DDL_DROP_TRIGGER_DEL_SYMBOLS);
        onCreate(db);
    }
}