package com.coreman2200.ringstrings.data.room_common.dao

import androidx.room.*
import com.coreman2200.ringstrings.data.room_common.entity.SymbolDetailEntity
import com.coreman2200.ringstrings.data.room_common.entity.SymbolEntity
import kotlinx.coroutines.flow.Flow

/**
 * SymbolDao
 * description
 *
 * Created by Cory Higginbottom on 1/13/22
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

@Dao
interface SymbolDescriptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(symbol: SymbolDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(symbol: List<SymbolDetailEntity>)

    @Update
    suspend fun update(symbol: SymbolDetailEntity)

    @Delete
    suspend fun delete(symbol: SymbolDetailEntity)

    @Query("SELECT * FROM symbol_description_table WHERE id = :symbolid LIMIT 1")
    fun getSymbolDescription(symbolid:String): Flow<SymbolDetailEntity>
}