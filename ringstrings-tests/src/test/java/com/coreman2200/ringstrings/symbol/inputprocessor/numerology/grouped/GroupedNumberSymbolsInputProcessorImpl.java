package com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped;

import com.coreman2200.ringstrings.profile.IProfile;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.NumberSymbolInputProcessorImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.IGroupedNumberSymbols;

/**
 * GroupedNumberSymbolsInputProcessor
 * Base class for grouped number symbol input processors used to produce a chart.
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

abstract public class GroupedNumberSymbolsInputProcessorImpl extends NumberSymbolInputProcessorImpl implements IGroupedNumberSymbolsInputProcessor {
    protected IProfile userProfile;

    protected GroupedNumberSymbolsInputProcessorImpl() {
        super();
    }

    public void setUserProfileAndNumberSystem(IProfile profile) {
        assert (profile != null);
        userProfile = profile;
        setNumberSystem(userProfile.getNumberSystem());
    }

    abstract public IGroupedNumberSymbols produceGroupedNumberSymbolsForProfile();
}
