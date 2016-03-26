package com.coreman2200.data.rsio.symboldef;

import android.content.Context;

import com.coreman2200.data.R;
import com.coreman2200.data.rsio.FileHandlerImpl;
import com.coreman2200.domain.protos.SymbolDescription;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SymbolDefFileHandlerImpl
 * description
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

public class SymbolDefFileHandlerImpl extends FileHandlerImpl implements ISymbolDefFileHandler {
    enum SymbolDefKeys {
        NAME("Name"),
        QUALITIES("Qualities"),
        DESCRIPTION("Description"),
        COLOR("Color");

        final String mKey;

        SymbolDefKeys(final String key) {
            mKey = key;
        }

        final public String getKey() {
            return mKey;
        }
    }

    private static SymbolDefFileHandlerImpl mInstance;
    private static final Map<Enum<? extends Enum<?>>, SymbolDescription> mSymbolDefs = new HashMap<>();
    private JSONObject mSymbolData;

    private SymbolDefFileHandlerImpl(Context context) {
        super(context);
        produceSymbolDefJSONDataObject();
        mInstance = this;
    }

    static public ISymbolDefFileHandler getInstance() {
        if (mInstance == null)
            assert(false);
        return mInstance;
    }

    static public ISymbolDefFileHandler createInstance(Context context) {
        if (mInstance == null)
            mInstance = new SymbolDefFileHandlerImpl(context);
        return mInstance;
    }

    private void produceSymbolDefJSONDataObject() {
        InputStream symboldefIS = this.getInputStreamForRawResource(R.raw.symboldefs); // TODO: access to R.raw.symboldefs?
        try {
            mSymbolData = new JSONObject(writeInputStreamToString(symboldefIS));
        } catch (JSONException e) {
            e.printStackTrace();
            assert (false);
        }

    }

    public SymbolDescription produceSymbolDefForSymbol(Enum<? extends Enum<?>> symbol) {
        SymbolDescription symboldef = mSymbolDefs.get(symbol);
        if (symboldef == null)
            symboldef = makeSymbolDefForSymbol(symbol);

        return symboldef;
    }

    private SymbolDescription makeSymbolDefForSymbol(Enum<? extends Enum<?>> symbol) {
        JSONObject symbolData = mSymbolData.optJSONObject(symbol.name());
        if (symbolData == null)
            return null;

        SymbolDescription def = null;
        try {
            List<String> qualities = getListFromJSONArray(symbolData.getJSONArray(SymbolDefKeys.QUALITIES.getKey()));
            def =new SymbolDescription.Builder()
                    .name(symbolData.getString(SymbolDefKeys.NAME.getKey()))
                    .description(symbolData.getString(SymbolDefKeys.DESCRIPTION.getKey()))
                    .qualities(qualities)
                    //.color(####)
                    .build();

            mSymbolDefs.put(symbol, def);
        } catch (JSONException e) {
            System.out.println(e.getLocalizedMessage());
            assert (false);
        }
        return def;
    }

    private List<String> getListFromJSONArray(JSONArray array) throws JSONException {
        List<String> list = new ArrayList<>();

        if (array == null)
            return list;

        for (int i =0; i < array.length(); i++)
            list.add(array.getString(i));

        return list;
    }

}
