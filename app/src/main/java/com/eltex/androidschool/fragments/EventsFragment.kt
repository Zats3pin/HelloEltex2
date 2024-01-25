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
import androidx.recyclerview.widget.RecyclerView
import com.eltex.androidschool.adapter.EventsAdapter
import com.eltex.androidschool.api.EventsApi
import com.eltex.androidschool.databinding.FragmentEventsBinding
import com.eltex.androidschool.effecthandler.EventEffectHandler
import com.eltex.androidschool.model.EventMessage
import com.eltex.androidschool.model.EventUiModel
import com.eltex.androidschool.model.EventUiState
import com.eltex.androidschool.mvi.Store
import com.eltex.androidschool.reducer.EventReducer
import com.eltex.androidschool.utils.toast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class EventsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        super.onCreate(savedInstanceState)
        val binding = FragmentEventsBinding.inflate(inflater, container, false)//


        val viewModel by viewModels<EventViewModel> {
            viewModelFactory {
                initializer {
                    EventViewModel(
                        Store(
                            EventReducer(),
                            EventEffectHandler(NetworkEventRepository(EventsApi.INSTANCE)),
                            setOf(EventMessage.Refresh),
                            EventUiState(),
                        )
                    )
                }
            }
        }

        val adapter = EventsAdapter(object : EventsAdapter.EventListener {


            override fun onLikeClickListener(event: EventUiModel) {
                viewModel.accept(EventMessage.Like(event))
            }

            override fun onParticipatedClickListener(event: EventUiModel) {
                viewModel.accept(EventMessage.Participate(event))
            }

            override fun onShareClickListener(event: EventUiModel) {
                val intent = Intent().setAction(Intent.ACTION_SEND).putExtra(
                    Intent.EXTRA_TEXT, getString(R.string.share_text, event.author, event.content)
                ).setType("text/plain")

                val chooser = Intent.createChooser(intent, null)

                startActivity(chooser)
            }


            override fun onMenuClickListener(event: EventUiModel) {
                toast(R.string.not_implemented, false)
            }


            override fun onDeleteClickListener(event: EventUiModel) {
                viewModel.accept(EventMessage.Delete(event))
            }


            override fun onEditClickListener(event: EventUiModel) {

                requireParentFragment().requireParentFragment().findNavController().navigate(
                    R.id.action_bottomNavigationFragment_to_newPostFragment, bundleOf(
                        NewEventFragment.ARG_EVENT_ID to event.id,
                        NewEventFragment.ARG_CONTENT to event.content
                    )
                )
            }
        }
        )


        binding.list.addOnChildAttachStateChangeListener(
            object : RecyclerView.OnChildAttachStateChangeListener {
                override fun onChildViewAttachedToWindow(view: View) {
                    val position = binding.list.getChildAdapterPosition(view)
                    if (position != RecyclerView.NO_POSITION && position == adapter.itemCount - 3) {
                        viewModel.accept(EventMessage.LoadNextPage)
                    }
                }

                override fun onChildViewDetachedFromWindow(view: View) = Unit
            }
        )


        binding.list.adapter = adapter

        binding.list.addItemDecoration(OffsetDecoration(resources.getDimensionPixelSize(R.dimen.small_spacing)))


        binding.swipeRefresh.setOnRefreshListener {
            viewModel.accept(EventMessage.Refresh)
        }
        binding.retryButton.setOnClickListener {
            viewModel.accept(EventMessage.Refresh)
        }
        requireActivity().supportFragmentManager.setFragmentResultListener(
            NewEventFragment.POST_CREATED_RESULT, viewLifecycleOwner
        ) { _, _ ->
            viewModel.accept(EventMessage.Refresh)
        }
        viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { state ->
            binding.swipeRefresh.isRefreshing = state.isRefreshing
            val emptyError = state.emptyError
            binding.errorGroup.isVisible = emptyError != null
            binding.errorText.text = emptyError?.getText(requireContext())
            binding.progress.isVisible = state.isEmptyLoading
            state.singleError?.let {
                Toast.makeText(
                    requireContext(), it.getText(requireContext()), Toast.LENGTH_SHORT
                ).show()
                viewModel.accept(EventMessage.HandleError)
            }
            adapter.submitList(state.events)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }
}



