package com.eltex.androidschool.adapter

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.CardEventBinding
import com.eltex.androidschool.model.Attachment
import com.eltex.androidschool.model.EventUiModel

class EventViewHolder(
    private val binding: CardEventBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindEvent(payLoad: EventPayLoad) {
        if (payLoad.liked != null) {
            updateLikeIcon(payLoad.liked)
        }
        if (payLoad.participated != null) {
            updateParticipatedIcon(payLoad.participated)
        }

        if (payLoad.like != null) {
            updateLikeCount(payLoad.like)
        }
        if (payLoad.participate != null) {
            updateParticipatedCount(payLoad.participate)
        }

        if (payLoad.attachment != null) {
            updateAttachment(payLoad.attachment)
        }
    }

    private fun updateAttachment(attachment: Attachment) {
        Glide.with(binding.attachmentPhoto)
            .load(attachment.url)
            .into(binding.attachmentPhoto)
    }

    fun bindEvent(
        event: EventUiModel
    ) {
        binding.author.text = event.author
        binding.content.text = event.content
        binding.published.text = event.published
        binding.initial.text = event.author.take(1)
        binding.link.text = event.url
        binding.status.text = event.type
        binding.timeStatus.text = event.datetime
        updateLikeIcon(event.likedByMe)
        updateParticipatedIcon(event.participatedByMe)
        updateLikeCount(event.like)
        updateParticipatedCount(event.participate)
        if (event.attachment != null) {
            binding.attachmentPhoto.isVisible = true
            updateAttachment(event.attachment)
        } else {
            binding.attachmentPhoto.isGone = true
        }
    }

    private fun updateLikeIcon(likedByMe: Boolean) {

        binding.like.setIconResource(
            if (likedByMe) {
                R.drawable.favorit_like
            } else {
                R.drawable.baseline_favorite_border_24
            }
        )
    }

    private fun updateParticipatedIcon(participatedByMe: Boolean) {
        binding.participated.setIconResource(
            if (participatedByMe) {
                R.drawable.event_true
            } else {
                R.drawable.event
            }
        )
    }

    private fun updateLikeCount(likeCount: Int) {
        binding.like.text = likeCount.toString()
    }

    private fun updateParticipatedCount(participateCount: Int) {
        binding.participated.text = participateCount.toString()
    }

}