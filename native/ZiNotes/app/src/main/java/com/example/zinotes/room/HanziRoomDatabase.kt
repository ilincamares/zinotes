package com.example.zinotes.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Hanzi::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class HanziRoomDatabase: RoomDatabase() {
    abstract fun hanziDao(): HanziDao

    companion object {
        @Volatile
        private var INSTANCE: HanziRoomDatabase? = null

        fun getDatabase(context: Context): HanziRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HanziRoomDatabase::class.java,
                    "hanzi_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}