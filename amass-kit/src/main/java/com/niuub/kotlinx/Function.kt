package com.niuub.kotlinx

/**
 * @author  mapsh on 2017/12/21 14:27.
 *
 */
fun <T> select(isTrue: Boolean, param1: () -> T, param2: () -> T) = if (isTrue) param1() else param2()

fun <T> select(isTrue: Boolean, param1: T, param2: T) = if (isTrue) param1 else param2