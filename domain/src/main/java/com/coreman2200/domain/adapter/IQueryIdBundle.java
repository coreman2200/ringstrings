package com.coreman2200.domain.adapter;

import com.coreman2200.data.entity.protos.SymbolIDBundle;

/**
 * IQueryIdBundle
 * description
 *
 * Created by Cory Higginbottom on 3/10/16
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public interface IQueryIdBundle {
    SymbolIDBundle getIdBundle();
    String getSelection();
    String[] getSelectionArgs();

}
