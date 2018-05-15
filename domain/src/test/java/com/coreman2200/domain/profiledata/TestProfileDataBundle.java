package com.coreman2200.domain.profiledata;

import com.coreman2200.domain.model.profiles.ProfileDataBundle;
import com.coreman2200.domain.model.profiles.interfaces.IProfileDataBundle;
import com.coreman2200.domain.protos.LocalProfileDataBundle;
import com.coreman2200.domain.protos.RingStringsAppSettings;

import org.junit.Test;

/**
 * TestProfileDataBundle
 * description
 *
 * Created by Cory Higginbottom on 2/19/16
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public class TestProfileDataBundle {
    private IProfileDataBundle mTestProfile;
    private RingStringsAppSettings mAppSettings;

    @Test
    public void testProfileBundleProduction() {
        LocalProfileDataBundle corybundle = MockDefaultDataBundles.testProfileBundleCoryH;
        assert ( corybundle != null );
        LocalProfileDataBundle kaylabundle = MockDefaultDataBundles.testProfileBundleKaylaP;
        assert ( kaylabundle != null );
        LocalProfileDataBundle randombundle = MockDefaultDataBundles.generateRandomProfile();
        assert ( randombundle != null );
        System.out.println(corybundle.toString());
        System.out.println(kaylabundle.toString());
        System.out.println(randombundle.toString());
    }

    @Test
    public void testProfileBirthDateMatchesBundleTimeStamp() {
        LocalProfileDataBundle corybundle = MockDefaultDataBundles.testProfileBundleCoryH;
        assert ( corybundle.birth_placement.timestamp == new ProfileDataBundle(corybundle).getBirthDate().getTimeInMillis() );
        LocalProfileDataBundle kaylabundle = MockDefaultDataBundles.testProfileBundleKaylaP;
        assert ( kaylabundle.birth_placement.timestamp == new ProfileDataBundle(kaylabundle).getBirthDate().getTimeInMillis() );
        LocalProfileDataBundle randombundle = MockDefaultDataBundles.generateRandomProfile();
        assert ( randombundle.birth_placement.timestamp == new ProfileDataBundle(randombundle).getBirthDate().getTimeInMillis() );
    }

    @Test
    public void testProfileAdapterCorrectlyRelatesInterface() {
        LocalProfileDataBundle bundle = MockDefaultDataBundles.testProfileBundleCoryH;
        mTestProfile = new ProfileDataBundle(bundle);

        assert ( mTestProfile.getFirstName() == bundle.full_name.segments.get(0) );
        assert ( mTestProfile.getMiddleName() == bundle.full_name.segments.get(1) );
        assert ( mTestProfile.getLastName() == bundle.full_name.segments.get(2) );
        assert ( mTestProfile.getBirthDate() != null );
        assert ( mTestProfile.getBirthDay() != 0 );
        assert ( mTestProfile.getBirthMonth() != 0 );
        assert ( mTestProfile.getBirthYear() != 0 );
        // assert ( mTestProfile.getCurrentLocation() ); intended to be optional..

    }

    //@Test
    public void testFuckitLetsCheckTheSettings() {
        mAppSettings = MockDefaultDataBundles.produceDefaultAppSettingsBundle();

        assert ( mAppSettings != null );
        System.out.println(mAppSettings.toString());
    }


}