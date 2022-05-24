package com.coreman2200.ringstrings.domain.usecase

import com.coreman2200.ringstrings.domain.DomainLayerContract
import com.coreman2200.ringstrings.domain.ProfileDataRequest
import com.coreman2200.ringstrings.domain.ProfileDataResponse
import javax.inject.Inject
import javax.inject.Named

/**
 * ProfileUseCases
 * description
 *
 * Created by Cory Higginbottom on 5/23/22
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

interface IProfileUseCases {
    fun fetchProfileData(params:ProfileDataRequest?):FetchProfileDataUc

    fun queryProfiles(params:ProfileDataRequest?):QueryProfilesUc

    fun storeProfileData(params:ProfileDataRequest?):StoreProfileDataUc

    //fun removeProfileData(params:Any?):FetchProfileDataUc
}

class ProfileUseCases @Inject constructor(
    @Named(DomainLayerContract.Data.PROFILE_REPOSITORY_TAG)
    private val profileDataRepository: @JvmSuppressWildcards DomainLayerContract.Data.ProfileDataRepository<ProfileDataResponse>
) : IProfileUseCases {
    override fun fetchProfileData(params: ProfileDataRequest?): FetchProfileDataUc =
        FetchProfileDataUc(profileDataRepository)


    override fun queryProfiles(params: ProfileDataRequest?): QueryProfilesUc =
        QueryProfilesUc(profileDataRepository)

    override fun storeProfileData(params: ProfileDataRequest?): StoreProfileDataUc =
        StoreProfileDataUc(profileDataRepository)

}