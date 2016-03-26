package com.coreman2200.data.processor;


import com.coreman2200.domain.protos.RingStringsAppSettings;

/**
 * AbstractInputProcessor
 * Processes input data into Symbols (abstract)
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

abstract public class AbstractInputProcessor {
    protected RingStringsAppSettings mAppSettings;

    protected AbstractInputProcessor(RingStringsAppSettings settings) {
        mAppSettings = settings;
    }
}
