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
interface SymbolDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(symbol: SymbolEntity)

    @Update
    fun update(symbol: SymbolEntity)

    @Delete
    fun delete(symbol: SymbolEntity)

    @Query("SELECT * FROM profile_symbol_data_table WHERE profileid IS :p AND chartid IS :c")
    fun getSymbols(
        symbol: SymbolEntity,
        p:Int = symbol.profileid,
        c:String = symbol.chartid,
    ): Flow<List<SymbolEntity>>

    @Query("SELECT * FROM profile_symbol_data_table WHERE profileid IS :p AND chartid IS :c AND groupid IS :g")
    fun getSymbolsByGroup(
        symbol: SymbolEntity,
        p:Int = symbol.profileid,
        c:String = symbol.chartid,
        g:String = symbol.groupid
    ): Flow<List<SymbolEntity>>

    @Query("SELECT * FROM symbol_description_table WHERE id = :symbolid LIMIT 1")
    fun getSymbolDescription(symbolid:String): Flow<SymbolDetailEntity>
}