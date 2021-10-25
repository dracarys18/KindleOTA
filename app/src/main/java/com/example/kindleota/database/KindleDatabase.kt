package com.example.kindleota.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [KindleData::class], version = 1, exportSchema = false)
abstract class KindleDatabase : RoomDatabase() {

    abstract val kindledabaseDao: KindleDataDAO

    companion object {
        @Volatile
        private var INSTANCE: KindleDatabase? = null
        fun getInstance(context: Context): KindleDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        KindleDatabase::class.java,
                        "kindle_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
