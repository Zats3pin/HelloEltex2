package com.eltex.androidschool.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eltex.androidschool.model.Event


@Entity(tableName = "Posts")
data class PostEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "content")
    val content: String = "",
    @ColumnInfo(name = "author")
    val author: String = "",
    @ColumnInfo(name = "published")
    val published: String = "",
    @ColumnInfo(name = "likedByMe")
    val likedByMe: Boolean = false,
    @ColumnInfo(name = "link")
    val link: String = "",
    @ColumnInfo(name = "status")
    val status: String = "",
    @ColumnInfo(name = "timeStatus")
    val timeStatus: String = "",
    @ColumnInfo(name = "participatedByMe")
    val participatedByMe: Boolean = false,
){

    fun toEvent() : Event = Event(
        id = id,
        content = content,
        author = author,
        published = published,
        likedByMe = likedByMe,
        link = link,
        status = status,
        timeStatus = timeStatus,
        participatedByMe = participatedByMe
    )

    companion object{
        fun fromPost(event: Event): PostEntity = with(event){
            PostEntity(
                id = id,
                content = content,
                author = author,
                published = published,
                likedByMe = likedByMe,
                link = link,
                status = status,
                timeStatus = timeStatus
            )
        }
    }

}