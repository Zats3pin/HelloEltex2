package com.eltex.androidschool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

import com.eltex.androidschool.databinding.ActivityMainBinding
import com.eltex.androidschool.databinding.CardEventBinding
import com.eltex.androidschool.model.Event
import com.eltex.androidschool.repository.InMemoryEventRepository
import com.eltex.androidschool.utils.toast
import com.eltex.androidschool.viewmodel.EventViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel by viewModels<EventViewModel> {
            viewModelFactory {
                initializer {
                    EventViewModel(InMemoryEventRepository())
                }
            }
        }

        val binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)

        viewModel.state
            .flowWithLifecycle(lifecycle)
            .onEach {
                binding.container.removeAllViews()
                val events = it.events

                events.forEach {event ->
                  val eventBinding =  CardEventBinding.inflate(layoutInflater, binding.container, true)
                    bindEvent(eventBinding,event)

                    eventBinding.like.setOnClickListener {
                        viewModel.likeById(event.id)

                    }

                    eventBinding.event.setOnClickListener {
                        viewModel.participateById(event.id)
                    }

                    eventBinding.menu.setOnClickListener {
                        toast(R.string.not_implemented, false)
                    }

                    eventBinding.share.setOnClickListener {
                        toast(R.string.not_implemented, false)
                    }


                }
            }.launchIn(lifecycleScope)




    }

    private fun bindEvent(
        binding:CardEventBinding,
        event: Event) {
        binding.author.text = event.author
        binding.content.text = event.content
        binding.published.text = event.published
        binding.initial.text = event.author.take(1)
        binding.link.text = event.link
        binding.status.text = if (event.status) {
            getString(R.string.event_online)
        } else {
            getString(R.string.event_offline)
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

