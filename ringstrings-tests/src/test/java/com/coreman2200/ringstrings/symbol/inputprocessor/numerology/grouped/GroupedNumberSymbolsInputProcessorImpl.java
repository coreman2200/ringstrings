package com.coreman2200.ringstrings.symbol.inputprocessor.numerology.grouped;

import com.coreman2200.ringstrings.numerology.numbersystem.AbstractNumberSystem;
import com.coreman2200.ringstrings.numerology.numbersystem.NumberSystemType;
import com.coreman2200.ringstrings.symbol.IProfile;
import com.coreman2200.ringstrings.symbol.inputprocessor.numerology.NumberSymbolInputProcessorImpl;
import com.coreman2200.ringstrings.symbol.numbersymbol.IGroupedNumberSymbols;

/**
 * GroupedNumberSymbolsInputProcessor
 * TODO: Stub.
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

public class GroupedNumberSymbolsInputProcessorImpl extends NumberSymbolInputProcessorImpl implements IGroupedNumberSymbolsInputProcessor {
    protected IProfile userProfile;

    public IGroupedNumberSymbols produceGroupedNumberSymbolsForProfile(IProfile profile) {
        assert (profile != null);
        setProfile(profile);
        return null;
    }

    private void setProfile(IProfile profile) {
        this.userProfile = profile;
        setNumberSystem(profile.getNumberSystem());
    }
}
