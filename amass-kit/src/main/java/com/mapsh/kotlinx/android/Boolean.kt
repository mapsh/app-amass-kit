package com.mapsh.kotlinx.android

sealed class BooleanExt<out T > constructor(val b: Boolean)

object Otherwise : BooleanExt<Nothing>(true)

class WithData<out T>(val data: T) : BooleanExt<T>(false)

inline fun <T> Boolean.yes(block: () -> T): BooleanExt<T> = when {
    this -> {
        WithData(block())
    }
    else -> Otherwise
}

inline fun <T> Boolean.no(block: () -> T) = when {
    this -> Otherwise
    else -> {
        WithData(block())
    }
}

inline infix fun <T> BooleanExt<T>.otherwise(block: () -> T): T = when (this) {
    Otherwise      -> block()
    is WithData<T> -> this.data
}

inline operator fun <T> Boolean.invoke(block: () -> T) = yes(block)