package com.eltex.androidschool.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.CardEventBinding
import com.eltex.androidschool.model.Event

class EventsAdapter(
    private val likeClickListener: (Event) -> Unit,
    private val participatedClickListener: (Event) -> Unit,
    private val shareClickListener: (Event) -> Unit,
    private val menuClickListener: (Event) -> Unit,
    private val deleteClickListener: (Event) -> Unit,
) : ListAdapter<Event, EventViewHolder>(EventItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val eventBinding = CardEventBinding.inflate(inflater, parent, false)
        val viewHolder = EventViewHolder(eventBinding)

        eventBinding.like.setOnClickListener {
            likeClickListener(getItem(viewHolder.adapterPosition))
        }

        eventBinding.participated.setOnClickListener {
            participatedClickListener(getItem(viewHolder.adapterPosition))
        }

        eventBinding.share.setOnClickListener {
            shareClickListener(getItem(viewHolder.adapterPosition))
        }

        eventBinding.menu.setOnClickListener {
            menuClickListener(getItem(viewHolder.adapterPosition))
        }

        eventBinding.menu.setOnClickListener {
            PopupMenu(it.context,it).apply {
                inflate(R.menu.post_actions_menu)

                setOnMenuItemClickListener {item ->
                    when(item.itemId){
                        R.id.delete ->{
                            deleteClickListener(getItem(viewHolder.adapterPosition))
                            true
                        }
                        else-> false
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