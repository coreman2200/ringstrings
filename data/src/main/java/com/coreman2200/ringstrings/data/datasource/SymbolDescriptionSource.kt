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

import com.coreman2200.ringstrings.data.room_common.dao.ProfileDao
import com.coreman2200.ringstrings.data.room_common.dao.SymbolDescriptionDao
import com.coreman2200.ringstrings.data.room_common.entity.LocationEntity
import com.coreman2200.ringstrings.data.room_common.entity.PlacementEntity
import com.coreman2200.ringstrings.data.room_common.entity.ProfileEntity
import com.coreman2200.ringstrings.data.room_common.entity.SymbolDetailEntity
import com.coreman2200.ringstrings.domain.*
import kotlinx.coroutines.flow.last
import javax.inject.Inject

interface SymbolDescriptionSource {

    companion object {
        const val SYMBOL_DETAIL_SOURCE_TAG = "symbolDetailDataSource"
    }
    suspend fun fetchDescriptionData(request: SymbolDescriptionRequest): SymbolDescriptionResponse
    suspend fun storeSymbolDescription(vararg request: SymbolDescriptionRequest)
}

class SymbolDescriptionDBSource @Inject constructor(val dao: SymbolDescriptionDao) : SymbolDescriptionSource {
    override suspend fun fetchDescriptionData(request: SymbolDescriptionRequest): SymbolDescriptionResponse {
        val desc = dao.getSymbolDescription (request.symbolid).last()
        return SymbolDescriptionResponse( description = desc.toData() )
    }

    override suspend fun storeSymbolDescription(vararg request: SymbolDescriptionRequest) {
        val args = request.map { it.toEntity() }.toTypedArray()
        dao.insertAll(*args)
    }

    private fun SymbolDetailEntity.toData() : SymbolDescription = SymbolDescription(
        id = id,
        description = description,
        qualities = qualities

    )

    private fun SymbolDescriptionRequest.toEntity() : SymbolDetailEntity = SymbolDetailEntity(
        id = symbolid,
        description = description,
        qualities = qualities
        )

}