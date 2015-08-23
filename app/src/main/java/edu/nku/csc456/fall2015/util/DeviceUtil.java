package edu.nku.csc456.fall2015.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Created by Benjamin on 8/23/2015.
 */
public class DeviceUtil {

    public static boolean isLarge(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        float scale = metrics.density;
        int w = metrics.widthPixels;
        int h = metrics.heightPixels;
        float wdp = w / scale;
        float hdp = h / scale;

        return wdp >= 600;
    }
}
