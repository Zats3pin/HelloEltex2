package com.eltex.androidschool.di

import androidx.fragment.app.Fragment

fun Fragment.getDependencyContainer() =
    (requireContext().applicationContext as AppComponent)
        .getContainer()
