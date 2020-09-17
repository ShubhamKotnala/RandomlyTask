package com.seven.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.db.dao.FeedsDataDao
import com.seven.myapplication.model.FeedResponse
import com.seven.myapplication.utils.MyConverters

const val DB_VERSION = 1

const val DB_NAME = "feed_database.db"

@Database(entities = [FeedResponse::class], version = DB_VERSION)
@TypeConverters(MyConverters::class)
abstract class FeedDatabase : RoomDatabase() {
    abstract fun feedDataDao(): FeedsDataDao

    companion object {
        @Volatile
        private var databaseInstance: FeedDatabase? = null

        fun getDatabaseInstance(mContext: Context): FeedDatabase =
            databaseInstance ?: synchronized(this) {
                databaseInstance ?: buildDatabaseInstance(mContext).also {
                    databaseInstance = it
                }
            }

        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, FeedDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
