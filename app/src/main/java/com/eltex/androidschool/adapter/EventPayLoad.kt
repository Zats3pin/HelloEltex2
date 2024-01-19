package com.eltex.androidschool.adapter

data class EventPayLoad(
    val liked: Boolean? = null,
    val participated: Boolean? = null,
    val like: Int? = null,
    val participate: Int? = null,
) {
    fun isNotEmpty(): Boolean = liked != null || like != null
    fun isNotEmptyParticipate(): Boolean = participated != null || participated != null
}