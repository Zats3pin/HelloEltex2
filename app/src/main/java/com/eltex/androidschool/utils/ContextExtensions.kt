package com.eltex.androidschool.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.eltex.androidschool.R


fun Context.toast(@StringRes res: Int, short: Boolean) {
    val length = if (short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    Toast.makeText(this, R.string.not_implemented, length).show()
}

