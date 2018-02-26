package com.mapsh.base

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mapsh.rx.disposedWith
import io.reactivex.Observable

/**
 * @author  mapsh on 2017/12/29 16:32.
 *
 */
abstract class InitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppActivityManager.getAppManager().addActivity(this)

        Observable.just("").subscribe().disposedWith(this,Lifecycle.Event.ON_PAUSE)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppActivityManager.getAppManager().removeActivity(this)
    }
}