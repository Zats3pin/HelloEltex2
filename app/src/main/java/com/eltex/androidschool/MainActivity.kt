package com.eltex.androidschool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.eltex.androidschool.databinding.ActivityMainBinding
import com.eltex.androidschool.model.Post
import com.eltex.androidschool.utils.toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)
        var post = Post(
            id = 1L,
            content = "Приглашаю провести уютный вечер за увлекательными играми! У нас есть несколько вариантов настолок, подходящих для любой компании.",
            author = "Lydia Westervelt",
            published = "11.05.22 11:21",
            likedByMe = false,
            link = "https://m2.material.io/components/cards",
            status = false,
            timeStatus = "16.05.22 12:00",
            eventMe = false,
        )
        bindPost(binding, post)

        binding.like.setOnClickListener {
            post = post.copy(likedByMe = !post.likedByMe)

            bindPost(binding, post)

        }

        binding.event.setOnClickListener {
            post = post.copy(eventMe = !post.eventMe)
            bindPost(binding, post)
        }

        binding.menu.setOnClickListener {
            toast(R.string.not_implemented, false)
        }

        binding.share.setOnClickListener {
            toast(R.string.not_implemented, false)
        }
    }

    private fun bindPost(binding: ActivityMainBinding, post: Post) {
        binding.author.text = post.author
        binding.content.text = post.content
        binding.published.text = post.published
        binding.initial.text = post.author.take(1)
        binding.link.text = post.link
        binding.status.text = if (post.status) {
            "Online"
        } else {
            "Offline"
        }
        binding.timeStatus.text = post.timeStatus
        binding.like.setIconResource(
            if (post.likedByMe) {
                R.drawable.favorit_like
            } else {
                R.drawable.baseline_favorite_border_24
            }
        )
        binding.like.text = if (post.likedByMe) {
            1
        } else {
            0
        }.toString()

        binding.event.setIconResource(
            if (post.eventMe) {
                R.drawable.event_true
            } else {
                R.drawable.event
            }
        )
        binding.event.text = if (post.eventMe) {
            1
        } else {
            0
        }.toString()
    }
}

