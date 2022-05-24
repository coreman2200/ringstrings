/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.coreman2200.ringstrings.domain.usecase

import com.coreman2200.ringstrings.domain.*
import com.coreman2200.ringstrings.domain.DomainLayerContract.Data.Companion.SYMBOL_REPOSITORY_TAG
import com.coreman2200.ringstrings.domain.util.Failure
import com.coreman2200.ringstrings.domain.util.Outcome
import javax.inject.Inject
import javax.inject.Named

const val STORE_SYMBOL_DATA_UC_TAG = "storeSymbolDataUc"

class StoreSymbolDataUc @Inject constructor(
    @Named(SYMBOL_REPOSITORY_TAG)
    private val symbolDataRepository: @JvmSuppressWildcards DomainLayerContract.Data.SymbolDataRepository<Unit>
) : DomainLayerContract.Presentation.UseCase<SymbolStoreRequest, Unit> {

    override suspend fun run(params: SymbolStoreRequest?): Outcome<Unit> =
        params?.let {
            Outcome.Success(symbolDataRepository.storeSymbol(request = it))
        } ?: run {
            Outcome.Error(Failure.InputParamsError())
        }
}
