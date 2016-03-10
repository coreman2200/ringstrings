package com.coreman2200.ringstrings.rsdisplay.fragments;

import android.content.res.Resources;

import com.coreman2200.ringstrings.R;

/**
 * SymbolViewTabs
 * enum listing available symbolview tabs, and qualifying attributes
 *
 * Created by Cory Higginbottom on 3/3/16
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public enum SymbolViewTabs {
    SYMBOLVIEW_DETAIL_TAB(R.string.symbolview_details, R.layout.fragment_symbolviewcontent_empty),
    SYMBOLVIEW_RELATED_TAB(R.string.symbolview_related, R.layout.fragment_symbolviewcontent_empty),
    SYMBOLVIEW_COMPARED_TAB(R.string.symbolview_compared, R.layout.fragment_symbolviewcontent_empty),
    SYMBOLVIEW_PROFILE_TAB(R.string.symbolview_profile, R.layout.fragment_symbolviewcontent_empty);

    private final int mTagNameStringId;
    private final int mTabLayoutId;

    SymbolViewTabs(final int stringRezId, final int layoutId) {
        mTagNameStringId = stringRezId;
        mTabLayoutId = layoutId;
    }

    public String getTagName(Resources rez) {
        return rez.getString(mTagNameStringId);
    }

    public int getLayoutForTag() {
        return mTabLayoutId;
    }

    public int getTagId() {
        return mTagNameStringId;
    }
    public int getTagPosition() { return this.ordinal(); }

}
