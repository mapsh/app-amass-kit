package com.niuub.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author  mapsh on 2017/12/29 16:32.
 *
 */
abstract class AbsAmassBaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppActivityManager.getAppManager().addActivity(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        AppActivityManager.getAppManager().removeActivity(this)
    }
}