package ee.traxnet.psiphonsampleapp;

import android.content.Context;
import android.util.Log;

public class LogUtils {
    private static final String TAG = "PsiphonSampleApp";

    public static void LOGE(String message) {
        Log.e(TAG, message);
    }

    public static void LOGI(String message) {
        Log.i(TAG, message);
    }

    public static void LOGW(String message) {
        Log.w(TAG, message);
    }


}
