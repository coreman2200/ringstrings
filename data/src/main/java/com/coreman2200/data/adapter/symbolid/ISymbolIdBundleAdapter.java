package com.coreman2200.data.adapter.symbolid;

import com.coreman2200.domain.profiledata.IProfileDataBundle;
import com.coreman2200.domain.symbol.SymbolStrata;
import com.coreman2200.domain.symbol.chart.Charts;
import com.coreman2200.presentation.symbol.tags.TagSymbols;

import java.util.List;

/**
 * ISymbolIdBundleAdapter
 * description
 *
 * Created by Cory Higginbottom on 3/10/16
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

// _PROFILEID, _CHARTID, _STRATAID, _TYPEID, _SYMBOLID, _DESC_ID, _NAME, SymbolQualities, _CHILDREN, Settings
public interface ISymbolIdBundleAdapter {
    IProfileDataBundle getProfileData();
    Charts getChart();
    SymbolStrata getSymbolStrata();
    Enum<? extends Enum<?>> getSymbolType();
    Enum<? extends Enum<?>> getSymbolId();
    public List<TagSymbols> getSymbolQualityTags();
    //Enum<? extends Enum<?>> getChildAt(int index);
    //List<Enum<? extends Enum<?>>> getChildren();

}
