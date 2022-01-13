package com.coreman2200.ringstrings.data.di

import com.coreman2200.ringstrings.data.datasource.ProfileDataSource
import com.coreman2200.ringstrings.data.datasource.ProfileDataSource.Companion.PROFILE_DATA_SOURCE_TAG
import com.coreman2200.ringstrings.data.datasource.SettingsDataSource
import com.coreman2200.ringstrings.data.datasource.SettingsDataSource.Companion.SETTINGS_DATA_SOURCE_TAG
import com.coreman2200.ringstrings.data.datasource.SwissephDataSource
import com.coreman2200.ringstrings.data.datasource.SwissephDataSource.Companion.SWISSEPH_DATA_SOURCE_TAG
import com.coreman2200.ringstrings.data.datasource.SymbolDataSource
import com.coreman2200.ringstrings.data.datasource.SymbolDataSource.Companion.SYMBOL_DATA_SOURCE_TAG
import com.coreman2200.ringstrings.data.repository.ProfileDataRepository
import com.coreman2200.ringstrings.data.repository.SettingsDataRepository
import com.coreman2200.ringstrings.data.repository.SwissephDataRepository
import com.coreman2200.ringstrings.data.repository.SymbolDataRepository
import com.coreman2200.ringstrings.domain.*
import com.coreman2200.ringstrings.domain.DomainLayerContract.Data.Companion.EPHEMERIS_REPOSITORY_TAG
import com.coreman2200.ringstrings.domain.DomainLayerContract.Data.Companion.PROFILE_REPOSITORY_TAG
import com.coreman2200.ringstrings.domain.DomainLayerContract.Data.Companion.SETTINGS_REPOSITORY_TAG
import com.coreman2200.ringstrings.domain.DomainLayerContract.Data.Companion.SYMBOL_REPOSITORY_TAG
import dagger.Module
import dagger.Provides
import javax.inject.Named

private const val TIMEOUT = 10L

@Module
object RepositoryModule {

    @Provides
    @Named(SYMBOL_REPOSITORY_TAG)
    fun provideSymbolDataRepository(
        @Named(SYMBOL_DATA_SOURCE_TAG)
        symbolDs: SymbolDataSource
    ): @JvmSuppressWildcards DomainLayerContract.Data.SymbolDataRepository<SymbolDataResponse> =
        SymbolDataRepository.apply { symbolDataSource = symbolDs }

    @Provides
    @Named(PROFILE_REPOSITORY_TAG)
    fun provideProfileDataRepository(
        @Named(PROFILE_DATA_SOURCE_TAG)
        profileDs: ProfileDataSource,
    ): @JvmSuppressWildcards DomainLayerContract.Data.ProfileDataRepository<ProfileDataResponse> =
        ProfileDataRepository.apply { profileDataSource = profileDs }

    @Provides
    @Named(SETTINGS_REPOSITORY_TAG)
    fun provideSettingsDataRepository(
        @Named(SETTINGS_DATA_SOURCE_TAG)
        settingsDs: SettingsDataSource,
    ): @JvmSuppressWildcards DomainLayerContract.Data.SettingsDataRepository<AppSettingsResponse> =
        SettingsDataRepository.apply { settingsDataSource = settingsDs }

    @Provides
    @Named(EPHEMERIS_REPOSITORY_TAG)
    fun provideSwissephDataRepository(
        swissephDs: SwissephDataSource
    ): @JvmSuppressWildcards DomainLayerContract.Data.SwissephDataRepository<SwissephDataResponse> =
        SwissephDataRepository.apply { swissephDataSource = swissephDs }

}

@Module
class DataSourceModule {

    @Provides
    @Named(PROFILE_DATA_SOURCE_TAG)
    fun provideProfileDataSource(ds: ProfileDataSource): ProfileDataSource = ds

    @Provides
    @Named(SETTINGS_DATA_SOURCE_TAG)
    fun provideSettingsDataSource(ds: SettingsDataSource): SettingsDataSource = ds

    @Provides
    @Named(SWISSEPH_DATA_SOURCE_TAG)
    fun provideSwissephDataSource(ds: SwissephDataSource): SwissephDataSource = ds

    @Provides
    @Named(SYMBOL_DATA_SOURCE_TAG)
    fun provideSymbolDataSource(ds: SymbolDataSource): SymbolDataSource = ds

}