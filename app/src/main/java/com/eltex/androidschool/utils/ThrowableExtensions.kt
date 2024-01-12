package com.eltex.androidschool.utils

import android.content.Context
import com.eltex.androidschool.R
import java.io.FileNotFoundException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException

fun Throwable.getText(context: Context): String = when (this) {
       is IOException -> context.getString(R.string.network_error)
        else -> context.getString(R.string.unknown_error)

}
