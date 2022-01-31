package com.coreman2200.ringstrings.domain

import org.robolectric.RobolectricTestRunner
import com.coreman2200.ringstrings.domain.input.numerology.numbersystem.INumberSystem
import com.coreman2200.ringstrings.domain.input.numerology.numbersystem.NumberSystemType
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

//import static org.assertj.android.api.Assertions.assertThat;
/**
 * ChaldeanNumberSystemTest
 * Test for number system functionality..
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
@RunWith(RobolectricTestRunner::class)
class TestNumberSystem {
    var mNumberSystem: INumberSystem = NumberSystemType.PYTHAGOREAN.numberSystem()

    /*@Before
    fun setup() {
        nNumberSystem = NumberSystemType.CHALDEAN.numberSystem()
    }*/

    @Test
    fun testNumberSystemIsFilledForAllLetters() {
        val characters = "abcdefghijklmnopqrstuvwxyz"
        assert(mNumberSystem.size() == characters.length)
    }

    @Test
    fun testNumberSystemIsOKWhenPresentedNonMembers() {
        val characters = "abcdefg@hijklmnopqr%stuv)wxyz"
        assert(mNumberSystem.size() == characters.length - 3)
    }
}