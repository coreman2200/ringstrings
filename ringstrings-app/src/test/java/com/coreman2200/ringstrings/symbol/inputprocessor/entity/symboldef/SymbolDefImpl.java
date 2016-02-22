package com.coreman2200.ringstrings.symbol.inputprocessor.entity.symboldef;

import com.coreman2200.ringstrings.symbol.entitysymbol.Tags.TagSymbols;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * SymbolDefImpl
 * SymbolDef bundles attributing data for symbols
 *
 * Created by Cory Higginbottom on 11/4/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class SymbolDefImpl implements ISymbolDef {
    private String mDescription;
    private String mName;
    private Collection<Enum<? extends Enum<?>>> mTags;
    private Enum<? extends Enum<?>> mID;

    public SymbolDefImpl(Enum<? extends Enum<?>> id, JSONObject symbol) {
        mID = id;
        try {
            mName = symbol.getString("Name");
            mTags = produceQualities(symbol.getJSONArray("Quality"));
            mDescription = symbol.getString("Description");

        } catch (JSONException e) {
            e.printStackTrace();
            assert (false);
        }
    }

    private Collection<Enum<? extends Enum<?>>> produceQualities(JSONArray qualities) throws JSONException {

        Collection<Enum<? extends Enum<?>>> tags = new ArrayList<>(qualities.length());
        for (int i = 0; i < qualities.length(); i++) {
            tags.add(TagSymbols.getTagForString(qualities.getString(i)));
        }

        return tags;
    }

    public Enum<? extends Enum<?>> symbolID() { return mID;}

    @Override
    public Collection<Enum<? extends Enum<?>>> getQualities() {
        return mTags;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getName() {
        return mName;
    }
}
