package com.mapsh.base

import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * @author  mapsh on 2017/12/29 16:32.
 *
 */
abstract class InitActivity : RxAppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppActivityManager.getAppManager().addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppActivityManager.getAppManager().removeActivity(this)
    }
}