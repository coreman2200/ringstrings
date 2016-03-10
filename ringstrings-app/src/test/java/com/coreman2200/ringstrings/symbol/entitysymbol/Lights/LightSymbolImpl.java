package com.coreman2200.ringstrings.symbol.entitysymbol.Lights;

import com.coreman2200.ringstrings.protos.SymbolDescription;
import com.coreman2200.ringstrings.protos.SymbolIDBundle;
import com.coreman2200.ringstrings.symbol.RelatedSymbolMap;
import com.coreman2200.ringstrings.symbol.entitysymbol.AbstractEntitySymbol;
import com.coreman2200.ringstrings.symbol.entitysymbol.EntityStrata;
import com.coreman2200.ringstrings.symbol.entitysymbol.Tags.TagSymbols;
import com.coreman2200.ringstrings.symbol.inputprocessor.entity.symboldef.ISymbolDef;
import com.coreman2200.ringstrings.symbol.inputprocessor.entity.symboldef.SymbolDefFileHandlerImpl;
import com.coreman2200.ringstrings.symbol.entitysymbol.Tags.IValueSymbol;
import com.coreman2200.ringstrings.symbol.symbolcomparator.ValueSymbolComparatorImpl;
import com.coreman2200.ringstrings.symbol.symbolinterface.ISymbol;

import java.util.ArrayList;
import java.util.Collection;

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

public class LightSymbolImpl extends AbstractEntitySymbol<IValueSymbol> implements ILightSymbol {
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
