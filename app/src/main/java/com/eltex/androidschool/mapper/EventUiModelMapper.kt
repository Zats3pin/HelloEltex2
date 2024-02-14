package com.eltex.androidschool.mapper

import com.eltex.androidschool.model.Event
import com.eltex.androidschool.model.EventUiModel
import javax.inject.Inject


class EventUiModelMapper @Inject constructor(
    private val eventDateTimeFormatter: EventDateTimeFormatter
) {

    fun map(event: Event): EventUiModel = with(event) {
        EventUiModel(
            id = id,
            content = content,
            author = author,
            published = eventDateTimeFormatter.formatInstant(published),
            url = url,
            type = type,
            datetime = eventDateTimeFormatter.formatInstant(datetime),
            likedByMe = likedByMe,
            participatedByMe = participatedByMe,
            like = likeOwnerIds.size,
            participate = participantsIds.size,
            attachment = attachment,
            authorAvatar = authorAvatar

        )
    }
}