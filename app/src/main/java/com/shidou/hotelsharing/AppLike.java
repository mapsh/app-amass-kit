package com.shidou.hotelsharing;


import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.squareup.leakcanary.LeakCanary;

import java.util.List;

import timber.log.Timber;


/**
 * lyc
 */

public class AppLike extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //如果不是当前主进程，不做下面的初始化
        if (!BuildConfig.APPLICATION_ID.equals(getProcessName(this, android.os.Process.myPid()))) {
            return;
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        if (BuildConfig.DEBUG) {
            // 开启Stetho调试工具
            // must be done on the FragmentUi thread
            ARouter.openDebug();
        }
        ARouter.init(this);

        if (LeakCanary.isInAnalyzerProcess(this)) {
        } else {
            LeakCanary.install(this);
        }

        //数据库初始化




        //EventBus 索引
        //EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();

    }


    @Nullable
    private static String getProcessName(Context context, int pid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null && !runningApps.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == pid) {
                    return procInfo.processName;
                }
            }
        }
        return null;
    }



}
