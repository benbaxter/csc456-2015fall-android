package edu.nku.csc456.fall2015.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.widget.TextView;

/**
 * Created by Benjamin on 8/23/2015.
 */
public class TextViewCompat {

    @SuppressWarnings("deprecation")
    public static void setTextAppearance(TextView textView, Context context, int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextAppearance(android.R.style.TextAppearance_DeviceDefault);
        } else {
            textView.setTextAppearance(context, android.R.style.TextAppearance_DeviceDefault);
        }
    }
}
