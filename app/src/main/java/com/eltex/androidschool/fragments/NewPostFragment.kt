package com.eltex.androidschool.fragments

import com.eltex.androidschool.utils.toast
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.eltex.androidschool.R

import com.eltex.androidschool.databinding.FragmentNewPostBinding
import com.eltex.androidschool.db.AppDb
import com.eltex.androidschool.repository.SqliteEventRepository
import com.eltex.androidschool.viewmodel.EventViewModel
import com.eltex.androidschool.viewmodel.NewEventViewModel
import com.eltex.androidschool.viewmodel.ToolbarViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NewPostFragment : Fragment() {

companion object{
    const val ARG_ID = "ARG_ID"
    const val ARG_CONTENT = "ARG_CONTENT"

}

    private val toolbarViewModel by activityViewModels<ToolbarViewModel>()

    override fun onStart() {
        super.onStart()
        toolbarViewModel.showSave(true)
    }

    override fun onStop() {
        super.onStop()
        toolbarViewModel.showSave(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val binding = FragmentNewPostBinding.inflate(inflater,container,false)

        val id = arguments?.getLong(ARG_ID) ?: 0L
        val toolbarViewModel by activityViewModels<ToolbarViewModel>()

        val viewModel by viewModels<NewEventViewModel>{
            viewModelFactory {
            initializer {
                NewEventViewModel(
                    repository = SqliteEventRepository(
                        AppDb.getInstance(requireContext().applicationContext
                        ).postsDao
                    ),
                    id = id,
                )
            }
        }
        }







        toolbarViewModel.saveClicked
            .filter {it}
                .onEach{
                    val content = binding.content.text?.toString().orEmpty()

                    if (content.isNotBlank()) {
                        viewModel.save(content)
                        findNavController().navigateUp()
                    } else {
                     toast(R.string.post_empty_error, false)
                    }

                    toolbarViewModel.saveClicked(false)
                }
            .launchIn(viewLifecycleOwner.lifecycleScope) // Не забудьте добавить


       return binding.root

    }


    private var eventId: Long = -1
    private var eventContent: String = ""


override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = FragmentNewPostBinding.inflate(layoutInflater) // переименовать под event


    }
}