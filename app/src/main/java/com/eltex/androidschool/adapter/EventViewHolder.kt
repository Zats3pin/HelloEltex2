package com.eltex.androidschool.adapter

import androidx.recyclerview.widget.RecyclerView
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.CardEventBinding
import com.eltex.androidschool.model.EventUiModel

class EventViewHolder(
    private val binding: CardEventBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindEvent(payLoad: EventPayLoad) {
        if (payLoad.liked != null) {
            updateLike(payLoad.liked)
        }
        if (payLoad.participated != null) {
            updateParticipated(payLoad.participated)
        }
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
        updateLike(event.likedByMe)
        updateParticipated(event.participatedByMe)
        updateLikeCount(event.like)
        updateParticipatedCount(event.participate)
    }

    private fun updateLike(likedByMe: Boolean) {

        binding.like.setIconResource(
            if (likedByMe) {
                R.drawable.favorit_like
            } else {
                R.drawable.baseline_favorite_border_24
            }
        )
        binding.like.text = if (likedByMe) {
            1
        } else {
            0
        }.toString()
    }

    private fun updateParticipated(participatedByMe: Boolean) {
        binding.participated.setIconResource(
            if (participatedByMe) {
                R.drawable.event_true
            } else {
                R.drawable.event
            }
        )
        binding.participated.text = if (participatedByMe) {
            1
        } else {
            0
        }.toString()
    }

    private fun updateLikeCount(likeCount: Int) {
        binding.like.text = likeCount.toString()
    }

    private fun updateParticipatedCount(participateCount: Int) {
        binding.participated.text = participateCount.toString()
    }

}