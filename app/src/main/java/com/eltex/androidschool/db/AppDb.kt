package com.eltex.androidschool.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eltex.androidschool.dao.PostDao
import com.eltex.androidschool.entity.PostEntity

@Database(
    entities = [PostEntity::class],
    version = 1,
)
abstract class AppDb:RoomDatabase() {
    abstract val postsDao: PostDao

    @InternalCoroutinesApi
    companion object {
        @Volatile
        private var INSTANCE: AppDb? = null
        fun getInstance(context: Context): AppDb {
            INSTANCE?.let {
                return it
            }

            val applicationContext = context.applicationContext

            synchronized(this) {
                INSTANCE?.let { return it }

                val appDb = Room.databaseBuilder(applicationContext, AppDb::class.java, "app_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = appDb

                return appDb
            }
        }
    }

}