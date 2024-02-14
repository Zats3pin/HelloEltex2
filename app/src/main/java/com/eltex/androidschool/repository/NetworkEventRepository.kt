package com.eltex.androidschool.repository

import android.content.ContentResolver
import com.eltex.androidschool.api.EventsApi
import com.eltex.androidschool.api.MediaApi
import com.eltex.androidschool.model.Attachment
import com.eltex.androidschool.model.Event
import com.eltex.androidschool.model.FileModel
import com.eltex.androidschool.model.Media
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.Instant
import javax.inject.Inject

class NetworkEventRepository @Inject constructor(
    private val eventApi: EventsApi,
    private val mediaApi: MediaApi,
    private val contentResolver: ContentResolver
) : EventRepository {


    override suspend fun saveEvent(
        id: Long,
        content: String,
        datetime: Instant,
        file: FileModel?
    ): Event {
        val event = file?.let {
            val media = upload(it)
            Event(
                id = id,
                content = content,
                datetime = datetime,
                attachment = Attachment(media.url, it.attachmentType)
            )
        } ?: Event(
            id = id,
            content = content,
            datetime = datetime
        )
        return eventApi.save(event)

    }

    private suspend fun upload(fileModel: FileModel): Media {
        return mediaApi.upload(
            MultipartBody.Part.createFormData(
                "file",
                "file",
                withContext(Dispatchers.IO) {
                    requireNotNull(contentResolver.openInputStream(fileModel.uri)).use {
                        it.readBytes()
                    }
                        .toRequestBody()
                }
            )
        )
    }


    override suspend fun deleteById(id: Long) = eventApi.deleteById(id)
    override suspend fun getLatest(count: Int): List<Event> = eventApi.getLatest(count)

    override suspend fun getBefore(id: Long, count: Int): List<Event> =
        eventApi.getBefore(id, count)


    override suspend fun participate(id: Long): Event = eventApi.participate(id)


    override suspend fun editById(id: Long, content: String): Event =
        eventApi.save(Event(id, content))

    override suspend fun likeById(id: Long): Event = eventApi.like(id)

    override suspend fun unLikeById(id: Long): Event = eventApi.unLike(id)

    override suspend fun unParticipate(id: Long): Event = eventApi.unParticipate(id)


}