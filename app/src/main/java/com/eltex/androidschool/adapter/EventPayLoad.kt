package com.eltex.androidschool.adapter

import com.eltex.androidschool.model.Attachment

data class EventPayLoad(
    val liked: Boolean? = null,
    val participated: Boolean? = null,
    val like: Int? = null,
    val participate: Int? = null,
    val content: String? = null,
    val attachment: Attachment? = null
) {
    fun isNotEmpty(): Boolean =
        liked != null || like != null || participate != null || content != null || attachment != null

    fun isNotEmptyParticipate(): Boolean = participated != null
}