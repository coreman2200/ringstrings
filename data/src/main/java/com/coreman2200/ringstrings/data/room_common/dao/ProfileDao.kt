package com.coreman2200.ringstrings.data.room_common.dao

import androidx.room.*
import com.coreman2200.ringstrings.data.room_common.entity.ProfileEntity
import com.coreman2200.ringstrings.data.room_common.entity.SymbolAndDetails
import com.coreman2200.ringstrings.data.room_common.entity.SymbolEntity
import kotlinx.coroutines.flow.Flow

/**
 * ProfileDao
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
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: ProfileEntity):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(symbol: List<ProfileEntity>)

    @Update
    suspend fun update(profile: ProfileEntity)

    @Delete
    fun delete(profile: ProfileEntity)

    @Query("SELECT * FROM user_profile_details_table WHERE id IS :id LIMIT 1")
    suspend fun get(id:Int): ProfileEntity

    @Query("SELECT * FROM user_profile_details_table ORDER BY display_name ASC")
    fun get(): Flow<List<ProfileEntity>>

    @Query("SELECT * FROM user_profile_details_table WHERE display_name IS :name ORDER BY display_name ASC")
    fun get(name:String): Flow<List<ProfileEntity>>

    @Transaction
    @Query("SELECT * FROM user_profile_details_table WHERE id IN (:ids) ORDER BY display_name ASC")
    fun get(
        ids:List<Int>,
    ): Flow<List<ProfileEntity>>


    @Query("""
  SELECT *
  FROM user_profile_details_table
  JOIN user_profile_details_table_fts ON user_profile_details_table.name = user_profile_details_table_fts.name
  WHERE user_profile_details_table_fts MATCH :query
""")
    fun search(query:String): Flow<List<ProfileEntity>>

}