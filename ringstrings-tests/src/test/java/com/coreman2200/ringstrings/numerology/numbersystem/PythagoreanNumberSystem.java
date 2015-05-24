package com.coreman2200.ringstrings.numerology.numbersystem;

import java.util.HashMap;

/**
 * PythagoreanNumberSystem
 * One of two actively supported Number Systems for Numerological calculations in this app
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


public class PythagoreanNumberSystem extends AbstractNumberSystem {
    public PythagoreanNumberSystem()
    {
        symbolValueMap = new HashMap<Character, Integer>();
        setNumberSystemValues();
    }

    @Override
    protected void setNumberSystemValues() {
        symbolValueMap.put('a', 1);
        symbolValueMap.put('b', 2);
        symbolValueMap.put('c', 3);
        symbolValueMap.put('d', 4);
        symbolValueMap.put('e', 5);
        symbolValueMap.put('f', 6);
        symbolValueMap.put('g', 7);
        symbolValueMap.put('h', 8);
        symbolValueMap.put('i', 9);
        symbolValueMap.put('j', 1);
        symbolValueMap.put('k', 2);
        symbolValueMap.put('l', 3);
        symbolValueMap.put('m', 4);
        symbolValueMap.put('n', 5);
        symbolValueMap.put('o', 6);
        symbolValueMap.put('p', 7);
        symbolValueMap.put('q', 8);
        symbolValueMap.put('r', 9);
        symbolValueMap.put('s', 1);
        symbolValueMap.put('t', 2);
        symbolValueMap.put('u', 3);
        symbolValueMap.put('v', 4);
        symbolValueMap.put('w', 5);
        symbolValueMap.put('x', 6);
        symbolValueMap.put('y', 7);
        symbolValueMap.put('z', 8);
    }

}
