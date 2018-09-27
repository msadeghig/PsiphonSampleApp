package ee.traxnet.psiphonsampleapp;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.common.logging.MoPubLog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

public class TraxnetApplication extends Application {

    static {
        MoPubLog.setSdkHandlerLevel(Level.ALL);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!MoPub.isSdkInitialized()) {
            SdkConfiguration sdkConfiguration =
                    new SdkConfiguration.Builder(BuildConfig.rewardedVideoAdUnitId).build();
            MoPub.initializeSdk(this, sdkConfiguration, initSdkListener());

        }
    }

    private SdkInitializationListener initSdkListener() {
        return new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {
                LogUtils.LOGI("MoPub initialized");
            }
        };
    }
}
