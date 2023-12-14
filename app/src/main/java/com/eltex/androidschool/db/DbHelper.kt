package com.eltex.androidschool.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.eltex.androidschool.dao.PostTable

class DbHelper(contex:Context) : SQLiteOpenHelper(contex, "app_db", null, 1 ) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
                CREATE TABLE ${PostTable.TABLE_NAME} (
                	${PostTable.ID}	INTEGER PRIMARY KEY AUTOINCREMENT,
                	${PostTable.CONTENT}	TEXT NOT NULL,
                	${PostTable.PIBLISHED}	INTEGER NOT NULL,
                	${PostTable.AUTHOR}	TEXT NOT NULL,
                	${PostTable.LIKED_BY_ME}	INTEGER NOT NULL DEFAULT 0,
                	${PostTable.LINK}	TEXT NOT NULL,
                	${PostTable.STATUS}	TEXT NOT NULL,
                	${PostTable.TIME_STATUS}	TEXT NOT NULL,
                	${PostTable.PARTICIPATED_BY_ME}	INTEGER NOT NULL DEFAULT 0);

            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }


}