package com.finite_la_kirilledia.notes

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

internal fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocusedView: View? = currentFocus
    currentFocusedView?.let {
        imm.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

internal fun Fragment.hideKeyboard() {
    requireActivity().hideKeyboard()
}