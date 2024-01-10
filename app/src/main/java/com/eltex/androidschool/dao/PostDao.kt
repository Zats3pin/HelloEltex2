package com.eltex.androidschool.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.eltex.androidschool.entity.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM Posts ORDER BY id DESC")
    fun getAll(): Flow<List<PostEntity>>

    @Upsert
    fun save(event: PostEntity): Long

    @Query(
        """
        UPDATE Posts SET
        likedByMe = CASE WHEN likedByMe = 1 THEN 0 ELSE 1 END
        WHERE id = :eventId
    """
    )

    fun likeById(eventId: Long)

    @Query("DELETE FROM Posts WHERE id = :eventId")
    fun deleteById(eventId: Long)

    @Query(
        """
        UPDATE Posts SET
        participatedByMe = CASE WHEN participatedByMe = 1 THEN 0 ELSE 1 END
        WHERE id = :eventId
    """
    )
    fun participatedById(eventId: Long)

    @Query(
        """
        UPDATE Posts SET content = :content
        WHERE id = :id
        """
    )
    fun editById(id: Long?, content: String?)
}