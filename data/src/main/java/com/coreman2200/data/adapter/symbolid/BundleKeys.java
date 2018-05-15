package com.coreman2200.data.adapter.symbolid;

import com.coreman2200.data.repository.db.RingStringsDbHelper;

/**
 * BundleKeys
 * description
 *
 * Created by Cory Higginbottom on 3/25/16
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

// //_PROFILEID, _CHARTID, _STRATAID, _TYPEID, _SYMBOLID, _DESC_ID, _NAME, SymbolQualities, _CHILDREN, Settings

enum BundleKeys {
    PROFILE(RingStringsDbHelper.COL_PROFILE),
    CHART(RingStringsDbHelper.COL_CHART),
    STRATA(RingStringsDbHelper.COL_STRATA),
    TYPE(RingStringsDbHelper.COL_TYPE),
    SYMBOL(RingStringsDbHelper.COL_SYMBOL),
    DESCRIPTION(RingStringsDbHelper.COL_SYMBOLDEF),
    NAME(RingStringsDbHelper.COL_NAME),
    QUALITIES(RingStringsDbHelper.COL_SYMBOLQUALITIES),
    CHILDREN(RingStringsDbHelper.COL_SYMBOLCHILDREN);


    private final String mDbKey;

    BundleKeys(final String key) {
        mDbKey = key;
    }

    public String getContentKey() {
        return mDbKey;
    }
}