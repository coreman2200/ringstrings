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
package com.coreman2200.ringstrings.data.datasource

import com.coreman2200.ringstrings.domain.SymbolDataRequest
import com.coreman2200.ringstrings.domain.SymbolDataResponse
import com.coreman2200.ringstrings.domain.SymbolDescriptionRequest

interface SymbolDataSource {

    companion object {
        const val SYMBOL_DATA_SOURCE_TAG = "symbolDataSource"
    }

    suspend fun fetchSymbolData(request: SymbolDataRequest): SymbolDataResponse
    suspend fun fetchDescriptionData(request: SymbolDescriptionRequest): SymbolDataResponse

    // suspend fun storeSymbolData(request: SymbolDataRequest): SymbolDataResponse
}
