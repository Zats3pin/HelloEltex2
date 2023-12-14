package com.eltex.androidschool.dao

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import com.eltex.androidschool.model.Event
import kotlinx.serialization.SerialName

class PostsDaoImpl(private val db: SQLiteDatabase) : PostDao {
    override fun getAll(): List<Event> =
        db.query(
            PostTable.TABLE_NAME,
            PostTable.allColunns,
            null,
            null,
            null,
            null,
            "${PostTable.ID} DESC"
        ).use { cursor ->
            val result = mutableListOf<Event>()

            while (cursor.moveToNext()) {
                result += cursor.getPost()
            }
            result
        }


    override fun save(event : Event): Event {
        val contentValues = contentValuesOf(
            PostTable.CONTENT to event.content,
            PostTable.AUTHOR to event.author,
            PostTable.PIBLISHED to event.published,
            PostTable.LIKED_BY_ME to event.likedByMe,
            PostTable.LINK to event.link,
            PostTable.STATUS to event.status,
            PostTable.TIME_STATUS to event.timeStatus,
            PostTable.PARTICIPATED_BY_ME to event.participatedByMe,

            )
        if (event.id != 0L) {
            contentValues.put(PostTable.ID, event.id)
        }

        val id = db.replace(PostTable.TABLE_NAME, null, contentValues)
        return  getPostById(id)
    }

        private fun getPostById(id:Long): Event =
        db.query(
            PostTable.TABLE_NAME,
            PostTable.allColunns,
            "${PostTable.ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            "${PostTable.ID} DESC"
        ).use { cursor ->


            cursor.moveToNext()
            cursor.getPost();
        }


    override fun likeById(eventId: Long): Event {
        db.execSQL("""
            UPDATE ${PostTable.TABLE_NAME} SET 
            ${PostTable.LIKED_BY_ME} = CASE WHEN 
            ${PostTable.LIKED_BY_ME} = 1 THEN 0 ELSE 1 
            END WHERE ${PostTable.ID} = ? 
        """.trimIndent(), arrayOf(eventId.toString())
        )
        return getPostById(eventId)
    }

    override fun deleteById(eventId: Long) {
        db.delete(PostTable.TABLE_NAME,"${PostTable.ID} = ?", arrayOf(eventId.toString()))
    }

    private fun Cursor.getPost(): Event =
        Event(
            id = getLong(getColumnIndexOrThrow(PostTable.ID)),
            content = getString(getColumnIndexOrThrow(PostTable.CONTENT)),
            author = getString(getColumnIndexOrThrow(PostTable.PIBLISHED)),
            published = getString(getColumnIndexOrThrow(PostTable.AUTHOR)),
            likedByMe = getInt(getColumnIndexOrThrow(PostTable.LIKED_BY_ME)) != 0,
            link = getString(getColumnIndexOrThrow(PostTable.LINK)),
            status = getString(getColumnIndexOrThrow(PostTable.STATUS)),
            timeStatus = getString(getColumnIndexOrThrow(PostTable.TIME_STATUS)),
            participatedByMe = getInt(getColumnIndexOrThrow(PostTable.PARTICIPATED_BY_ME))!= 0,
        )
}
