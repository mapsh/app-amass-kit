package com.mapsh.kotlinx.android

import android.app.Fragment
import android.content.Context
import android.view.View
import android.widget.Toast

/**
 * @author  mapsh on 2017/12/21 11:02.
 *
 */
/**
 * context
 */
fun Context.toast(text: CharSequence) = toast(context = this, text = text, duration = Toast.LENGTH_SHORT)

fun Context.longToast(text: CharSequence) = toast(context = this, text = text, duration = Toast.LENGTH_LONG)

/**
 * view
 */
fun View.toast(text: CharSequence) = context.toast(text)

fun View.longToast(text: CharSequence) = context.longToast(text)

/**
 * fragment
 */
fun Fragment.toast(text: CharSequence) = activity.toast(text)

fun Fragment.longToast(text: CharSequence) = activity.longToast(text)

fun android.support.v4.app.Fragment.toast(text: CharSequence) = activity!!.toast(text)
fun android.support.v4.app.Fragment.longToast(text: CharSequence) = activity!!.longToast(text)

internal fun toast(context: Context, text: CharSequence, duration: Int) {
    Toast.makeText(context.applicationContext, text, duration).show()
}