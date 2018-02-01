package com.mapsh.kotlinx.android

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * 软键盘操作 扩展函数
 *
 * @author  mapsh on 2017/12/21 10:51.
 *
 */

fun Activity.showSoftInput(focusView: View? = currentFocus): Boolean {
    focusView?.let {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return imm.showSoftInput(focusView, InputMethodManager.SHOW_IMPLICIT)
    }
    return false
}

fun Activity.hideSoftInput(focusView: View? = currentFocus): Boolean {
    focusView?.let {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(focusView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
    return false
}

fun EditText.showSoftInput() {
    requestFocus()
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}


fun Context.isSoftInputActive(): Boolean {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return inputMethodManager.isActive
}

