package com.coreman2200.ringstrings.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * MainExecutorRule
 * description
 *
 * Created by Cory Higginbottom on 2/2/22
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

@ExperimentalCoroutinesApi
class MainCoroutineRule: TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }

    companion object {
        val testDispatcher: TestDispatcher = StandardTestDispatcher()
        val testScope = TestScope(testDispatcher)
    }
}

@ExperimentalCoroutinesApi
fun MainCoroutineRule.runTest(block: suspend () -> Unit) = runTest(MainCoroutineRule.testDispatcher) {
    block()
}