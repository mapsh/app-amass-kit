package com.shidou.hotelsharing;

/**
 * @author mapsh on 2017/12/5 09:23.
 */

public class FakeCrashLibrary {

    public static void log(int priority, String tag, String message) {

    }

    public static void logWarning(Throwable t) {

    }

    public static void logError(Throwable t) {

    }

    private FakeCrashLibrary() {
        throw new AssertionError("No instances.");
    }
}
