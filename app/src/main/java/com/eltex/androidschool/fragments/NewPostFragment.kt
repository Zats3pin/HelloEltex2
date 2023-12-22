package com.eltex.androidschool.fragments

import com.eltex.androidschool.utils.toast
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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
import kotlinx.coroutines.flow.onEach

class NewPostFragment : Fragment() {

companion object{
    const val ARG_ID = "ARG_ID"
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
                        //   не работает!!!!!!!!!!!!!!!!!!!!!toast
                    // toast(R.string.post_empty_error, false)
                    }

                    toolbarViewModel.saveClicked(false)
                }






       return binding.root

    }


    private var eventId: Long = -1
    private var eventContent: String = ""

/** override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intentEventData = intent.getLongExtra("event_id", -1)
        if (intentEventData != -1L) {
            eventId = intentEventData
            eventContent = intent.getStringExtra("event_content") ?: ""
            binding.content.setText(eventContent)
        } else {
            eventId = -1
        }
 */
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = FragmentNewPostBinding.inflate(layoutInflater) // переименовать под event




   /**
    val intentEventData = intent.getLongExtra("event_id", -1)
    if (intentEventData != -1L) {
        eventId = intentEventData
        eventContent = intent.getStringExtra("event_content") ?: ""
        binding.content.setText(eventContent)
    } else {
        eventId = -1
    }
    */

            /**  binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.save -> {
                    val content = binding.content.text?.toString().orEmpty()

                    if (content.isNotBlank()) {
                        setResult(
                            RESULT_OK,
                            Intent().putExtra(Intent.EXTRA_TEXT, content)
                                .putExtra("event_id", eventId)
                        )
                        finish()
                    } else {
                        toast(R.string.post_empty_error, false)
                    }

                    true
                }

                else -> false
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        } */

    }
}