@file:Suppress("NOTHING_TO_INLINE")

package com.niuub.kotlinx.view

import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.IntRange
import android.support.annotation.UiThread
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

/**
 * Sets the view's visibility to GONE
 */
inline fun View.gone() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

/**
 * Sets the view's visibility to VISIBLE
 */
inline fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

/**
 * Sets the view's visibility to INVISIBLE
 */
inline fun View.invisible() {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
}

/**
 * Toggle's view's visibility. If View is visible, then sets to gone. Else sets Visible
 */
inline fun View.toggle() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}

/**
 * 为view添加OnGlobalLayoutListener监听并在测量完成后自动取消监听同时执行[action]函数
 *
 * @param action
 */
inline fun <T : View> T.afterMeasured(crossinline action: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                action()
            }
        }
    })
}

@UiThread
inline fun View.fadeIn(duration: Long = 400) {
    this.clearAnimation()
    val anim = AlphaAnimation(this.alpha, 1.0f)
    anim.duration = duration
    this.startAnimation(anim)
}

@UiThread
inline fun View.fadeOut(duration: Long = 400) {
    this.clearAnimation()
    val anim = AlphaAnimation(this.alpha, 0.0f)
    anim.duration = duration
    this.startAnimation(anim)
}

fun View.getColor(@ColorRes id: Int) = ContextCompat.getColor(context, id)

fun View.getDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(context, id)

@UiThread
fun View.slideToUp(@IntRange(from = 0) duration: Long = 400, listener: Animation.AnimationListener?) {
    com.niuub.anim.AnimationUtils.slideToUp(this, duration, listener)
}

@UiThread
fun View.slideToDown(@IntRange(from = 0) duration: Long = 400, listener: Animation.AnimationListener?) {
    com.niuub.anim.AnimationUtils.slideToDown(this, duration, listener)
}
