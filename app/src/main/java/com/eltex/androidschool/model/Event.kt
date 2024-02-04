package com.eltex.androidschool.model

import com.eltex.androidschool.utils.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable

data class Event(
    @SerialName("id")
    val id: Long = 0L,
    @SerialName("content")
    val content: String = "",
    @SerialName("author")
    val author: String = "",
    @SerialName("published")
    @Serializable(InstantSerializer::class)
    val published: Instant = Instant.now(),
    @SerialName("url")
    val url: String = "",
    @SerialName("type")
    val type: String = "",
    @SerialName("datetime")
    @Serializable(InstantSerializer::class)
    val datetime: Instant = Instant.now(),
    @SerialName("likedByMe")
    val likedByMe: Boolean = false,
    @SerialName("participatedByMe")
    val participatedByMe: Boolean = false,
    @SerialName("likeOwnerIds")
    val likeOwnerIds: Set<Long> = emptySet(),
    @SerialName("participantsIds")
    val participantsIds: Set<Long> = emptySet(),
    @SerialName("attachment")
    val attachment: Attachment? = null,

    )