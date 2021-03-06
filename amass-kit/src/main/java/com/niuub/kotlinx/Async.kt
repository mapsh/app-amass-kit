package com.niuub.kotlinx

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * Execute [f] on the application UI thread.
 */
fun Context.runOnUiThread(f: Context.() -> Unit) {
    if (com.niuub.kotlinx.ContextHelper.mainThread == Thread.currentThread()) f() else com.niuub.kotlinx.ContextHelper.mainHandler.post { f() }
}

/**
 * Execute [f] on the application UI thread.
 */
inline fun Fragment.runOnUiThread(crossinline f: () -> Unit) {
    activity?.runOnUiThread { f() }
}

class AnkoAsyncContext<T>(val weakRef: WeakReference<T>)

/**
 * Execute [f] on the application UI thread.
 * If the [doAsync] receiver still exists (was not collected by GC),
 *  [f] gets it as a parameter ([f] gets null if the receiver does not exist anymore).
 */
fun <T> com.niuub.kotlinx.AnkoAsyncContext<T>.onComplete(f: (T?) -> Unit) {
    val ref = weakRef.get()
    if (com.niuub.kotlinx.ContextHelper.mainThread == Thread.currentThread()) {
        f(ref)
    } else {
        com.niuub.kotlinx.ContextHelper.mainHandler.post { f(ref) }
    }
}

/**
 * Execute [f] on the application UI thread.
 * [doAsync] receiver will be passed to [f].
 * If the receiver does not exist anymore (it was collected by GC), [f] will not be executed.
 */
fun <T> com.niuub.kotlinx.AnkoAsyncContext<T>.uiThread(f: (T) -> Unit): Boolean {
    val ref = weakRef.get() ?: return false
    if (com.niuub.kotlinx.ContextHelper.mainThread == Thread.currentThread()) {
        f(ref)
    } else {
        com.niuub.kotlinx.ContextHelper.mainHandler.post { f(ref) }
    }
    return true
}

/**
 * Execute [f] on the application UI thread if the underlying [Activity] still exists and is not finished.
 * The receiver [Activity] will be passed to [f].
 *  If it is not exist anymore or if it was finished, [f] will not be called.
 */
fun <T : Activity> com.niuub.kotlinx.AnkoAsyncContext<T>.activityUiThread(f: (T) -> Unit): Boolean {
    val activity = weakRef.get() ?: return false
    if (activity.isFinishing) return false
    activity.runOnUiThread { f(activity) }
    return true
}

fun <T : Activity> com.niuub.kotlinx.AnkoAsyncContext<T>.activityUiThreadWithContext(f: Context.(T) -> Unit): Boolean {
    val activity = weakRef.get() ?: return false
    if (activity.isFinishing) return false
    activity.runOnUiThread { activity.f(activity) }
    return true
}



fun <T : Fragment> com.niuub.kotlinx.AnkoAsyncContext<T>.fragmentUiThread(f: (T) -> Unit): Boolean {
    val fragment = weakRef.get() ?: return false
    if (fragment.isDetached) return false
    val activity = fragment.activity ?: return false
    activity.runOnUiThread { f(fragment) }
    return true
}

fun <T : Fragment> com.niuub.kotlinx.AnkoAsyncContext<T>.fragmentUiThreadWithContext(f: Context.(T) -> Unit): Boolean {
    val fragment = weakRef.get() ?: return false
    if (fragment.isDetached) return false
    val activity = fragment.activity ?: return false
    activity.runOnUiThread { activity.f(fragment) }
    return true
}

private val crashLogger = { throwable: Throwable -> throwable.printStackTrace() }
/**
 * Execute [task] asynchronously.
 *
 * @param exceptionHandler optional exception mainHandler.
 *  If defined, any exceptions thrown inside [task] will be passed to it. If not, exceptions will be ignored.
 * @param task the code to execute asynchronously.
 */
fun <T> T.doAsync(
        exceptionHandler: ((Throwable) -> Unit)? = com.niuub.kotlinx.crashLogger,
        task: com.niuub.kotlinx.AnkoAsyncContext<T>.() -> Unit
): Future<Unit> {
    val context = com.niuub.kotlinx.AnkoAsyncContext(WeakReference(this))
    return com.niuub.kotlinx.BackgroundExecutor.submit {
        return@submit try {
            context.task()
        } catch (thr: Throwable) {
            val result = exceptionHandler?.invoke(thr)
            if (result != null) {
                result
            } else {
                Unit
            }
        }
    }
}

fun <T> T.doAsync(
        exceptionHandler: ((Throwable) -> Unit)? = com.niuub.kotlinx.crashLogger,
        executorService: ExecutorService,
        task: com.niuub.kotlinx.AnkoAsyncContext<T>.() -> Unit
): Future<Unit> {
    val context = com.niuub.kotlinx.AnkoAsyncContext(WeakReference(this))
    return executorService.submit<Unit> {
        try {
            context.task()
        } catch (thr: Throwable) {
            exceptionHandler?.invoke(thr)
        }
    }
}

fun <T, R> T.doAsyncResult(
        exceptionHandler: ((Throwable) -> Unit)? = com.niuub.kotlinx.crashLogger,
        task: com.niuub.kotlinx.AnkoAsyncContext<T>.() -> R
): Future<R> {
    val context = com.niuub.kotlinx.AnkoAsyncContext(WeakReference(this))
    return com.niuub.kotlinx.BackgroundExecutor.submit {
        try {
            context.task()
        } catch (thr: Throwable) {
            exceptionHandler?.invoke(thr)
            throw thr
        }
    }
}

fun <T, R> T.doAsyncResult(
        exceptionHandler: ((Throwable) -> Unit)? = com.niuub.kotlinx.crashLogger,
        executorService: ExecutorService,
        task: com.niuub.kotlinx.AnkoAsyncContext<T>.() -> R
): Future<R> {
    val context = com.niuub.kotlinx.AnkoAsyncContext(WeakReference(this))
    return executorService.submit<R> {
        try {
            context.task()
        } catch (thr: Throwable) {
            exceptionHandler?.invoke(thr)
            throw thr
        }
    }
}

internal object BackgroundExecutor {
    private var executor: ExecutorService =
            Executors.newScheduledThreadPool(2 * Runtime.getRuntime().availableProcessors())

    fun <T> submit(task: () -> T): Future<T> = com.niuub.kotlinx.BackgroundExecutor.executor.submit(task)
}

private object ContextHelper {
    val mainHandler = Handler(Looper.getMainLooper())
    val mainThread: Thread = Looper.getMainLooper().thread
}