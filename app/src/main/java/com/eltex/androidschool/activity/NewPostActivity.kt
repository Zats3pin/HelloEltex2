package com.eltex.androidschool.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.ActivityNewPostBinding
import com.eltex.androidschool.utils.toast

class NewPostActivity : AppCompatActivity() {
    private var eventId: Long = -1
    private var eventContent: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
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