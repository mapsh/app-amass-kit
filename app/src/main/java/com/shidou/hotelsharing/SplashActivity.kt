package com.shidou.hotelsharing

import android.graphics.drawable.Drawable
import android.os.Bundle
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.mapsh.base.InitActivity
import com.mapsh.glide.GlideApp
import com.mapsh.kotlinx.rx.disposedWith
import com.mapsh.utils.RxHelper
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.splash_activity.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Created by mapsh on 2017/7/19.
 */

/**1、从后台取得广告地址
 * 2、Glide加载广告图片
 *
 */

class SplashActivity : InitActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        tvSkip.setOnClickListener {
            skip()
        }




        RxHelper.countdown(5)
                .compose(RxHelper.bindToLifecycle(this))
                .compose(RxHelper.io2Main())
                .subscribe {
                    if (it == 4) {
                        load()
                    }
                    if (it >= 1) {
                        tvSkip.text = String.format("%d 秒跳过", it)
                    } else {
                      //  skip()
                    }
                }
    }

    private fun load() {
        GlideApp
                .with(this)
                .load(R.drawable.ad)
                .into(object : DrawableImageViewTarget(imgAdvertising) {
                    override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
                        super.onResourceReady(resource, transition)
                        imgAdvertising.isClickable = true
                    }
                })
    }

    private fun skip() {

        Flowable.interval(1, TimeUnit.SECONDS)
                .subscribe {
                    Timber.e("$it")
                }.disposedWith(this)


    }

}
