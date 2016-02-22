package com.coreman2200.ringstrings.symbol.entitysymbol;

import com.coreman2200.ringstrings.symbol.AbstractSymbol;
import com.coreman2200.ringstrings.symbol.RelatedSymbolMap;
import com.coreman2200.ringstrings.symbol.SymbolStrata;
import com.coreman2200.ringstrings.symbol.entitysymbol.Tags.ITagSymbol;
import com.coreman2200.ringstrings.symbol.entitysymbol.Tags.TagSymbols;
import com.coreman2200.ringstrings.symbol.inputprocessor.entity.symboldef.ISymbolDef;
import com.coreman2200.ringstrings.symbol.inputprocessor.entity.symboldef.ISymbolDefFileHandler;
import com.coreman2200.ringstrings.symbol.inputprocessor.entity.symboldef.SymbolDefFileHandlerImpl;
import com.coreman2200.ringstrings.symbol.entitysymbol.Tags.ValueSymbolImpl;
import com.coreman2200.ringstrings.symbol.entitysymbol.Tags.IValueSymbol;
import com.coreman2200.ringstrings.symbol.symbolinterface.IEntitySymbol;
import com.coreman2200.ringstrings.symbol.symbolinterface.ISymbol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * AbstractEntitySymbol
 * The Entity Symbol expresses symbols/symbol groupings per their relationships and respective
 * "Quality", or rather, an aggregated and comparator-ready grouping of Tags
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

abstract public class AbstractEntitySymbol<T extends ISymbol> extends AbstractSymbol<T> implements IEntitySymbol {

    protected ISymbol mSymbol;

    protected AbstractEntitySymbol(ISymbol symbol) {
        super(symbol.symbolID());
        mSymbol = symbol;
    }

    protected final void produceQualities() {

        Set<Map.Entry<Enum<? extends Enum<?>>, T>> symbols = getAllEntriesForSymbol(mSymbol);


        for (Map.Entry<Enum<? extends Enum<?>>, T> entry : symbols) {
            T entrySymbol = entry.getValue();
            ISymbolDef symboldef = getSymbolDefForKey(entrySymbol.symbolID());
            if ((entrySymbol.symbolStrata().compareTo(SymbolStrata.RELATED_SYMBOLS) <= 0) && symboldef != null) {
                aggregateTags(symboldef.getQualities());
            }

            Set<Map.Entry<Enum<? extends Enum<?>>, T>> subsymbols = getAllEntriesForSymbol(entry.getValue());
            for (T ss : getFilteredSymbolListFromSet(subsymbols)) {
                ISymbolDef sd = getSymbolDefForKey(ss.symbolID());
                if (sd != null) {
                    aggregateTags(sd.getQualities());
                }
            }

        }
        System.out.println("Total Unique Quality: " + symbolIDCollection().size() + "(vs " + TagSymbols.values().length + " overall)");
        System.out.println("Total Symbols Aggregated: " + symbols.size());

    }

    private final Set<Map.Entry<Enum<? extends Enum<?>>, T>> getAllEntriesForSymbol(ISymbol symbol) {
        return symbol.produceSymbol();
    }

    private final ISymbolDef getSymbolDefForKey(Enum<? extends Enum<?>> id) {
        ISymbolDefFileHandler handler = SymbolDefFileHandlerImpl.getInstance();
        return handler.produceSymbolDefForSymbol(id);
    }

    private final Collection<T> getFilteredSymbolListFromSet(Set<Map.Entry<Enum<? extends Enum<?>>, T>> set) {
        Collection<T> list = new ArrayList<>();
        for (Map.Entry<Enum<? extends Enum<?>>, T> elem : set) {
            if (elem.getValue().symbolStrata().compareTo(SymbolStrata.RELATED_SYMBOLS) <= 0)
                list.add(elem.getValue());
        }
        return list;
    }

    protected final void aggregateTags(Collection<Enum<? extends Enum<?>>> tags) {
        for (Enum<? extends Enum<?>> tag : tags)
            addQuality(tag, 1);
    }

    protected final void aggregateTags(Map<Enum<? extends Enum<?>>, IValueSymbol> qualities) {
        for (Enum<? extends Enum<?>> tag : qualities.keySet()) {
            addQuality(tag, qualities.get(tag).getValue());
        }
    }

    private final void addQuality(Enum<? extends Enum<?>> tag, Integer count) {
        IValueSymbol tagCount;

        try {
            tagCount = (IValueSymbol)getSymbolDataForKey(tag);
            tagCount.add(count);
            updateSymbolDataForKey(tag, (T) tagCount);

        } catch (NullPointerException e) {
            tagCount = new ValueSymbolImpl(count);
            addSymbolDataForKey(tag, (T) tagCount);
        }

    }

    public final Collection<Enum<? extends Enum<?>>> getQualities() {
        return getSortedSymbolIDs(RelatedSymbolMap.SortOrder.DESCENDING);

    }


}
