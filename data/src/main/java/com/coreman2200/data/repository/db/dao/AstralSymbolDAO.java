package com.coreman2200.data.repository.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.coreman2200.data.adapter.symbolid.ISymbolIdBundleAdapter;
import com.coreman2200.data.adapter.symbolid.SymbolIdBundleAdapter;
import com.coreman2200.data.repository.db.RingStringsContract;
import com.coreman2200.data.repository.db.RingStringsDbHelper;
import com.coreman2200.domain.model.symbols.strata.AstralStrata;
import com.coreman2200.domain.model.symbols.astrals.grouped.Aspects;
import com.coreman2200.domain.model.symbols.astrals.grouped.AstralCharts;
import com.coreman2200.domain.model.symbols.astrals.grouped.CelestialBodies;
import com.coreman2200.domain.model.symbols.astrals.grouped.Houses;
import com.coreman2200.domain.model.symbols.astrals.grouped.Zodiac;
import com.coreman2200.domain.model.symbols.astrals.impl.AspectedSymbolsImpl;
import com.coreman2200.domain.model.symbols.astrals.impl.CelestialBodySymbolImpl;
import com.coreman2200.domain.model.symbols.astrals.impl.FictionalBodySymbolImpl;
import com.coreman2200.domain.model.symbols.astrals.impl.HouseSymbolImpl;
import com.coreman2200.domain.model.symbols.astrals.impl.ZodiacSymbolImpl;
import com.coreman2200.domain.model.symbols.astrals.interfaces.IAstralSymbol;
import com.coreman2200.domain.model.symbols.charts.AstrologicalChartImpl;
import com.coreman2200.domain.model.symbols.interfaces.ILightSymbol;
import com.coreman2200.domain.model.symbols.entities.lights.LightSymbolImpl;

import java.io.IOException;

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

    public static AstralSymbolDAO fromCursor(Cursor cursor) throws IOException {
        final SymbolIdBundleAdapter bundle = SymbolIdBundleAdapter.fromCursor(cursor);
        final Double degree = cursor.getDouble(cursor.getColumnIndex(RingStringsContract.Symbols._DEGREE));
        final IAstralSymbol symbol = produceAstralSymbol(degree, bundle);

        ILightSymbol ls = new LightSymbolImpl(bundle.getIdBundle(), symbol);

        return new AstralSymbolDAO(ls);
    }

    private static IAstralSymbol produceAstralSymbol(Double degree, ISymbolIdBundleAdapter bundle) {
        Enum<? extends Enum<?>> symbolid = bundle.getSymbolId();

        switch ((AstralStrata)bundle.getSymbolType()) {
            case ASTRALCHART:
                return new AstrologicalChartImpl((AstralCharts)symbolid, degree);
            case ASTRALHOUSE:
                return new HouseSymbolImpl((Houses)symbolid, degree);
            case ASTRALZODIAC:
                return new ZodiacSymbolImpl((Zodiac)symbolid, degree);
            case ASTRALASPECT: // TODO: Obtaining aspected symbols.. add symbols after (ie grouped)?
                return new AspectedSymbolsImpl((Aspects)symbolid, null, null);
            default:
                CelestialBodies body = (CelestialBodies)symbolid;
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
