package com.coreman2200.data.repository.db;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * RingStringsContract
 * RingStrings Contract describes expectations for storing/retrieving symbol datas & responses.
 *
 * Created by Cory Higginbottom on 12/14/15
 */

public class RingStringsContract {
    public enum Tables {
        SYMBOLS("symbol"),
        QUALITIES("quality"),
        SYMBOLDEFS("symboldef"),
        SYMBOL_QUALITIES("symbol_quality"),
        DEF_QUALITIES("symboldef_quality");

        final private String mTableName;

        Tables(final String name) {
            mTableName = name;
        }

        public String tableName() {
            return mTableName;
        }
    }
    /**
     * The authority of the ringstrings provider.
     */
    public static final String AUTHORITY =
            "vnd.coreman2200.ringstrings.provider";
    /**
     * The content URI for the top-level
     * ringstrings authority.
     */
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY);

    /**
     * A selection clause for ID based queries.
     */
    public static final String SELECTION_ID_BASED = BaseColumns._ID + " = ? ";

    /**
     * Constants for the Symbols table
     * of the ringstrings provider.
     */
    public static final class Symbols
            implements CommonSymbolColumns {
        /**
         * The content URI for this table.
         */
        public static final Uri CONTENT_URI =  Uri.withAppendedPath(RingStringsContract.CONTENT_URI, Tables.SYMBOLS.tableName());
        /**
         * The mime type of a directory of items.
         */
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.ringstrings.ringstrings_symbol";
        /**
         * The mime type of a single item.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.ringstrings.ringstrings_symbol";

        /**
         * The name of this symbol.
         */
        public static final String _NAME = "_name";

        /**
         * The data corresponding to the Symbol value (numerology).
         */
        public static final String _VALUE = "_value";
        /**
         * The data corresponding to the Symbol's degree (astrology).
         */
        public static final String _DEGREE = "_degree";
        /**
         * The data corresponding to the Symbol's SymbolDescription.
         */
        public static final String _DESC_ID = "_desc_id";

        /**
         * Grouping of Qualities, if available for symbol
         */
        public static final String SYMBOL_QUALITIES = "SymbolQualities";

        /**
         * Grouping of Children, if available for symbol
         */
        public static final String SYMBOL_CHILDREN = "SymbolChildren";

        /**
         * A projections of all columns in the symbol table.
         */
        public static final String[] PROJECTION_ALL = {_PROFILEID, _CHARTID, _STRATAID, _TYPEID, _SYMBOLID, _NAME, _DESC_ID, _SIZE, SYMBOL_QUALITIES, SYMBOL_CHILDREN, _DEGREE, _VALUE};

        public static final String[] PROJECTION_ASTROSYMBOL = {_PROFILEID, _CHARTID, _STRATAID, _TYPEID, _SYMBOLID, _NAME, _DESC_ID, _SIZE, _DEGREE, SYMBOL_QUALITIES, SYMBOL_CHILDREN};

        public static final String[] PROJECTION_NUMSYMBOL = {_PROFILEID, _CHARTID, _STRATAID, _TYPEID, _SYMBOLID, _NAME, _DESC_ID, _SIZE, _VALUE, SYMBOL_QUALITIES, SYMBOL_CHILDREN};

        /**
         * The sort order for queries containing NAME fields.
         */
        public static final String SORT_ORDER_DEFAULT = _ID + " ASC";

        public static final String SORT_ORDER_STRATA = _STRATAID + " DESC";

        public static final String SORT_ORDER_CHART = _CHARTID + " ASC";

        public static final String SORT_ORDER_TYPE = _TYPEID + " ASC";

        public static final String SORT_ORDER_SIZE = _SIZE + " DESC";

        public static final String SORT_ORDER_BUILD = _STRATAID + " ASC";
    }

    public static final class Quality
            implements BaseColumns {
        /**
         * The Content URI for this table.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(RingStringsContract.CONTENT_URI, Tables.QUALITIES.tableName());

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/symbol_quality";
        /**
         * The mime type of a single quality.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/symbol_quality";

        /**
         * The name of this quality.
         */
        public static final String _NAME = "_name";

        /**
         * The count for this quality/profile.
         */
        public static final String _COUNT = "_count";

        /**
         * The id of the quality.
         */
        public static final String _QUALITY_ID = "_quality_id";

        /**
         * A projection of all columns in the photos table.
         */
        public static final String[] PROJECTION_ALL = {_ID, _QUALITY_ID, _NAME, _COUNT};

        public static final String SORT_ORDER_DEFAULT = _QUALITY_ID + " DESC";
    }

    public static final class SymbolDescription
            implements BaseColumns {
        /**
         * The Content URI for this table.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(RingStringsContract.CONTENT_URI, Tables.SYMBOLDEFS.tableName());

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/symbol_symboldef";
        /**
         * The mime type of a single quality.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/symbol_symboldef";

        /**
         * The id of the symbol this description belongs to.
         */
        public static final String _SYMBOL_ID = "_symbol_id";

        /**
         * The id of the symbol description.
         */
        public static final String _DESC_ID = "_desc_id";

        /**
         * The id of the quality.
         */
        public static final String _NAME = "_name";

        /**
         * The id of the quality.
         */
        public static final String _DESCRIPTION = "_description";

        /**
         * Stored Proto SymbolDescription Message
         */
        public static final String _MESSAGE = "_message";

        /**
         * A projection of all columns in the table.
         */
        public static final String[] PROJECTION_ALL = {_ID, _NAME, _DESCRIPTION, _MESSAGE, _SYMBOL_ID, _DESC_ID};
    }

    public static final class SymbolDefQualities
            implements BaseColumns {
        /**
         * The Content URI for this table.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(RingStringsContract.CONTENT_URI, Tables.DEF_QUALITIES.tableName());

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/symbol_symboldefquality";
        /**
         * The mime type of a single quality.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/symbol_symboldefquality";

        /**
         * The id of the symbol description.
         */
        public static final String _DESC_ID = "_desc_id";

        /**
         * The id of the quality.
         */
        public static final String _QUALITY_ID = "_quality_id";

        /**
         * A projection of all columns in the table.
         */
        public static final String[] PROJECTION_ALL = {_ID, _DESC_ID, _QUALITY_ID};
    }

    public static final class SymbolQualities
            implements BaseColumns {
        /**
         * The Content URI for this table.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(RingStringsContract.CONTENT_URI, Tables.SYMBOL_QUALITIES.tableName());

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/symbol_symbolquality";
        /**
         * The mime type of a single quality.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/symbol_symbolquality";

        /**
         * The id of the symbol this description belongs to.
         */
        public static final String _SYMBOL_ID = "_symbol_id";


        /**
         * The id of the quality.
         */
        public static final String _QUALITY_ID = "_quality_id";


        /**
         * The count for this quality/symbol.
         */
        public static final String _COUNT = "_count";

        /**
         * A projection of all columns in the photos table.
         */
        public static final String[] PROJECTION_ALL = {_ID, _QUALITY_ID, _SYMBOL_ID, _COUNT};

        public static final String SORT_ORDER_DEFAULT = _COUNT + " DESC";
    }

    /**
     * This interface defines common columns found in multiple tables.
     */
    public static interface CommonSymbolColumns extends BaseColumns {
        /**
         * The symbol's associated profile (id)
         */
        public static final String _PROFILEID = "_profile_id";

        /**
         * The symbol's associated chart (id)
         */
        public static final String _CHARTID = "_chart_id";

        /**
         * The symbol's associated strata (id)
         */
        public static final String _STRATAID = "_strata_id";

        /**
         * The symbol's associated type (id)
         */
        public static final String _TYPEID = "_type_id";

        /**
         * The symbol's id
         */
        public static final String _SYMBOLID = "_symbol_id";

        /**
         * Size of symbol
         */
        public static final String _SIZE = "_size";
    }
}

