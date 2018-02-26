package com.mapsh.kotlinx.rx

import android.arch.lifecycle.GenericLifecycleObserver
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

/**
 * @author  mapsh on 2017/12/26 15:19.
 *
 */

fun Disposable.disposedBy(bag: DisposeBag) = bag.add(this)

private object DisposeBagPlugins {
    @JvmStatic
    var defaultLifecycleDisposeEvent = Lifecycle.Event.ON_DESTROY
}

class DisposeBag @JvmOverloads constructor(owner: LifecycleOwner,
                                           private val event: Lifecycle.Event = DisposeBagPlugins.defaultLifecycleDisposeEvent)
    : Disposable, DisposableContainer, GenericLifecycleObserver {

    @JvmOverloads constructor(resources: Iterable<Disposable>,
                              owner: LifecycleOwner,
                              event: Lifecycle.Event = DisposeBagPlugins.defaultLifecycleDisposeEvent)
            : this(owner, event) {

        resources.forEach { composite.add(it) }
    }

    private val lifecycle = owner.lifecycle

    private val composite by lazy { CompositeDisposable() }

    init {
        lifecycle.addObserver(this)
    }

    override fun isDisposed() = composite.isDisposed

    override fun dispose() {
        lifecycle.removeObserver(this)
        composite.dispose()
    }

    override fun add(d: Disposable) = composite.add(d)

    override fun remove(d: Disposable) = composite.remove(d)

    override fun delete(d: Disposable) = composite.delete(d)


    override fun onStateChanged(source: LifecycleOwner, correspondingEvent: Lifecycle.Event) {

        if (event != correspondingEvent) return

        when (correspondingEvent) {
            Lifecycle.Event.ON_CREATE  -> {
            }
            Lifecycle.Event.ON_PAUSE   -> {
                dispose()
            }
            Lifecycle.Event.ON_START   -> {
            }
            Lifecycle.Event.ON_RESUME  -> {

            }
            Lifecycle.Event.ON_STOP    -> {
                dispose()

            }
            Lifecycle.Event.ON_DESTROY -> {
                dispose()
            }
            Lifecycle.Event.ON_ANY     -> {
            }
        }
    }

}

fun Disposable.disposedWith(owner: LifecycleOwner,
                            event: Lifecycle.Event = DisposeBagPlugins.defaultLifecycleDisposeEvent) {

    owner.lifecycle.addObserver(object : GenericLifecycleObserver {

        override fun onStateChanged(source: LifecycleOwner, correspondingEvent: Lifecycle.Event) {

            if (event != correspondingEvent) return

            when (correspondingEvent) {
                Lifecycle.Event.ON_CREATE  -> {
                }
                Lifecycle.Event.ON_PAUSE   -> {
                    removeObserverAndDispose(owner)
                }
                Lifecycle.Event.ON_START   -> {

                }
                Lifecycle.Event.ON_RESUME  -> {
                }
                Lifecycle.Event.ON_STOP    -> {
                    removeObserverAndDispose(owner)
                }
                Lifecycle.Event.ON_DESTROY -> {
                    removeObserverAndDispose(owner)
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