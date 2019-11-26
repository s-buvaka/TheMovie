package com.wispapp.themovie.core.application

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.showKeyboard(isShow: Boolean) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (isShow) imm.showSoftInput(this, 0)
    else imm.hideSoftInputFromWindow(windowToken, 0)
}