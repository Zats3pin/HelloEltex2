package com.eltex.androidschool.adapter

data class EventPayLoad(
    val liked: Boolean? = null,
) {
    fun isNotEmpty(): Boolean = liked != null
}