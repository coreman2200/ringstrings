package com.coreman2200.ringstrings.rsprovider.dao;

import android.content.ContentValues;

import com.coreman2200.ringstrings.symbol.symbolinterface.ISymbol;

/**
 * ISymbolDAO
 * Interface for symbol daos
 *
 * Created by Cory Higginbottom on 2/22/16
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public interface ISymbolDAO<T extends ISymbol> {
    T getSymbol();
    ContentValues getContentValues();
    void add() throws Exception;
    void delete() throws Exception;

}
