package com.shidou.hotelsharing

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.mapsh.glide.GlideApp
import com.shidou.base.BaseEmptyActivity
import com.shidou.dao.Self
import com.shidou.module.login.LoginOrRegisterActivity
import com.shidou.router.AppMainRouter
import com.shidou.utils.RxHelper
import io.objectbox.BoxStore
import kotlinx.android.synthetic.main.splash_activity.*

/**
 * Created by mapsh on 2017/7/19.
 */

/**1、从后台取得广告地址
 * 2、Glide加载广告图片
 *
 */

class SplashActivity : BaseEmptyActivity() {

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
                        skip()
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
        //查找是否有登录
        val user = BoxStore.getDefault().boxFor(Self::class.java)
                .query().build().findFirst()
        if (user == null) {
            startActivity(Intent(this, LoginOrRegisterActivity::class.java))
            finish()
        } else {
            AppMainRouter.launch(this, object : NavigationCallback {
                override fun onLost(postcard: Postcard?) {
                }

                override fun onFound(postcard: Postcard?) {
                }

                override fun onInterrupt(postcard: Postcard?) {
                }

                override fun onArrival(postcard: Postcard?) {
                    finish()
                }
            })
        }


    }

}
