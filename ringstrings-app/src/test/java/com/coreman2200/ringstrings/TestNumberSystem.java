package com.coreman2200.ringstrings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import com.coreman2200.ringstrings.numbersystem.NumberSystem;
import com.coreman2200.ringstrings.numbersystem.INumberSystem;
import com.coreman2200.ringstrings.numbersystem.NumberSystemType;

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

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class TestNumberSystem {
    INumberSystem mNumberSystem;

    @Before
    public void setup() {
        mNumberSystem = NumberSystem.createNumberSystemWithType(NumberSystemType.PYTHAGOREAN);
    }

    @Test
    public void testNumberSystemIsFilledForAllLetters() {
        final String characters = "abcdefghijklmnopqrstuvwxyz";
        int counter = 0;

        for (char c : characters.toCharArray()) {
            assert(mNumberSystem.numValueForChar(c) !=  0);
            counter++;
        }

        assert(mNumberSystem.size() == characters.length());
    }

    @Test
    public void testNumberSystemIsOKWhenPresentedNonMembers() {
        final String characters = "abcdefg@hijklmnopqr%stuv)wxyz";
        int counter = 0;

        for (char c : characters.toCharArray()) {
            counter++;
        }

        assert(mNumberSystem.size() == characters.length() - 3);
    }

}
