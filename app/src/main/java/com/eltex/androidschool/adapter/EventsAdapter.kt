package com.eltex.androidschool.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.CardEventBinding
import com.eltex.androidschool.databinding.ItemErrorBinding
import com.eltex.androidschool.databinding.ItemSkeletonBinding
import com.eltex.androidschool.model.EventUiModel
import com.eltex.androidschool.model.PagingModel

class EventsAdapter(
    private val listener: EventListener
) : ListAdapter<PagingModel<EventUiModel>, RecyclerView.ViewHolder>(EventPagingModelItemCallback()) {

    interface EventListener {
        fun onLikeClickListener(event: EventUiModel)
        fun onParticipatedClickListener(event: EventUiModel)
        fun onMenuClickListener(event: EventUiModel)
        fun onShareClickListener(event: EventUiModel)
        fun onDeleteClickListener(event: EventUiModel)
        fun onEditClickListener(event: EventUiModel)
        fun onRetryPageClickListener()
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is PagingModel.Data -> R.layout.card_event
            is PagingModel.Error -> R.layout.item_error
            PagingModel.Progress -> R.layout.item_progress
        }

    private fun createEventViewHolder(parent: ViewGroup): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val eventBinding = CardEventBinding.inflate(inflater, parent, false)
        val viewHolder = EventViewHolder(eventBinding)

        eventBinding.like.setOnClickListener {
            listener.onLikeClickListener((getItem(viewHolder.adapterPosition) as PagingModel.Data).value)
        }

        eventBinding.participated.setOnClickListener {
            listener.onParticipatedClickListener((getItem(viewHolder.adapterPosition) as PagingModel.Data).value)
        }

        eventBinding.share.setOnClickListener {
            listener.onShareClickListener((getItem(viewHolder.adapterPosition) as PagingModel.Data).value)
        }


        eventBinding.menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.post_actions_menu)

                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.edit -> {
                            listener.onEditClickListener((getItem(viewHolder.adapterPosition) as PagingModel.Data).value)
                            true
                        }
                        R.id.delete -> {
                            listener.onDeleteClickListener((getItem(viewHolder.adapterPosition) as PagingModel.Data).value)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.card_event -> createEventViewHolder(parent)
            R.layout.item_error -> createErrorViewHolder(parent)
            R.layout.item_progress -> createProgressViewHolder(parent)
            else -> error("Unknown view type: $viewType")
        }


    private fun createErrorViewHolder(parent: ViewGroup): ErrorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemErrorBinding.inflate(inflater, parent, false)
        binding.retryButton.setOnClickListener {
            listener.onRetryPageClickListener()
        }
        return ErrorViewHolder(binding)
    }

    private fun createProgressViewHolder(parent: ViewGroup): ProgressViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProgressViewHolder(ItemSkeletonBinding.inflate(inflater, parent, false))
    }


    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>
    ) {
        if (payloads.isNotEmpty()) {
            payloads.forEach {
                if (it is EventPayLoad && holder is EventViewHolder) {
                    holder.bindEvent(it)
                }
            }
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is PagingModel.Data -> (holder as EventViewHolder).bindEvent(item.value)
            is PagingModel.Error -> (holder as ErrorViewHolder).bind(item.reason)
            PagingModel.Progress -> Unit
        }
    }
}