package com.coreman2200.ringstrings.numerology.numbersystem;

import java.util.HashMap;

/**
 * AbstractNumberSystem
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

abstract public class AbstractNumberSystem implements INumberSystem {
    protected HashMap<Character, Integer> symbolValueMap;
    static private INumberSystem numberSystem;
    static private NumberSystemType numberSystemType;

    protected AbstractNumberSystem() {
        symbolValueMap = new HashMap<Character, Integer>();
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
        if (numberSystem != null && numberSystemType == type)
            return numberSystem;
        else {
            numberSystem = (type.equals(NumberSystemType.PYTHAGOREAN)) ? new PythagoreanNumberSystem() : new ChaldeanNumberSystem();
            numberSystemType = type;
        }
        return numberSystem;
    }
}
