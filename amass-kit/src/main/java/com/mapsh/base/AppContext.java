package com.mapsh.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

/**
 *
 * @author mapsh
 * @date 2017/5/4
 */

@SuppressWarnings("unchecked")
@SuppressLint("PrivateApi")
public class AppContext {
    public static final Application INSTANCE;

    static {
        Application app = null;
        try {
            app = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
            if (app == null) {
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
            }
        } catch (final Exception e) {
            Log.e("App", "Failed to get current application from AppGlobals." + e.getMessage());
            try {
                app = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
            } catch (final Exception ex) {
                Log.e("App", "Failed to get current application from ActivityThread." + e.getMessage());
            }
        } finally {
            INSTANCE = app;
            if (INSTANCE != null) {
                init(INSTANCE);
            }
        }
    }

    private static Boolean isDebug = null;
    private static boolean isInit  = false;

    public static boolean isDebug() {
        return isInit && isDebug != null && isDebug;
    }

    public static void init(Context context) {
        if (context == null) return;
        if (isDebug == null) {
            isDebug = context.getApplicationInfo() != null
                    && (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;

            isInit = true;
        }
    }
}
