package com.coreman2200.ringstrings.symbol.entitysymbol.Lights;

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
    private ISymbolDef mSymbolDef;

    public LightSymbolImpl(ISymbol symbol) {
        super(symbol);
        setSymbolComparator(new ValueSymbolComparatorImpl());
        mSymbolDef = SymbolDefFileHandlerImpl.getInstance().produceSymbolDefForSymbol(symbolID());
        if (mSymbolDef != null)
            aggregateTags(mSymbolDef.getQualities());
        produceQualities();

    }

    public String getName() {
        if (mSymbolDef != null)
            return mSymbolDef.getName();
        else
            return mSymbol.name();
    }
    public String getDescription() {
        if (mSymbolDef != null)
            return mSymbolDef.getDescription();
        else
            return "None.";
    }

    protected void setSymbolStrata() { mSymbolStrata = EntityStrata.LIGHT; }

}
