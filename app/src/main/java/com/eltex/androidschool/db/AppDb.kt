package com.eltex.androidschool.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    entities = [PostEntity::class],
    version = 2,
)
abstract class AppDb : RoomDatabase() {
    abstract val postsDao: PostDao

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
                    .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = appDb

                return appDb
            }
        }


        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `Posts_new` (" +
                            "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "`content` TEXT NOT NULL, " +
                            "`published` TEXT NOT NULL, " +
                            "`author` TEXT NOT NULL, " +
                            "`likedByMe` INTEGER NOT NULL DEFAULT 0, " +
                            "`link` TEXT NOT NULL, " +
                            "`status` TEXT NOT NULL, " +
                            "`timeStatus` TEXT NOT NULL, " +
                            "`participatedByMe` INTEGER NOT NULL DEFAULT 0)"
                )


                db.execSQL("INSERT INTO `Posts_new` (`id`, `content`, `published`, `author`, `likedByMe`, `link`, `status`, `timeStatus`, `participatedByMe`) SELECT `id`, `context`, `published`, `author`, `likeByMe`, `link`, `status`, `timeStatus`, `participatedByMe` FROM `Posts`")


                db.execSQL("DROP TABLE IF EXISTS `Posts`")


                db.execSQL("ALTER TABLE `Posts_new` RENAME TO `Posts`")
            }
        }
    }
}