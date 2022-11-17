package ru.effectivemobile.ui.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Build
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat


fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

fun View.addKeyboardInsetListener() {
    val window = (context as Activity).window
    val decor = window.decorView
    val thisView = this

    ViewCompat.setOnApplyWindowInsetsListener(decor) { v, insets ->
        val returnted = ViewCompat.onApplyWindowInsets(v, insets)
        val visibilityState = if (insets.keyboardVisible()) View.GONE else View.VISIBLE
        thisView.visibility = visibilityState
        returnted
    }
}


fun WindowInsetsCompat.keyboardVisible() = isVisible(WindowInsetsCompat.Type.ime())


fun EditText.textToInt() = text.toString().toIntWithHandleErrors()

fun String.toIntWithHandleErrors() = try {
    toInt()
} catch (e: Exception) {
    0
}