package com.coreman2200.data.adapter.symbolid;

import com.coreman2200.domain.model.protos.SymbolIDBundle;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * QueryIdBundleAdapter
 * Adapts a SymbolIdBundle into a raw query or query constituent parts for persisted Symbols
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

public class QueryIdBundleAdapter extends AbstractIdBundleAdapter<String> implements IQueryIdBundle {

    public QueryIdBundleAdapter(final SymbolIDBundle bundle) {
        super(bundle);
    }

    @Override
    protected Map<BundleKeys, String> produceIdMap() {
        Map<BundleKeys, String> map = new HashMap<>();

        if (mIdBundle.profile_id != null)
            map.put(BundleKeys.PROFILE, String.valueOf(mIdBundle.profile_id));

        if (mIdBundle.chart_id != null)
            map.put(BundleKeys.CHART, String.valueOf(mIdBundle.chart_id));

        if (mIdBundle.strata_id != null)
            map.put(BundleKeys.STRATA, String.valueOf(mIdBundle.strata_id));


        if (mIdBundle.type_id != null)
            map.put(BundleKeys.TYPE, String.valueOf(mIdBundle.type_id));


        if (mIdBundle.symbol_id != null)
            map.put(BundleKeys.SYMBOL, String.valueOf(mIdBundle.symbol_id));

        if (mIdBundle.desc_id != null)
            map.put(BundleKeys.DESCRIPTION, String.valueOf(mIdBundle.desc_id));

        if (mIdBundle.name != null)
            map.put(BundleKeys.NAME, mIdBundle.name);


        if (mIdBundle.symbol_qualities != null && !mIdBundle.symbol_qualities.isEmpty())
            map.put(BundleKeys.QUALITIES, produceListString(mIdBundle.symbol_qualities));


        //if (mIdBundle.symbol_children != null && !mIdBundle.symbol_children.isEmpty())
        //    map.put(BundleKeys.CHILDREN, produceListString(mIdBundle.symbol_children));
        
        return map;
    }

    public String getSelection() {
        StringBuilder selectionBuilder = new StringBuilder();
        boolean where = false;
        final String whereString = "WHERE ";
        final String andString = "AND ";

        for (BundleKeys key : BundleKeys.values()) {
            if (mIdMap.get(key) != null) {
                selectionBuilder.append((!where) ? whereString : andString);
                selectionBuilder.append(key.getContentKey() + " = ? ");
                where = true;
            }
        }

        return selectionBuilder.toString();
    }

    public String[] getSelectionArgs() {
        Collection<String> selectionArgs = mIdMap.values();
        
        String[] args = new String[selectionArgs.size()];

        int i = 0;
        for (BundleKeys key : BundleKeys.values()) {
            String argstring = mIdMap.get(key);
            if (argstring != null && !argstring.isEmpty()) {
                args[i++] = argstring;
            }
        }

        return args;

    }
}
