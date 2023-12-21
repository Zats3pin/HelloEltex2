package com.eltex.androidschool.fragments

import com.eltex.androidschool.utils.toast
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.eltex.androidschool.R

import com.eltex.androidschool.databinding.FragmentNewPostBinding
import com.eltex.androidschool.viewmodel.ToolbarViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach

class NewPostFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val binding = FragmentNewPostBinding.inflate(inflater,container,false)

        //val intentEventData = intent.getLongExtra("event_id", -1)
       // if (intentEventData != -1L) {
       //     eventId = intentEventData
       //     eventContent = intent.getStringExtra("event_content") ?: ""
       //     binding.content.setText(eventContent)
      //  } else {
       //     eventId = -1
      //  }

        val toolbarViewModel by activityViewModels<ToolbarViewModel>()

        toolbarViewModel.saveClicked
            .filter {it}
                .onEach{
                    val content = binding.content.text?.toString().orEmpty()

                    if (content.isNotBlank()) {
/**   setResult(
                            RESULT_OK,
                            Intent().putExtra(Intent.EXTRA_TEXT, content)
                                .putExtra("event_id", eventId)
                        )
                        finish()*/
                    } else {
                        //   не работает
                    // toast(R.string.post_empty_error, false)
                    }
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