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

        val viewModel by viewModels<EventViewModel> {
            viewModelFactory {
                initializer {
                    EventViewModel(InMemoryEventRepository())
                }
            }
        }

        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        val newPostContract =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
           val content =  it.data?.getStringExtra(Intent.EXTRA_TEXT)

            if (content != null){
                viewModel.addPost(content)
            }
        }

        binding.newPost.setOnClickListener {
            newPostContract.launch(Intent(this, NewPostActivity::class.java))
        }

        setContentView(binding.root)

        val adapter = EventsAdapter(
            object : EventsAdapter.EventListener{



                override fun onLikeClickListener(event: Event) {
                    viewModel.likeById(event.id)
                }

                override fun onParticipatedClickListener(event: Event) {
                    viewModel.participatedById(event.id)
                }

                override fun onShareClickListener(event: Event) {
                    val intent = Intent()
                        .setAction(Intent.ACTION_SEND)
                        .putExtra(
                            Intent.EXTRA_TEXT,
                            getString(R.string.share_text, event.author, event.content)
                        )
                        .setType("text/plain")

                    val chooser = Intent.createChooser(intent, null)

                    startActivity(chooser)
                }

                override fun onDeleteClickListener(event: Event) {
                    viewModel.deleteById(event.id)
                }
            }

        )

        binding.list.adapter = adapter

        binding.list.addItemDecoration(OffsetDecoration(resources.getDimensionPixelSize(R.dimen.small_spacing)))

        viewModel.state.flowWithLifecycle(lifecycle).onEach {
            adapter.submitList(it.events)
        }.launchIn(lifecycleScope)


    }


}

