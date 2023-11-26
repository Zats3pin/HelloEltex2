package com.eltex.androidschool.model.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.eltex.androidschool.R


fun Context.toast(@StringRes res: Int, short: Boolean) {
    if(short) {
        Toast.makeText(this, R.string.not_implemented, Toast.LENGTH_SHORT).show()
    }else
    {Toast.makeText(this, R.string.not_implemented, Toast.LENGTH_LONG).show()}
}