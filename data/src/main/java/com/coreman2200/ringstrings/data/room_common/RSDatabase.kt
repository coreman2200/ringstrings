package com.coreman2200.ringstrings.data.room_common

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.coreman2200.ringstrings.data.file.details.SymbolDetailFileHandler
import com.coreman2200.ringstrings.data.room_common.dao.ProfileDao
import com.coreman2200.ringstrings.data.room_common.dao.SymbolDao
import com.coreman2200.ringstrings.data.room_common.dao.SymbolDescriptionDao
import com.coreman2200.ringstrings.data.room_common.entity.*
import com.coreman2200.ringstrings.domain.SymbolDescription
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    entities = [SymbolEntity::class, ProfileEntity::class, ProfileEntityFTS::class,
        SymbolDetailEntity::class, PlacementEntity::class, LocationEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RSDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun symbolDao(): SymbolDao
    abstract fun detailDao(): SymbolDescriptionDao

    companion object {
        const val RINGSTRINGS_DATABASE_TAG = "ringstringsDatabase"

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
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(instance!!)
                }
            }
        }

        private suspend fun populateDatabase(db: RSDatabase) {
            //val symbolDao = db.symbolDao()
            //val profileDao = db.profileDao()

            // FTS profile details
            db.compileStatement("INSERT INTO user_profile_details_table_fts(user_profile_details_table_fts) VALUES ('rebuild')")

            val detailDao = db.detailDao()
            detailDao.insertAll(getDescriptions())
        }

        private fun getDescriptions():List<SymbolDetailEntity> {
            val fh = SymbolDetailFileHandler()
            return fh.getAllDescriptions().toList()
        }
    }

}

class Converters {
    @TypeConverter
    fun fromString(str:String) : List<String> {
        return str.split(",").map { it.trim() }
    }

    @TypeConverter
    fun fromList(list:List<String>?) : String {
        return list?.joinToString(separator = ",") ?: ""
    }

}