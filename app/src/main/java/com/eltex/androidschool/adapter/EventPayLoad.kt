package com.eltex.androidschool.adapter

data class EventPayLoad(
    val liked: Boolean? = null,
    val participated: Boolean? = null,
) {
    fun isNotEmpty(): Boolean = liked != null

    fun isNotEmptyParticipate(): Boolean = participated != null

}