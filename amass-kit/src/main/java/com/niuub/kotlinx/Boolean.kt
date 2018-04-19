package com.niuub.kotlinx

sealed class BooleanExt<out T > constructor(val b: Boolean)

object Otherwise : com.niuub.kotlinx.BooleanExt<Nothing>(true)

class WithData<out T>(val data: T) : com.niuub.kotlinx.BooleanExt<T>(false)

inline fun <T> Boolean.yes(block: () -> T): com.niuub.kotlinx.BooleanExt<T> = when {
    this -> {
        com.niuub.kotlinx.WithData(block())
    }
    else -> com.niuub.kotlinx.Otherwise
}

inline fun <T> Boolean.no(block: () -> T) = when {
    this -> com.niuub.kotlinx.Otherwise
    else -> {
        com.niuub.kotlinx.WithData(block())
    }
}

inline infix fun <T> com.niuub.kotlinx.BooleanExt<T>.otherwise(block: () -> T): T = when (this) {
    com.niuub.kotlinx.Otherwise      -> block()
    is com.niuub.kotlinx.WithData<T> -> this.data
}

inline operator fun <T> Boolean.invoke(block: () -> T) = yes(block)