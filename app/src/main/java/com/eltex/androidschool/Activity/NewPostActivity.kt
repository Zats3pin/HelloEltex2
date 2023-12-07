package com.eltex.androidschool.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.ActivityNewPostBinding
import com.eltex.androidschool.utils.toast

class NewPostActivity : AppCompatActivity() {
    private var eventNewId: Long = -1
    private var eventNewContent: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val intentEventData = intent.getLongExtra("event_id", -1)
        if (intentEventData != -1L) {
            eventNewId = intentEventData
            eventNewContent = intent.getStringExtra("event_content") ?: ""
            binding.content.setText(eventNewContent)
        } else {
            eventNewId = -1
        }


        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.save -> {
                    val content = binding.content.text?.toString().orEmpty()

                    if (content.isNotBlank()) {
                        setResult(
                            RESULT_OK, Intent().putExtra(Intent.EXTRA_TEXT, content)
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
        }

    }
}