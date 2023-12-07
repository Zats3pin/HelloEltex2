package com.eltex.androidschool.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eltex.androidschool.R
import com.eltex.androidschool.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.save ->{
                 val content = binding.content.text?.toString().orEmpty()

                    true
                }
                else -> false
            }
        }
    }
}