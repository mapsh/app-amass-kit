package com.niuub.kotlinx

import android.app.Fragment
import android.content.Context
import android.view.View

/**
 * Created by mapsh on 2017/8/17.
 */

/*
  ---------- context ----------
 */

/**
 * screen width in pixels
 */
val Context.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

/**
 * screen height in pixels
 */
val Context.screenHeight: Int
    get() = resources.displayMetrics.heightPixels

fun Context.dip2px(dip: Int): Int {
    val scale = resources.displayMetrics.density
    return (dip * scale + 0.5f).toInt()
}

fun Context.px2dip(px: Int): Int {
    val scale = resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

fun Context.sp2px(sp: Int): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (sp * fontScale + 0.5f).toInt()
}

fun Context.px2sp(px: Int): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (px / fontScale + 0.5f).toInt()
}

/*
 ---------- view ----------
 */
fun View.dip2px(value: Int): Int = context.dip2px(value)

fun View.px2dip(value: Int): Int = context.px2dip(value)

fun View.sp2px(value: Int): Int = context.sp2px(value)

fun View.px2sp(value: Int): Int = context.px2sp(value)

/*
 ---------- fragment ----------
 */
fun Fragment.dip2px(value: Int): Int = activity.dip2px(value)

fun Fragment.px2dip(value: Int): Int = activity.px2dip(value)

fun Fragment.sp2px(value: Int): Int = activity.sp2px(value)

fun Fragment.px2sp(value: Int): Int = activity.px2sp(value)

fun android.support.v4.app.Fragment.dip2px(value: Int): Int = activity?.dip2px(value) ?: 0

fun android.support.v4.app.Fragment.px2dip(value: Int): Int = activity?.px2dip(value) ?: 0

fun android.support.v4.app.Fragment.sp2px(value: Int): Int = activity?.sp2px(value) ?: 0

fun android.support.v4.app.Fragment.px2sp(value: Int): Int = activity?.px2sp(value) ?: 0
