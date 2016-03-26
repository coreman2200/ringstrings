package com.coreman2200.data.entity.symbol.lights;

import com.coreman2200.domain.protos.SymbolDescription;
import com.coreman2200.domain.protos.SymbolIDBundle;
import com.coreman2200.domain.symbol.symbolinterface.ILightSymbol;
import com.coreman2200.data.entity.symbol.AbstractEntitySymbol;
import com.coreman2200.domain.symbol.strata.EntityStrata;
import com.coreman2200.domain.symbol.symbolinterface.ISymbol;

/**
 * LightSymbolImpl
 * description
 *
 * Created by Cory Higginbottom on 11/5/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class LightSymbolImpl extends AbstractEntitySymbol<ISymbol> implements ILightSymbol {
    private SymbolDescription mSymbolDef;
    private SymbolIDBundle mIdBundle;

    public LightSymbolImpl(SymbolIDBundle idbundle, ISymbol symbol) {
        super(symbol);
        mIdBundle = idbundle;
        mSymbolDef = getSymbolDefForKey(symbolID());

    }

    @Override
    public ISymbol getSymbol() {
        return mSymbol;
    }

    public String getName() {
        if (mSymbolDef != null)
            return mSymbolDef.name;
        else
            return mSymbol.name();
    }
    public String getDescription() {
        if (mSymbolDef != null)
            return mSymbolDef.description;
        else
            return "";
    }

    public SymbolIDBundle getIdBundle() {
        return mIdBundle;
    }

    protected void setSymbolStrata() { mSymbolStrata = EntityStrata.LIGHT; }

}
