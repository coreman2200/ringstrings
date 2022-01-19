package com.coreman2200.ringstrings.domain

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.coreman2200.ringstrings.domain.input.entity.IProfileData
import com.coreman2200.ringstrings.domain.swisseph.ISwissEphemerisManager
import com.coreman2200.ringstrings.domain.swisseph.SwissEphemerisManager
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * TestSwissEphemerisManagerImpl
 * Tests for Swisseph manager.
 *
 * Created by Cory Higginbottom on 6/6/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
@RunWith(RobolectricTestRunner::class) //
class TestSwissEphemerisManagerImpl {
    private val context:Context = ApplicationProvider.getApplicationContext<Application>()
    private val settings: AstrologySettings = MockDefaultDataBundles.produceDefaultAppSettingsBundle(context).astro
    private val swisseph: ISwissEphemerisManager = SwissEphemerisManager(settings)
    private val mTestProfile: IProfileData = MockDefaultDataBundles.testProfileBundleCoryH

    @Test
    fun testSwissephManagerProducesAccurateNatalPlacementInHouses() {
        swisseph.produceNatalAstralMappingsForProfile(mTestProfile)
        val houses = swisseph.producedHouseMap()
        assert(!houses.isNullOrEmpty())
    }

    @Test
    fun testSwissephManagerProducesAccurateCurrentPlacementInHouses() {
        swisseph.produceCurrentAstralMappingsForProfile(mTestProfile)
    }
}