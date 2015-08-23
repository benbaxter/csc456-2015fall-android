package edu.nku.csc456.fall2015;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import edu.nku.csc456.fall2015.service.Csc456ApiService;

/**
 * Created by Benjamin on 8/23/2015.
 */
public class Csc456Application extends Application {

    private static Csc456Application instance;
    private Tracker tracker;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
    }

    public static Csc456Application getInstance() {
        return instance;
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            tracker = analytics.newTracker(R.xml.global_tracker);
        }
        return tracker;
    }
}
