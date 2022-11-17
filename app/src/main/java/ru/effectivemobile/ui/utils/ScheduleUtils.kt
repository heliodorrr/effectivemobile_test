package ru.effectivemobile.ui.utils

import android.os.Handler
import android.os.Looper

internal val MainHandler = Handler(Looper.getMainLooper())

fun postDelayed(delay: Long, action: ()->Unit) {
    MainHandler.postDelayed(action, delay)
}