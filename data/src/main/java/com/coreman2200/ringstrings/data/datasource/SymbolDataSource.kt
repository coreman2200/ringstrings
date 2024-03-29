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

import com.coreman2200.ringstrings.data.room_common.dao.SymbolDao
import com.coreman2200.ringstrings.data.room_common.entity.*
import com.coreman2200.ringstrings.domain.*
import com.coreman2200.ringstrings.domain.symbol.entitysymbol.EntityStrata
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface SymbolDataSource {

    companion object {
        const val SYMBOL_DATA_SOURCE_TAG = "symbolDataSource"
    }

    suspend fun fetchSymbolData(request: SymbolDataRequest): SymbolDataResponse

    suspend fun storeSymbolData(request: SymbolStoreRequest)
}

class SymbolDatabaseSource @Inject constructor(val dao: SymbolDao) : SymbolDataSource {
    override suspend fun fetchSymbolData(request: SymbolDataRequest): SymbolDataResponse {
        val symbols: Flow<List<SymbolAndDetails>>
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
            data.strata == EntityStrata.SOCIAL.toString() -> { // todo how else to indicate..
                val list = mutableListOf(*data.children.map { it.toInt() }.toTypedArray())
                list.add(data.profileid) // include grouping
                symbols = dao.getSymbolsInProfiles(
                    list
                )
            }
            else -> {

                symbols = dao.getSymbolsInProfile(
                    data.profileid
                )
            }
        }

        return SymbolDataResponse(symbols = symbols.map { it.toData() })
    }

    private fun SymbolAndDetails.toData(): SymbolData = SymbolData(
        instanceid = symbol.instanceid,
        profileid = symbol.profileid,
        chartid = symbol.chartid,
        groupid = symbol.groupid,
        symbolid = symbol.symbolid,
        strata = symbol.strata,
        name = symbol.name,
        type = symbol.type,
        value = symbol.value,
        flag = symbol.flag,
        relations = symbol.relations,
        children = symbol.children,
        details = description?.toData()
    )

    private fun List<SymbolAndDetails>.toData(): List<SymbolData> = this.map { it.toData() }

    private fun SymbolData.toEntity(): SymbolEntity = SymbolEntity(
        instanceid = this.instanceid,
        profileid = this.profileid,
        chartid = this.chartid,
        groupid = this.groupid,
        symbolid = this.symbolid,
        strata = this.strata,
        name = this.name,
        type = this.type,
        value = this.value,
        flag = this.flag,
        children = this.children,
        relations = this.relations
    )

    override suspend fun storeSymbolData(request: SymbolStoreRequest) {
        val args = request.data.map { it.toEntity() }.toTypedArray()
        dao.insertAll(*args)
    }
}
