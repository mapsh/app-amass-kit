package com.mapsh.kotlinx.android.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.widget.Toast

/**
 * Created by mapsh on 2017/8/17.
 */
internal object CommonImpl {

    fun toast(context: Context, text: CharSequence, duration: Int) {
        Toast.makeText(context.applicationContext, text, duration).show()
    }

    fun dip2px(context: Context, dip: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dip * scale + 0.5f).toInt()
    }

    fun px2dip(context: Context, px: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }

    fun sp2px(context: Context, sp: Int): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (sp * fontScale + 0.5f).toInt()
    }

    fun px2sp(context: Context, px: Int): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (px / fontScale + 0.5f).toInt()
    }

    fun loadColor(context: Context, @ColorRes resid: Int): Int {
        return ContextCompat.getColor(context, resid)
    }

    fun loadDrawable(context: Context, @DrawableRes resid: Int): Drawable {
        return ContextCompat.getDrawable(context, resid)!!
    }

}