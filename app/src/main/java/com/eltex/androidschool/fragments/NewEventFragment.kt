package com.eltex.androidschool.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.eltex.androidschool.R
import com.eltex.androidschool.api.EventsApi
import com.eltex.androidschool.databinding.FragmentNewPostBinding
import com.eltex.androidschool.model.AttachmentType
import com.eltex.androidschool.model.FileModel
import com.eltex.androidschool.repository.NetworkEventRepository
import com.eltex.androidschool.utils.Status
import com.eltex.androidschool.utils.getText
import com.eltex.androidschool.utils.toast
import com.eltex.androidschool.viewmodel.NewEventViewModel
import com.eltex.androidschool.viewmodel.ToolbarViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NewEventFragment : Fragment() {

    companion object {
        const val ARG_EVENT_ID = "ARG_EVENT_ID"
        const val POST_CREATED_RESULT = "POST_CREATED_RESULT"
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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)
        val id = arguments?.getLong(ARG_EVENT_ID) ?: 0L
        val editContent = arguments?.getString(ARG_CONTENT)
        if (editContent?.isNotBlank() == true) {
            binding.content.setText(editContent)
        }


        val viewModel by viewModels<NewEventViewModel> {
            viewModelFactory {
                initializer {
                    NewEventViewModel(
                        repository = NetworkEventRepository(EventsApi.INSTANCE), eventId = id
                    )
                }
            }
        }

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
                viewModel.saveFile(FileModel(it, AttachmentType.IMAGE))
            }
        }

        binding.remove.setOnClickListener {
            viewModel.saveFile(null)
        }

        binding.pickFile.setOnClickListener {
            pickImage.launch("image/*")
        }
        val imageUri = createFileUri()
        val takePhoto =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
                if (success) {
                    viewModel.saveFile(FileModel(imageUri, AttachmentType.IMAGE))
                }
            }

        binding.takePhoto.setOnClickListener {
            takePhoto.launch(imageUri)
        }


        viewModel.state.onEach { state ->
            (state.status as? Status.Error)?.let {
                requireContext().applicationContext.toast(it.reason.getText(requireContext()), true)
                viewModel.consumeError()
            }


            val file = state.file
            when (file?.attachmentType) {
                AttachmentType.IMAGE -> {
                    binding.preview.isVisible = true
                    binding.photoPreview.setImageURI(file.uri)
                }

                AttachmentType.VIDEO -> TODO()
                AttachmentType.AUDIO -> TODO()
                null -> binding.preview.isGone = true
            }

            state.result?.let {
                requireActivity().supportFragmentManager.setFragmentResult(
                    POST_CREATED_RESULT, bundleOf()
                )
                findNavController().navigateUp()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        val toolbarViewModel by activityViewModels<ToolbarViewModel>()
        toolbarViewModel.saveClicked.filter { it }.onEach {
            val content = binding.content.text?.toString().orEmpty()
            if (content.isBlank()) {
                requireContext().applicationContext.toast(
                    R.string.post_empty_error.toString(), true
                )
            } else {
                viewModel.save(content)
            }
            toolbarViewModel.saveClicked(false)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        return binding.root
    }

    private fun createFileUri(): Uri {
        val directory = requireContext().cacheDir.resolve("file_picker").apply {
            mkdirs()
        }

        val file = directory.resolve("image.png")

        return FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            file
        )
    }

}