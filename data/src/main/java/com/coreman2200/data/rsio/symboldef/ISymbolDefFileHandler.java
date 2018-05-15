package com.coreman2200.data.rsio.symboldef;

import com.coreman2200.data.rsio.IFileHandler;
import com.coreman2200.domain.model.protos.SymbolDescription;

/**
 * ISymbolDefFileHandler
 * Maps symbols to received symboldef data and produces ISymbolDefs
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

public interface ISymbolDefFileHandler extends IFileHandler {
    SymbolDescription produceSymbolDefForSymbol(Enum<? extends Enum<?>> symbol);

}
