package com.eltex.androidschool.model


data class EventUiModel(
    val id: Long = 0L,
    val content: String = "",
    val author: String = "",
    val published: String = "",
    val url: String = "",
    val type: String = "",
    val datetime: String = "",
    val likedByMe: Boolean = false,
    val participatedByMe: Boolean = false,
    val like: Int = 0,
    val participate: Int = 0,
    val attachment: Attachment? = null,
)

