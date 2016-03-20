package com.coreman2200.domain.adapter;

import android.database.Cursor;

import com.coreman2200.data.entity.profiledata.IProfileDataBundle;
import com.coreman2200.data.entity.protos.SymbolIDBundle;
import com.coreman2200.domain.symbol.SymbolStrata;
import com.coreman2200.domain.symbol.chart.Charts;
import com.coreman2200.domain.symbol.entitysymbol.Tags.TagSymbols;
import com.coreman2200.domain.symbol.numbersymbol.NumberStrata;
import com.coreman2200.domain.symbol.numbersymbol.grouped.BaseNumberSymbols;
import com.coreman2200.domain.symbol.symbolinterface.ISymbol;
import com.coreman2200.domain.symbol.symbolinterface.ISymbolStrata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SymbolIdBundleAdapter
 * Adapts SymbolIdBundles elems to corresponding symbol ids
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

public class SymbolIdBundleAdapter extends AbstractIdBundleAdapter<Object> implements ISymbolIdBundleAdapter {

    public SymbolIdBundleAdapter(SymbolIDBundle bundle) {
        super(bundle);
        System.out.println(bundle.toString());
    }

    public static final SymbolIdBundleAdapter fromCursor(Cursor cursor) {
        SymbolIDBundle.Builder bundlebuilder = new SymbolIDBundle.Builder();

        if (!cursor.isNull(cursor.getColumnIndex(BundleKeys.PROFILE.getContentKey())))
            bundlebuilder.profile_id(cursor.getInt(cursor.getColumnIndex(BundleKeys.PROFILE.getContentKey())));

        if (!cursor.isNull(cursor.getColumnIndex(BundleKeys.CHART.getContentKey())))
            bundlebuilder.chart_id(cursor.getInt(cursor.getColumnIndex(BundleKeys.CHART.getContentKey())));

        if (!cursor.isNull(cursor.getColumnIndex(BundleKeys.STRATA.getContentKey())))
            bundlebuilder.strata_id(cursor.getInt(cursor.getColumnIndex(BundleKeys.STRATA.getContentKey())));

        if (!cursor.isNull(cursor.getColumnIndex(BundleKeys.TYPE.getContentKey())))
            bundlebuilder.type_id(cursor.getInt(cursor.getColumnIndex(BundleKeys.TYPE.getContentKey())));

        if (!cursor.isNull(cursor.getColumnIndex(BundleKeys.SYMBOL.getContentKey())))
            bundlebuilder.symbol_id(cursor.getInt(cursor.getColumnIndex(BundleKeys.SYMBOL.getContentKey())));

        if (!cursor.isNull(cursor.getColumnIndex(BundleKeys.NAME.getContentKey())))
            bundlebuilder.name(cursor.getString(cursor.getColumnIndex(BundleKeys.NAME.getContentKey())));

        if (!cursor.isNull(cursor.getColumnIndex(BundleKeys.DESCRIPTION.getContentKey())))
            bundlebuilder.desc_id(cursor.getInt(cursor.getColumnIndex(BundleKeys.DESCRIPTION.getContentKey())));

        if (!cursor.isNull(cursor.getColumnIndex(BundleKeys.QUALITIES.getContentKey())))
            bundlebuilder.symbol_qualities(produceIdList(cursor.getString(cursor.getColumnIndex(BundleKeys.QUALITIES.getContentKey()))));

        // TODO: Implement Children expression in db..
        //if (!cursor.isNull(cursor.getColumnIndex(BundleKeys.CHILDREN.getContentKey())))
        //    bundlebuilder.symbol_children(produceIdList(cursor.getString(cursor.getColumnIndex(BundleKeys.CHILDREN.getContentKey()))));

        return new SymbolIdBundleAdapter(bundlebuilder.build());
    }

    public static final SymbolIdBundleAdapter fromSymbol(ISymbol symbol, int profid, int chartid) {
        SymbolIDBundle.Builder builder = new SymbolIDBundle.Builder();
        builder.profile_id(profid);
        builder.chart_id(chartid);
        builder.strata_id(symbol.symbolStrata().ordinal());
        builder.type_id(symbol.symbolType().ordinal());
        builder.symbol_id(symbol.symbolID().ordinal());
        builder.desc_id(symbol.symbolID().ordinal());
        builder.name(symbol.name());

        Collection<Enum<? extends Enum<?>>> children = symbol.symbolIDCollection();
        // TODO: Having to remove self from symbolid collection (assuming consistency with self-eval in symbol)
        children.remove(symbol.symbolID());
        builder.symbol_children(produceIdList(children));

        return new SymbolIdBundleAdapter(builder.build());
    }

