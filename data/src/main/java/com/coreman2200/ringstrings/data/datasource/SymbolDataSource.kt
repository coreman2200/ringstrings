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

import com.coreman2200.ringstrings.data.room_common.RSDatabase
import com.coreman2200.ringstrings.data.room_common.dao.SymbolDao
import com.coreman2200.ringstrings.data.room_common.entity.SymbolDetailEntity
import com.coreman2200.ringstrings.data.room_common.entity.SymbolEntity
import com.coreman2200.ringstrings.domain.*
import com.squareup.wire.internal.newMutableList
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.random.Random

interface SymbolDataSource {

    companion object {
        const val SYMBOL_DATA_SOURCE_TAG = "symbolDataSource"
    }

    suspend fun fetchSymbolData(request: SymbolDataRequest): SymbolDataResponse

    suspend fun storeSymbolData(request: SymbolStoreRequest)
}

class SymbolDatabaseSource @Inject constructor(val dao:SymbolDao) : SymbolDataSource {
    override suspend fun fetchSymbolData(request: SymbolDataRequest): SymbolDataResponse {
        val symbols: Flow<List<SymbolEntity>>
        val data = request.data
        when {
            data.symbolid.isNotEmpty() -> {
                symbols = dao.getSymbolInProfileChartNamed(
                    data.profileid,
                    data.chartid,
                    data.symbolid
                )
            }
            data.groupid.isNotEmpty() -> {
                symbols = dao.getSymbolsInProfileChartForGroup(
                    data.profileid,
                    data.chartid,
                    data.groupid
                )
            }
            data.chartid.isNotEmpty() -> {
                symbols = dao.getSymbolsInProfileForChart(
                    data.profileid,
                    data.chartid
                )
            }
            else -> {

                symbols = dao.getSymbolsInProfile(
                    data.profileid
                )
            }
        }

        val elems = symbols.first()
        return SymbolDataResponse(symbols = elems.map { it.toData() })
    }

    private fun SymbolEntity.toData() : SymbolData = SymbolData(
        instanceid = this.instanceid,
        profileid = this.profileid,
        chartid = this.chartid,
        groupid = this.groupid,
        symbolid = this.symbolid,
        strata = this.strata,
        type = this.type,
        value = this.value,
        relations = this.relations,
        description = SymbolDescription(id = symbolid)
    )

    private fun SymbolData.toEntity() : SymbolEntity = SymbolEntity(
        instanceid = this.instanceid,
        profileid = this.profileid,
        chartid = this.chartid,
        groupid = this.groupid,
        symbolid = this.symbolid,
        strata = this.strata,
        type = this.type,
        value = this.value,
        relations = this.relations,
    )

    override suspend fun storeSymbolData(request: SymbolStoreRequest) {
        val args = request.data.map { it.toEntity() }.toTypedArray()
        dao.insertAll(*args)
    }

}
