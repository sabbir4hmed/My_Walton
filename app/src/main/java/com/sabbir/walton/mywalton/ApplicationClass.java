package com.sabbir.walton.mywalton;

import android.app.Application;

import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

/*THis is push notification Class*/


public class ApplicationClass extends Application {
    private static final String ONESIGNAL_APP_ID = "55f3e8a7-9836-4e2c-8608-ceb55ed444c0";

    @Override
    public void onCreate() {
        super.onCreate();

        // OneSignal Initialization
        OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID);

        // Enable notifications with sound and vibration
        OneSignal.getNotifications().clearAllNotifications();
        OneSignal.getNotifications().requestPermission(true, Continue.none());

    }
}
