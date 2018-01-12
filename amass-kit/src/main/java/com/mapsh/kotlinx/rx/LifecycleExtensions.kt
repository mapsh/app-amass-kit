package com.mapsh.kotlinx.rx

import android.arch.lifecycle.GenericLifecycleObserver
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import io.reactivex.disposables.Disposable

/**
 * @author  mapsh on 2017/12/26 15:20.
 *
 */

fun Disposable.disposedWith(owner: LifecycleOwner,
                            event: Lifecycle.Event = DisposeBagPlugins.defaultLifecycleDisposeEvent) {

    owner.lifecycle.addObserver(object : GenericLifecycleObserver {

        override fun onStateChanged(source: LifecycleOwner, e: Lifecycle.Event) {

            when (e) {
                Lifecycle.Event.ON_CREATE  -> {
                }
                Lifecycle.Event.ON_PAUSE   -> {
                    if (event == Lifecycle.Event.ON_PAUSE) {
                        removeObserverAndDispose(owner)
                    }
                }
                Lifecycle.Event.ON_START   -> {
                }
                Lifecycle.Event.ON_RESUME  -> {

                }
                Lifecycle.Event.ON_STOP    -> {
                    if (event == Lifecycle.Event.ON_STOP) {
                        removeObserverAndDispose(owner)
                    }
                }
                Lifecycle.Event.ON_DESTROY -> {
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        removeObserverAndDispose(owner)
                    }
                }
                Lifecycle.Event.ON_ANY     -> {
                }
            }

        }

        fun removeObserverAndDispose(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            dispose()
        }
    })
}