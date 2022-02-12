package com.coreman2200.ringstrings.data.room_common.dao

import androidx.room.*
import com.coreman2200.ringstrings.data.room_common.entity.SymbolAndDetails
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
    suspend fun insert(symbol: SymbolEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(symbol: List<SymbolEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg symbol: SymbolEntity)

    @Update
    suspend fun update(symbol: SymbolEntity)

    @Delete
    suspend fun delete(symbol: SymbolEntity)

    @Transaction
    @Query("SELECT * FROM profile_symbol_data_table WHERE profileid IN (:ids) ORDER BY type ASC")
    fun getSymbolsInProfiles(
        ids:List<Int>,
    ): Flow<List<SymbolAndDetails>>

    @Transaction
    @Query("SELECT * FROM profile_symbol_data_table WHERE profileid IS :profileid ORDER BY type ASC")
    fun getSymbolsInProfile(
        profileid: Int,
    ): Flow<List<SymbolAndDetails>>

    @Transaction
    @Query("SELECT * FROM profile_symbol_data_table WHERE profileid IS :p AND chartid IS :c ORDER BY type ASC")
    fun getSymbolsInProfileForChart(
        p:Int,
        c:String
    ): Flow<List<SymbolAndDetails>>

    @Transaction
    @Query("SELECT * FROM profile_symbol_data_table WHERE profileid IS :p AND chartid IS :c AND groupid IS :g ORDER BY type ASC")
    fun getSymbolsInProfileChartForGroup(
        p:Int,
        c:String,
        g:String
    ): Flow<List<SymbolAndDetails>>

    @Transaction
    @Query("SELECT * FROM profile_symbol_data_table WHERE profileid IS :p AND chartid IS :c AND symbolid IS :s LIMIT 1")
    fun getSymbolInProfileChartNamed(
        p:Int,
        c:String,
        s:String
    ): Flow<List<SymbolAndDetails>>
}