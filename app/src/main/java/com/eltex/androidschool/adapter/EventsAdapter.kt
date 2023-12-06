package com.eltex.androidschool.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.eltex.androidschool.databinding.CardEventBinding
import com.eltex.androidschool.model.Event

class EventsAdapter(private val likeClickListener: (Event) -> Unit): Adapter<EventViewHolder>()  {
    var events: List<Event> = emptyList()
        set(value){
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val eventBinding =  CardEventBinding.inflate(inflater, parent, false)

        val viewHolder = EventViewHolder(eventBinding)

        eventBinding.like.setOnClickListener {
            likeClickListener(events[viewHolder.adapterPosition])
        }


        return viewHolder
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindEvent(events[position])
    }
    override fun getItemCount(): Int = events.size

}