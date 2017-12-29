package com.mapsh.kotlinx.android.app

import android.app.Fragment
import android.support.v4.app.FragmentTransaction


/**
 * @author  mapsh on 2017/12/21 10:41.
 *
 */
inline fun Fragment.fragmentTransaction(function: android.app.FragmentTransaction.() -> android.app.FragmentTransaction) {
    fragmentManager.beginTransaction()
            .function()
            .commit()
}

inline fun android.support.v4.app.Fragment.fragmentTransaction(function: FragmentTransaction.() -> android.support.v4.app.FragmentTransaction) {
    fragmentManager!!.beginTransaction()
            .function()
            .commit()
}