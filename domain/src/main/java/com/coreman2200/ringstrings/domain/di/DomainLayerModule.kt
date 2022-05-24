package com.coreman2200.ringstrings.domain.di

import com.coreman2200.ringstrings.domain.*
import com.coreman2200.ringstrings.domain.usecase.*
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import javax.inject.Named

@Module
object DomainLayerModule {

    @Provides
    @Named(FETCH_SYMBOL_DATA_UC_TAG)
    fun provideFetchSymbolDataUc(usecase: FetchSymbolDataUc): @JvmSuppressWildcards DomainLayerContract.Presentation.FlowUseCase<SymbolDataRequest, List<SymbolVo>> =
        usecase

    @Provides
    @Named(STORE_SYMBOL_DATA_UC_TAG)
    fun provideStoreSymbolDataUc(usecase: StoreSymbolDataUc): @JvmSuppressWildcards DomainLayerContract.Presentation.UseCase<SymbolStoreRequest, Unit> =
        usecase

    @Provides
    @Named(FETCH_SYMBOL_DETAILS_UC_TAG)
    fun provideFetchSymbolDetailsUc(usecase: FetchSymbolDetailsUc): @JvmSuppressWildcards DomainLayerContract.Presentation.UseCase<SymbolDescriptionRequest, SymbolDescriptionResponse> =
        usecase

    @Provides
    @Named(FETCH_PROFILE_DATA_UC_TAG)
    fun provideFetchProfileDataUc(usecase: FetchProfileDataUc): @JvmSuppressWildcards DomainLayerContract.Presentation.FlowUseCase<ProfileDataRequest, List<ProfileData>> =
        usecase

    @Provides
    @Named(QUERY_PROFILE_DATA_UC_TAG)
    fun provideQueryProfilesUc(usecase: QueryProfilesUc): @JvmSuppressWildcards DomainLayerContract.Presentation.FlowUseCase<ProfileDataRequest, List<ProfileData>> =
        usecase

    @Provides
    @Named(STORE_PROFILE_DATA_UC_TAG)
    fun provideStoreProfileDataUc(usecase: StoreProfileDataUc): @JvmSuppressWildcards DomainLayerContract.Presentation.UseCase<ProfileDataRequest, ProfileData> =
        usecase

    @Provides
    @Named(FETCH_SETTINGS_DATA_UC_TAG)
    fun provideFetchSettingsDataUc(usecase: FetchSettingsDataUc): @JvmSuppressWildcards DomainLayerContract.Presentation.UseCase<AppSettingsRequest, AppSettingsResponse> =
        usecase

    @Provides
    @Named(UPDATE_SETTINGS_DATA_UC_TAG)
    fun provideUpdateSettingsDataUc(usecase: UpdateSettingsDataUc): @JvmSuppressWildcards DomainLayerContract.Presentation.UseCase<AppSettingsRequest, AppSettingsResponse> =
        usecase

    @Provides
    @Named(FETCH_SWISSEPH_DATA_UC_TAG)
    fun provideFetchSwissephDataUc(usecase: FetchSwissephDataUc): @JvmSuppressWildcards DomainLayerContract.Presentation.UseCase<SwissephDataRequest, SwissephDataResponse> =
        usecase
}
