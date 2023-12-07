package com.eltex.androidschool.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.eltex.androidschool.R
import com.eltex.androidschool.adapter.EventsAdapter
import com.eltex.androidschool.adapter.OffsetDecoration
import com.eltex.androidschool.databinding.ActivityMainBinding
import com.eltex.androidschool.model.Event
import com.eltex.androidschool.repository.InMemoryEventRepository
import com.eltex.androidschool.utils.toast
import com.eltex.androidschool.viewmodel.EventViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))//


        val viewModel by viewModels<EventViewModel> {
            viewModelFactory {
                initializer {
                    EventViewModel(InMemoryEventRepository())
                }
            }
        }

        val newPostContract =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                val content = it.data?.getStringExtra(Intent.EXTRA_TEXT)
                val eventId = it.data?.getLongExtra("event_id", -1)
                if (content != null && eventId == -1L) {
                    viewModel.addPost(content)

                } else {
                    viewModel.editById(eventId, content)
                }
            }

        binding.newPost.setOnClickListener {
            newPostContract.launch(Intent(this, NewPostActivity::class.java))
        }
        if (intent.action == Intent.ACTION_SEND) { // Обязательно проверьте соответствует ли action ожидаемому
            val text = intent.getStringExtra(Intent.EXTRA_TEXT)
            intent.removeExtra(Intent.EXTRA_TEXT) // Удаляем, чтобы при повороте экрана снова не открывалась активити
            val intent = Intent(applicationContext, NewPostActivity::class.java)
        }

        setContentView(binding.root)

        val adapter = EventsAdapter(object : EventsAdapter.EventListener {


            override fun onLikeClickListener(event: Event) {
                viewModel.likeById(event.id)
            }

            override fun onParticipatedClickListener(event: Event) {
                viewModel.participatedById(event.id)
            }

            override fun onShareClickListener(event: Event) {
                val intent = Intent().setAction(Intent.ACTION_SEND).putExtra(
                    Intent.EXTRA_TEXT, getString(R.string.share_text, event.author, event.content)
                ).setType("text/plain")

                val chooser = Intent.createChooser(intent, null)

                startActivity(chooser)
            }


            override fun onMenuClickListener(event: Event) {
                toast(R.string.not_implemented, false)
            }


            override fun onDeleteClickListener(event: Event) {
                viewModel.deleteById(event.id)
            }


            override fun onEditClickListener(event: Event) {
                val intent = Intent(applicationContext, NewPostActivity::class.java)
                intent.putExtra("event_id", event.id)
                intent.putExtra("event_content", event.content)
                newPostContract.launch(intent)
                viewModel.deleteById(event.id)
            }
        })

        binding.list.adapter = adapter

        binding.list.addItemDecoration(OffsetDecoration(resources.getDimensionPixelSize(R.dimen.small_spacing)))

        viewModel.state.flowWithLifecycle(lifecycle).onEach {
            adapter.submitList(it.events)
        }.launchIn(lifecycleScope)
    }
}

