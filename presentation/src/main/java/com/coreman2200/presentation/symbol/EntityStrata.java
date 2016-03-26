package com.coreman2200.presentation.symbol;

import com.coreman2200.domain.symbol.symbolinterface.ISymbolStrata;

/**
 * EntityStrata
 * Enums Entity Symbols
 *
 * Created by Cory Higginbottom on 6/10/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public enum EntityStrata implements ISymbolStrata {
    TAG, // TODO: New description of Symbol or Entity do not include TAG...
    LIGHT,
    RING,
    PROFILE,
    USER,
    SOCIAL,
    GROUPED,
    ALL,
    GLOBAL;

    public static final boolean isEntitySymbol(Enum<? extends Enum<?>> type) {
        return type instanceof EntityStrata;
    }

    @Override
    public Enum[] getGrouping() {
        return values();
    }
}
