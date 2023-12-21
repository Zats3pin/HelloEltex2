package com.eltex.androidschool.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.eltex.androidschool.R
import com.eltex.androidschool.viewmodel.EventViewModel
import android.content.Intent

import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

import com.eltex.androidschool.adapter.EventsAdapter
import com.eltex.androidschool.adapter.OffsetDecoration

import com.eltex.androidschool.databinding.FragmentEventsBinding

import com.eltex.androidschool.db.AppDb
import com.eltex.androidschool.model.Event
import com.eltex.androidschool.repository.SqliteEventRepository
import com.eltex.androidschool.utils.toast

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A simple [Fragment] subclass.
 * Use the [EventsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
/**class EventsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false)
    }


}
*/

class EventsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreate(savedInstanceState)
        val binding = FragmentEventsBinding.inflate(inflater, container, false)//


        val viewModel by viewModels<EventViewModel> {
            viewModelFactory {
                initializer {
                    EventViewModel(
                        SqliteEventRepository(
                            AppDb.getInstance(requireContext().applicationContext).postsDao
                        )
                    )
                }
            }
        }

        /**  val newPostContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val content = it.data?.getStringExtra(Intent.EXTRA_TEXT)
        val eventId = it.data?.getLongExtra("event_id", -1)
        if (content != null && eventId == -1L) {
        viewModel.addPost(content)

        } else {
        viewModel.editById(eventId, content)
        }
        }
         */

        /**
        binding.newPost.setOnClickListener {
        newPostContract.launch(Intent(this, NewPostActivity::class.java))
        }

        if (intent.action == Intent.ACTION_SEND) { // Обязательно проверьте соответствует ли action ожидаемому
        val text = intent.getStringExtra(Intent.EXTRA_TEXT)
        intent.removeExtra(Intent.EXTRA_TEXT) // Удаляем, чтобы при повороте экрана снова не открывалась активити
        val intent = Intent(applicationContext, NewPostActivity::class.java)
        intent.putExtra("event_id", -2L)
        intent.putExtra("event_content", text)
        newPostContract.launch(intent)
        }
         */


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
                //  toast(R.string.not_implemented, false)
            }


            override fun onDeleteClickListener(event: Event) {
                viewModel.deleteById(event.id)
            }


            override fun onEditClickListener(event: Event) {
                //       val intent = Intent(applicationContext, NewPostActivity::class.java)
                //       intent.putExtra("event_id", event.id)
                //       intent.putExtra("event_content", event.content)
                //       newPostContract.launch(intent)
            }
        })

        binding.list.adapter = adapter

        binding.list.addItemDecoration(OffsetDecoration(resources.getDimensionPixelSize(R.dimen.small_spacing)))

        viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach {
            adapter.submitList(it.events)
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)


        return binding.root
    }

}



