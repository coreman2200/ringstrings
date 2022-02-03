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
package com.coreman2200.ringstrings.domain

import arrow.core.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

interface DomainLayerContract {

    interface Presentation {

        interface UseCase<in T, out S> {

            fun invoke(scope: CoroutineScope, params: T?, onResult: (Either<Failure, S>) -> Unit) {
                // task undertaken in a worker thread
                val job = scope.async { run(params) }
                // once completed, result sent in the Main thread
                scope.launch(Dispatchers.Main) { onResult(job.await()) }
            }

            suspend fun run(params: T? = null): Either<Failure, S>
        }
    }

    interface Data {

        companion object {
            const val SYMBOL_REPOSITORY_TAG = "symbolRepository"
            const val SYMBOL_DETAIL_REPOSITORY_TAG = "symbolDetailRepository"
            const val SETTINGS_REPOSITORY_TAG = "settingsRepository"
            const val PROFILE_REPOSITORY_TAG = "profileRepository"
            const val EPHEMERIS_REPOSITORY_TAG = "swissephRepository"
        }

        interface SymbolDataRepository<out S> {
            suspend fun fetchSymbol(request: SymbolDataRequest): Either<Failure, S>
            suspend fun storeSymbol(request: SymbolStoreRequest)
        }

        interface SymbolDetailRepository<out S> {
            suspend fun fetchSymbolDescription(request: SymbolDescriptionRequest): Either<Failure, S>
            suspend fun storeSymbolDescription(vararg request: SymbolDescriptionRequest)

        }

        interface SettingsDataRepository<out S> {
            suspend fun fetchAppSettings(request: AppSettingsRequest): Either<Failure, S>
            suspend fun storeAppSettings(request: AppSettingsRequest): Either<Failure, S>
        }

        interface ProfileDataRepository<out S> {
            suspend fun fetchProfile(request: ProfileDataRequest): Either<Failure, S>
            suspend fun storeProfile(request: ProfileDataRequest): Either<Failure, S>

        }

        interface SwissephDataRepository<out S> {
            suspend fun fetchSwisseph(request: SwissephDataRequest): Either<Failure, S>

        }
    }
}
