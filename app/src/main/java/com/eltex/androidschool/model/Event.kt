package com.eltex.androidschool.model

import kotlinx.serialization.Serializable

@Serializable

data class Event(
    val id: Long = 0L,
    val content: String = "",
    val author: String = "",
    val published: String = "",
    val likedByMe: Boolean = false,
    val link: String = "",
    val status: String = "",
    val timeStatus: String = "",
    val participatedByMe: Boolean = false,
    )