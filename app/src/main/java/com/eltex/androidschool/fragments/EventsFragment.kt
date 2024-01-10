package com.eltex.androidschool.fragments

import android.app.admin.NetworkEvent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.eltex.androidschool.R
import com.eltex.androidschool.viewmodel.EventViewModel
import android.content.Intent
import android.widget.Toast
import androidx.core.os.bundleOf

import androidx.core.view.isVisible
import com.eltex.androidschool.adapter.OffsetDecoration
import com.eltex.androidschool.repository.NetworkEventRepository
import com.eltex.androidschool.utils.getText




import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController

import com.eltex.androidschool.adapter.EventsAdapter
import com.eltex.androidschool.databinding.FragmentEventsBinding
import com.eltex.androidschool.model.Event
import com.eltex.androidschool.utils.toast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class EventsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreate(savedInstanceState)
        val binding = FragmentEventsBinding.inflate(inflater, container, false)//


        val viewModel by viewModels<EventViewModel> {
            viewModelFactory {
                initializer {
                    EventViewModel(
                        NetworkEventRepository()
                    )
                }
            }
        }


        val adapter = EventsAdapter(object : EventsAdapter.EventListener {


            override fun onLikeClickListener(event: Event) {
                viewModel.like(event)
            }

            override fun onParticipatedClickListener(event: Event) {
                viewModel.participate(event)
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

                requireParentFragment().requireParentFragment().findNavController().navigate(
                    R.id.action_bottomNavigationFragment_to_newPostFragment, bundleOf(
                        NewPostFragment.ARG_EVENT_ID to event.id,
                        NewPostFragment.ARG_CONTENT to event.content
                    )
                )
            }
        })


        binding.list.adapter = adapter

        binding.list.addItemDecoration(OffsetDecoration(resources.getDimensionPixelSize(R.dimen.small_spacing)))



        binding.swipeRefresh.setOnRefreshListener {
            viewModel.load()
        }
        binding.retryButton.setOnClickListener {
            viewModel.load()
        }
        requireActivity().supportFragmentManager.setFragmentResultListener(
            NewPostFragment.POST_CREATED_RESULT,
            viewLifecycleOwner
        ) { _, _ ->
            viewModel.load()
        }
        viewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                binding.swipeRefresh.isRefreshing = state.isRefreshing
                val emptyError = state.emptyError
                binding.errorGroup.isVisible = emptyError != null
                binding.errorText.text = emptyError?.getText(requireContext())
                binding.progress.isVisible = state.isEmptyLoading
                state.refreshingError?.let {
                    Toast.makeText(
                        requireContext(),
                        it.getText(requireContext()),
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.consumeError()
                }
                adapter.submitList(state.events)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }

}



