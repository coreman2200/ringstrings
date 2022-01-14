package com.coreman2200.ringstrings.data.room_common

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.coreman2200.ringstrings.data.room_common.dao.ProfileDao
import com.coreman2200.ringstrings.data.room_common.dao.SymbolDao
import com.coreman2200.ringstrings.data.room_common.entity.*
import javax.inject.Inject

/**
 * RSDataBase
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

@Database(
    entities = [SymbolEntity::class, ProfileEntity::class, SymbolDetailEntity::class,
        PlacementEntity::class, LocationEntity::class],
    version = 1
)
abstract class RSDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun symbolDao(): SymbolDao

    companion object {
        @Volatile
        private var instance: RSDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): RSDatabase {
            if(instance == null) synchronized(this) {
                instance = Room.databaseBuilder(
                    ctx, RSDatabase::class.java,
                    "rs_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance!!

        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: RSDatabase) {
            val symbolDao = db.symbolDao()
            val profileDao = db.profileDao()
        }
    }

}