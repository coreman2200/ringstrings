package com.coreman2200.domain.symbol.entitysymbol.Lights;

import com.coreman2200.data.entity.protos.SymbolIDBundle;
import com.coreman2200.domain.symbol.inputprocessor.entity.symboldef.ISymbolDef;
import com.coreman2200.domain.symbol.symbolinterface.IEntitySymbol;
import com.coreman2200.domain.symbol.symbolinterface.ISymbol;

/**
 * ILightSymbol
 * Light Entity Symbol interface..
 *
 * Created by Cory Higginbottom on 11/3/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public interface ILightSymbol extends IEntitySymbol, ISymbolDef {
    SymbolIDBundle getIdBundle();
    ISymbol getSymbol();
}
