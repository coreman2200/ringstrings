package com.coreman2200.ringstrings.symbol.inputprocessor.entity.symboldef;

import android.content.Context;

import com.coreman2200.ringstrings.R;
import com.coreman2200.ringstrings.RSIO.FileHandlerImpl;

import org.json.JSONException;
import org.json.JSONML;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
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
    static private SymbolDefFileHandlerImpl mInstance;
    private Map<Enum<? extends Enum<?>>, ISymbolDef> mSymbolDefs = new HashMap<>();
    private JSONObject mSymbolData;

    private SymbolDefFileHandlerImpl(Context context) {
        super(context);
        produceSymbolDefDataObject();
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

    private void produceSymbolDefDataObject() {
        InputStream symboldefIS = this.getInputStreamToRawResource(R.raw.symboldefs); // TODO: access to R.raw.symboldefs?
        try {
            mSymbolData = new JSONObject(writeInputStreamToString(symboldefIS));
        } catch (JSONException e) {
            e.printStackTrace();
            assert (false);
        }

    }

    public ISymbolDef produceSymbolDefForSymbol(Enum<? extends Enum<?>> symbol) {
        ISymbolDef symboldef = mSymbolDefs.get(symbol);
        if (symboldef == null)
            symboldef = makeSymbolDefForSymbol(symbol);

        return symboldef;
    }

    private ISymbolDef makeSymbolDefForSymbol(Enum<? extends Enum<?>> symbol) {
        JSONObject symbolData = mSymbolData.optJSONObject(symbol.name());
        if (symbolData == null)
            return null;

        ISymbolDef symbolDef = new SymbolDefImpl(symbol, symbolData);
        mSymbolDefs.put(symbol, symbolDef);
        return symbolDef;
    }

}
