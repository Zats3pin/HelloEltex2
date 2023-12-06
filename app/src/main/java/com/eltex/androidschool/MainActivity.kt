package com.eltex.androidschool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.eltex.androidschool.adapter.EventsAdapter
import com.eltex.androidschool.adapter.OffsetDecoration

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

       val adapter = EventsAdapter{
           viewModel.likeById(it.id)
        }

        binding.root.adapter = adapter

        binding.root.addItemDecoration(OffsetDecoration(resources.getDimensionPixelSize(R.dimen.small_spacing)))

        viewModel.state
            .flowWithLifecycle(lifecycle)
            .onEach {
                adapter.submitList(it.events)

               // binding.container.removeAllViews()
               // val events = it.events

               // events.forEach {event ->

               //     bindEvent(eventBinding,event)

                //    eventBinding.like.setOnClickListener {
               //         viewModel.likeById(event.id)

               //     }

               //     eventBinding.event.setOnClickListener {
               //         viewModel.participateById(event.id)
               //     }

               //     eventBinding.menu.setOnClickListener {
               //         toast(R.string.not_implemented, false)
               //     }

               //     eventBinding.share.setOnClickListener {
               //         toast(R.string.not_implemented, false)
               //     }


              //  }
            }.launchIn(lifecycleScope)




    }


}

