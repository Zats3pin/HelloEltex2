package com.eltex.androidschool.utils
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.toast(@StringRes res: Int, short: Boolean) {
    requireContext().toast(res.toString(), short)
}

