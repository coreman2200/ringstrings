package com.coreman2200.presentation.rsdisplay.interactors.numerology.grouped;

import com.coreman2200.presentation.rsdisplay.interactors.numerology.INumberSymbolInteractor;
import com.coreman2200.domain.model.profiles.interfaces.IProfileDataBundle;
import com.coreman2200.domain.model.symbols.numbers.interfaces.IGroupedNumberSymbols;

/**
 * IGroupedNumberSymbolsInteractor
 * Interface for processing Grouped Number Symbols.
 *
 * Created by Cory Higginbottom on 5/26/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public interface IGroupedNumberSymbolsInteractor extends INumberSymbolInteractor {
    IGroupedNumberSymbols produceGroupedNumberSymbolsForProfile(IProfileDataBundle profile);
}
