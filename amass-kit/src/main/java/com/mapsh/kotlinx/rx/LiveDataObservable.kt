package com.mapsh.kotlinx.rx

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

/**
 * @author mapsh on 2017/12/15 10:08.
 */

class LiveDataObservable<T>(private val mOwner: LifecycleOwner?, private val mLiveData: LiveData<T>) :
        Observable<T>() {

    override fun subscribeActual(observer: Observer<in T>) {
        val liveDataObserver = LiveDataObserver(mLiveData, observer)
        observer.onSubscribe(liveDataObserver)
        if (mOwner == null) {
            mLiveData.observeForever(liveDataObserver)
        } else {
            mLiveData.observe(mOwner, liveDataObserver)
        }
    }

    private class LiveDataObserver<T>(private val liveData: LiveData<T>, private val observer: Observer<in T>) :
            MainThreadDisposable(),
            android.arch.lifecycle.Observer<T> {

        override fun onDispose() {
            liveData.removeObserver(this)
        }

        override fun onChanged(t: T?) {
            if (t == null) return
            observer.onNext(t)
        }
    }
}


fun <T> LiveData<T>.toObservable(owner: LifecycleOwner): Observable<T> =
        LiveDataObservable(owner, this)

fun <T> LiveData<T>.toForeverObservable(): Observable<T> = LiveDataObservable(null, this)
