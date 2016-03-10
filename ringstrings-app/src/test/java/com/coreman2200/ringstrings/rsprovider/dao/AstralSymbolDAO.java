package com.coreman2200.ringstrings.rsprovider.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.coreman2200.ringstrings.protos.RingStringsAppSettings;
import com.coreman2200.ringstrings.protos.SymbolIDBundle;
import com.coreman2200.ringstrings.rsprovider.RingStringsContract;
import com.coreman2200.ringstrings.rsprovider.RingStringsDbHelper;
import com.coreman2200.ringstrings.symbol.astralsymbol.AstralStrata;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Aspects;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.CelestialBodies;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Houses;
import com.coreman2200.ringstrings.symbol.astralsymbol.grouped.Zodiac;
import com.coreman2200.ringstrings.symbol.astralsymbol.impl.AspectedSymbolsImpl;
import com.coreman2200.ringstrings.symbol.astralsymbol.impl.CelestialBodySymbolImpl;
import com.coreman2200.ringstrings.symbol.astralsymbol.impl.FictionalBodySymbolImpl;
import com.coreman2200.ringstrings.symbol.astralsymbol.impl.HouseSymbolImpl;
import com.coreman2200.ringstrings.symbol.astralsymbol.impl.ZodiacSymbolImpl;
import com.coreman2200.ringstrings.symbol.astralsymbol.interfaces.IAstralSymbol;
import com.coreman2200.ringstrings.symbol.chart.AstrologicalChartImpl;
import com.coreman2200.ringstrings.symbol.chart.Charts;
import com.coreman2200.ringstrings.symbol.entitysymbol.Lights.ILightSymbol;
import com.coreman2200.ringstrings.symbol.entitysymbol.Lights.LightSymbolImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AstralSymbolDAO
 * DAO for Astrological symbols (extends essentially to recognize "_degree" attr.
 *
 * Created by Cory Higginbottom on 2/21/16
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class AstralSymbolDAO extends AbstractSymbolDAO {

    private AstralSymbolDAO(ILightSymbol symbol) {
        super(symbol);
    }

    public static AstralSymbolDAO fromLightSymbol(ILightSymbol symbol) {
        return new AstralSymbolDAO(symbol);
    }

    public static AstralSymbolDAO fromCursor(RingStringsAppSettings settings, Cursor cursor) throws IOException {
        List<String> astralProjection = new ArrayList<>();
        astralProjection.addAll(Arrays.asList(RingStringsContract.Symbols.PROJECTION_ASTROSYMBOL));
        astralProjection.remove(RingStringsContract.Symbols._DEGREE);
        Map<String, Integer> projMap = new HashMap<>(astralProjection.size());
        for (String elem : astralProjection) {
            projMap.put(elem, cursor.getInt(cursor.getColumnIndex(elem)));
        }

        final SymbolIDBundle bundle = produceIdBundleWithMap(projMap, settings);
        final Double degree = cursor.getDouble(cursor.getColumnIndex(RingStringsContract.Symbols._DEGREE));
        final IAstralSymbol symbol = produceAstralSymbol(degree, bundle);

        ILightSymbol ls = new LightSymbolImpl(bundle, symbol);

        return new AstralSymbolDAO(ls);
    }

    private static IAstralSymbol produceAstralSymbol(Double degree, SymbolIDBundle bundle) {
        AstralStrata aststrata = AstralStrata.values()[bundle.strata_id];

        switch (aststrata) {
            case ASTRALCHART:
                return new AstrologicalChartImpl(Charts.values()[bundle.chart_id], degree);
            case ASTRALHOUSE:
                return new HouseSymbolImpl(Houses.values()[bundle.symbol_id], degree);
            case ASTRALZODIAC:
                return new ZodiacSymbolImpl(Zodiac.values()[bundle.symbol_id], degree);
            case ASTRALASPECT: // TODO: Obtaining aspected symbols.. add symbols after (ie grouped)?
                return new AspectedSymbolsImpl(Aspects.values()[bundle.symbol_id], null, null);
            default:
                CelestialBodies body = CelestialBodies.values()[bundle.symbol_id];
                if (body.isRealCelestialBody())
                    return new CelestialBodySymbolImpl(body, degree);
                else
                    return new FictionalBodySymbolImpl(body, degree);
        }
    }

    @Override
    public ContentValues getContentValues() {
        double degree;
        degree = ((IAstralSymbol)mSymbol.getSymbol()).getAstralSymbolDegree();

        ContentValues contentvalues = super.getContentValues();
        contentvalues.put(RingStringsDbHelper.COL_DEGREE, degree);

        return contentvalues;
    }
}
