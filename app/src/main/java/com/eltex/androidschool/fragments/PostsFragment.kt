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

import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

import com.eltex.androidschool.adapter.EventsAdapter
import com.eltex.androidschool.adapter.OffsetDecoration

import com.eltex.androidschool.databinding.FragmentEventsBinding

import com.eltex.androidschool.db.AppDb
import com.eltex.androidschool.model.Event
import com.eltex.androidschool.repository.SqliteEventRepository
import com.eltex.androidschool.utils.toast

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PostsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false)
    }


}


