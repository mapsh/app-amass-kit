package com.shidou.hotelsharing;


import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.stetho.Stetho;
import com.shidou.dao.DaoHelper;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

import java.util.List;

import io.objectbox.android.AndroidObjectBrowser;
import timber.log.Timber;


/**
 * lyc
 */

public class AppLike extends DefaultApplicationLike {

    public AppLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        //如果不是当前主进程，不做下面的初始化
        if (!BuildConfig.APPLICATION_ID.equals(getProcessName(getApplication(), android.os.Process.myPid()))) {
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
            Stetho.initializeWithDefaults(getApplication());
            ARouter.openDebug();
        }
        ARouter.init(getApplication());

        if (LeakCanary.isInAnalyzerProcess(getApplication())) {
        } else {
            LeakCanary.install(getApplication());
        }

        //数据库初始化

        DaoHelper.initDatabase(getApplication());
        //BoxStore boxStore = MyObjectBox.builder().androidContext(getApplication()).buildDefault();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(DaoHelper.getBoxStore()).start(getApplication());
        }

        //Bugly初始化
        BuglyConfig.init(getApplication());
        //百度地图初始化
        SDKInitializer.initialize(getApplication());

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

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

}
