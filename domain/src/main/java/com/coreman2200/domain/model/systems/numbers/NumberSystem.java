package com.coreman2200.domain.model.systems.numbers;

import com.coreman2200.domain.model.systems.numbers.interfaces.INumberSystem;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * NumberSystem
 * Abstract class class for number system, an object that represents the system we use to convert
 * characters in a name/string into numerical values.
 *
 * Created by Cory Higginbottom on 5/23/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

abstract public class NumberSystem implements INumberSystem {
    protected HashMap<Character, Integer> symbolValueMap;
    private NumberSystemType mNumberSystemType;

    protected NumberSystem(NumberSystemType type) {
        symbolValueMap = new HashMap<Character, Integer>();
        mNumberSystemType = type;
        setNumberSystemValues();
    }

    abstract protected void setNumberSystemValues();

    public int numValueForChar(char character) {
        return (symbolValueMap.containsKey(character)) ? symbolValueMap.get(character) : 0;
    }

    public int size() throws NullPointerException {
        if (symbolValueMap == null)
            throw new NullPointerException("Symbol map not yet defined");

        return symbolValueMap.size();
    }

    static public INumberSystem createNumberSystemWithType(NumberSystemType type) {
        switch (type) {
            case CHALDEAN:
                return new ChaldeanNumberSystem();
            case PYTHAGOREAN:
                return new PythagoreanNumberSystem();
            default:
                throw new NoSuchElementException("No such Number System found for " + type.name());
        }
    }
}
