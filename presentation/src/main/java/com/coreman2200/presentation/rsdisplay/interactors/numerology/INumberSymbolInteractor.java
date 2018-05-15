package com.coreman2200.presentation.rsdisplay.interactors.numerology;

import com.coreman2200.domain.model.symbols.numbers.interfaces.INumberSymbol;

/**
 * INumberSymbolInteractor
 * Interface for number symbol input processors
 *
 * Created by Cory Higginbottom on 5/25/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public interface INumberSymbolInteractor {
    INumberSymbol convertTextStringToNumberSymbol(String text);
    INumberSymbol convertValueToNumberSymbol(int value);

}
