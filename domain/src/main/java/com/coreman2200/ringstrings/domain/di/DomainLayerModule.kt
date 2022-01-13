package com.coreman2200.ringstrings.domain.di

import com.coreman2200.ringstrings.domain.DomainLayerContract
import com.coreman2200.ringstrings.domain.SymbolDataRequest
import com.coreman2200.ringstrings.domain.SymbolDataResponse
import com.coreman2200.ringstrings.domain.usecase.FETCH_SYMBOL_DATA_UC_TAG
import com.coreman2200.ringstrings.domain.usecase.FetchSymbolDataUc
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object DomainLayerModule {

    @Provides
    @Named(FETCH_SYMBOL_DATA_UC_TAG)
    fun provideFetchSymbolDataUc(usecase: FetchSymbolDataUc): @JvmSuppressWildcards DomainLayerContract.Presentation.UseCase<SymbolDataRequest, SymbolDataResponse> =
        usecase
}