    // private static final List<TagSymbols>

    @Override
    protected Map<BundleKeys, Object> produceIdMap() {
        Map<BundleKeys, Object> map = new HashMap<>();
        Charts chart = null;
        Enum<? extends Enum<?>> type = null;

        if (mIdBundle.profile_id != null)
            map.put(BundleKeys.PROFILE, (mIdBundle.profile_id));

        if (mIdBundle.chart_id != null) {
            chart = Charts.values()[mIdBundle.chart_id];
            System.out.print(chart.toString() + " -> ");
            map.put(BundleKeys.CHART, chart);
        }

        if (mIdBundle.strata_id != null) {
            map.put(BundleKeys.STRATA, SymbolStrata.values()[mIdBundle.strata_id]);
            System.out.print(map.get(BundleKeys.STRATA).toString() + " -> ");
        }

        if (mIdBundle.type_id != null && chart != null) {
            type = findTypeWithinChart(chart);
            System.out.print(type.toString() + " -> ");
            map.put(BundleKeys.TYPE, type);
        }

        if (mIdBundle.symbol_id != null && type != null)
            map.put(BundleKeys.SYMBOL, findSymbolIdFromType(type));


        if (mIdBundle.name != null)
            map.put(BundleKeys.NAME, mIdBundle.name);


        if (mIdBundle.symbol_qualities != null)
            map.put(BundleKeys.QUALITIES, getSymbolQualityTags(mIdBundle.symbol_qualities));

        if (mIdBundle.symbol_children != null)
            map.put(BundleKeys.CHILDREN, mIdBundle.symbol_children);

        return map;
    }

    private Enum<? extends Enum<?>> findTypeWithinChart(Charts chart) {
        return chart.getGrouping()[mIdBundle.type_id];
    }

    private Enum<? extends Enum<?>> findSymbolIdFromType(Object type) {
        assert (type != null);
        assert (!type.getClass().isAssignableFrom(ISymbolStrata.class));

        ISymbolStrata strata = (ISymbolStrata)type;

        Enum[] grouping = strata.getGrouping();

        /* TODO: Fix for Derived Numbers */
        if (strata.equals(NumberStrata.DERIVEDNUMBER)) {
            try {
                int baseval = Integer.parseInt(mIdBundle.name);
                return BaseNumberSymbols.getBaseNumberSymbolIDForValue(baseval);
            } catch (NumberFormatException e) { }
        }

        return (grouping == null) ? (Enum<? extends Enum<?>>)strata : grouping[mIdBundle.symbol_id];

    }

    @Override
    public IProfileDataBundle getProfileData() {
        return null; // TODO
    }

    @Override
    public Charts getChart() {
        return (Charts)mIdMap.get(BundleKeys.CHART);
    }

    @Override
    public SymbolStrata getSymbolStrata() {
        return (SymbolStrata)mIdMap.get(BundleKeys.STRATA);
    }

    @Override
    public Enum<? extends Enum<?>> getSymbolType() {
        return (Enum<? extends Enum<?>>)mIdMap.get(BundleKeys.TYPE);
    }

    @Override
    public Enum<? extends Enum<?>> getSymbolId() {
        return (Enum<? extends Enum<?>>)mIdMap.get(BundleKeys.SYMBOL);
    }

    public List<TagSymbols> getSymbolQualityTags() {
        return (List<TagSymbols>)mIdMap.get(BundleKeys.QUALITIES);
    }

    private List<TagSymbols> getSymbolQualityTags(List<Integer> qualities) {
        List<TagSymbols> tags = new ArrayList<>();
        TagSymbols[] alltags = TagSymbols.values();
        for (Integer id : qualities) {
            tags.add(alltags[id]);
        }
        return tags;
    }

    public void printSymbol() {
        for (BundleKeys key : BundleKeys.values()) {

            System.out.print(key.getContentKey() + ": ");
            if (key == BundleKeys.QUALITIES)
                System.out.print(produceTagNameListString((List<TagSymbols>)mIdMap.get(key)));
            else if (key == BundleKeys.CHILDREN)
                System.out.print(((List<Integer>)mIdMap.get(key)).size());
            else if (mIdMap.containsKey(key))
                System.out.print(mIdMap.get(key).toString());
            System.out.print(" - ");
        }
        System.out.println();
    }

    private String produceTagNameListString(List<TagSymbols> list) {
        StringBuilder joined = new StringBuilder();

        for (TagSymbols id : list) {
            joined.append(id.name());
            joined.append(", ");
        }

        return joined.toString();
    }
}
