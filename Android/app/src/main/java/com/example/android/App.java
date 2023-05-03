package com.example.android;

import android.app.Application;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(mailTo = "incomeclean@gmail.com",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);

    }

}
