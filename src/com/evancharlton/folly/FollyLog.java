
package com.evancharlton.folly;

import android.util.Log;

public final class FollyLog {
    private static final String TAG = "Folly";

    private FollyLog() {
    }

    public static void d(String msg, Object... args) {
        if (args != null && args.length > 0) {
            msg = String.format(msg, args);
        }
        Log.d(TAG, msg);
    }

    public static void e(Throwable t, String msg, Object... args) {
        if (args != null && args.length > 0) {
            msg = String.format(msg, args);
        }
        Log.e(TAG, msg, t);
    }
}
