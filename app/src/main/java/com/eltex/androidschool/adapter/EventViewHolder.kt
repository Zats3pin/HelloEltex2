package com.eltex.androidschool.adapter

import android.provider.Settings.Secure.getString
import androidx.recyclerview.widget.RecyclerView
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.CardEventBinding
import com.eltex.androidschool.model.Event

class EventViewHolder(
    private val binding: CardEventBinding,
) : RecyclerView.ViewHolder(binding.root)
{
    fun bindEvent(
        event: Event) {
        binding.author.text = event.author
        binding.content.text = event.content
        binding.published.text = event.published
        binding.initial.text = event.author.take(1)
        binding.link.text = event.link
        binding.status.text = if (event.status) {
            "online"
        } else {
            "offline"
        }
        binding.timeStatus.text = event.timeStatus
        binding.like.setIconResource(
            if (event.likedByMe) {
                R.drawable.favorit_like
            } else {
                R.drawable.baseline_favorite_border_24
            }
        )
        binding.like.text = if (event.likedByMe) {
            1
        } else {
            0
        }.toString()

        binding.event.setIconResource(
            if (event.participatedByMe) {
                R.drawable.event_true
            } else {
                R.drawable.event
            }
        )
        binding.event.text = if (event.participatedByMe) {
            1
        } else {
            0
        }.toString()
    }


}