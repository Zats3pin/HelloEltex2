package com.eltex.androidschool.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.CardEventBinding
import com.eltex.androidschool.model.Event

class EventsAdapter(
    private val listener: EventListener
) : ListAdapter<Event, EventViewHolder>(EventItemCallback()) {

    interface EventListener {
        fun onLikeClickListener(event: Event)
        fun onParticipatedClickListener(event: Event)
        fun onMenuClickListener(event: Event)
        fun onShareClickListener(event: Event)
        fun onDeleteClickListener(event: Event)
        fun onEditClickListener(event: Event)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val eventBinding = CardEventBinding.inflate(inflater, parent, false)
        val viewHolder = EventViewHolder(eventBinding)

        eventBinding.like.setOnClickListener {
            listener.onLikeClickListener(getItem(viewHolder.adapterPosition))
        }

        eventBinding.participated.setOnClickListener {
            listener.onParticipatedClickListener(getItem(viewHolder.adapterPosition))
        }

        eventBinding.share.setOnClickListener {
            listener.onShareClickListener(getItem(viewHolder.adapterPosition))
        }


        eventBinding.menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.post_actions_menu)

                setOnMenuItemClickListener { item ->
                    when (item.itemId) {

                        R.id.edit -> {
                            listener.onEditClickListener(getItem(viewHolder.adapterPosition))
                            true
                        }

                        R.id.delete -> {
                            listener.onDeleteClickListener(getItem(viewHolder.adapterPosition))
                            true
                        }


                        else -> false
                    }
                }
                show()
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(
        holder: EventViewHolder, position: Int, payloads: List<Any>
    ) {
        if (payloads.isNotEmpty()) {
            payloads.forEach {
                if (it is EventPayLoad) {
                    holder.bindEvent(it)
                }
            }
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindEvent(getItem(position))
    }
}