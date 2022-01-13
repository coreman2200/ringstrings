package com.coreman2200.ringstrings.domain.input.numerology.numbersystem

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
interface INumberSystem {
    fun numValueForChar(character: Char): Int
    fun size(): Int
}

abstract class NumberSystem protected constructor() : INumberSystem {
    protected var valueMap: MutableMap<Char, Int> = mutableMapOf<Char, Int>()
    protected abstract fun setNumberSystemValues()
    override fun numValueForChar(character: Char): Int {
        return valueMap.getOrDefault(character, 0)
    }

    override fun size(): Int {
        return valueMap.size
    }

    init {
        setNumberSystemValues()
    }
}

class ChaldeanNumberSystem : NumberSystem() {
    override fun setNumberSystemValues() {
        valueMap['a'] = 1
        valueMap['b'] = 2
        valueMap['c'] = 3
        valueMap['d'] = 4
        valueMap['e'] = 5
        valueMap['f'] = 8
        valueMap['g'] = 3
        valueMap['h'] = 5
        valueMap['i'] = 1
        valueMap['j'] = 1
        valueMap['k'] = 2
        valueMap['l'] = 3
        valueMap['m'] = 4
        valueMap['n'] = 5
        valueMap['o'] = 7
        valueMap['p'] = 8
        valueMap['q'] = 1
        valueMap['r'] = 2
        valueMap['s'] = 3
        valueMap['t'] = 4
        valueMap['u'] = 6
        valueMap['v'] = 6
        valueMap['w'] = 7
        valueMap['x'] = 5
        valueMap['y'] = 1
        valueMap['z'] = 7
    }
}

class PythagoreanNumberSystem : NumberSystem() {
    override fun setNumberSystemValues() {
        valueMap['a'] = 1
        valueMap['b'] = 2
        valueMap['c'] = 3
        valueMap['d'] = 4
        valueMap['e'] = 5
        valueMap['f'] = 6
        valueMap['g'] = 7
        valueMap['h'] = 8
        valueMap['i'] = 9
        valueMap['j'] = 1
        valueMap['k'] = 2
        valueMap['l'] = 3
        valueMap['m'] = 4
        valueMap['n'] = 5
        valueMap['o'] = 6
        valueMap['p'] = 7
        valueMap['q'] = 8
        valueMap['r'] = 9
        valueMap['s'] = 1
        valueMap['t'] = 2
        valueMap['u'] = 3
        valueMap['v'] = 4
        valueMap['w'] = 5
        valueMap['x'] = 6
        valueMap['y'] = 7
        valueMap['z'] = 8
    }
}
