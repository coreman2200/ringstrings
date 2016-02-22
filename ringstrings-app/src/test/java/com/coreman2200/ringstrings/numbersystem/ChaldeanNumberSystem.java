package com.coreman2200.ringstrings.numbersystem;

/**
 * ChaldeanNumberSystem
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


public class ChaldeanNumberSystem extends NumberSystem {

    public ChaldeanNumberSystem() {
        super(NumberSystemType.CHALDEAN);
    }

    @Override
    protected void setNumberSystemValues() {
        symbolValueMap.put('a', 1);
        symbolValueMap.put('b', 2);
        symbolValueMap.put('c', 3);
        symbolValueMap.put('d', 4);
        symbolValueMap.put('e', 5);
        symbolValueMap.put('f', 8);
        symbolValueMap.put('g', 3);
        symbolValueMap.put('h', 5);
        symbolValueMap.put('i', 1);
        symbolValueMap.put('j', 1);
        symbolValueMap.put('k', 2);
        symbolValueMap.put('l', 3);
        symbolValueMap.put('m', 4);
        symbolValueMap.put('n', 5);
        symbolValueMap.put('o', 7);
        symbolValueMap.put('p', 8);
        symbolValueMap.put('q', 1);
        symbolValueMap.put('r', 2);
        symbolValueMap.put('s', 3);
        symbolValueMap.put('t', 4);
        symbolValueMap.put('u', 6);
        symbolValueMap.put('v', 6);
        symbolValueMap.put('w', 7);
        symbolValueMap.put('x', 5);
        symbolValueMap.put('y', 1);
        symbolValueMap.put('z', 7);
    }
}
