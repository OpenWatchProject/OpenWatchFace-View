package com.openwatchproject.watchface.item;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;

public class TapActionItem extends StaticItem {
    private static final String TAG = "TapActionItem";

    /**
     * Indicates the package containing the activity to be launched.
     *
     * Only valid if type == 100!
     *
     * Equivalent to ClockSkin's pkg
     */
    private String packageName;

    /**
     * Indicates the class for the activity to be launched.
     *
     * Only valid if type == 100 and packageName is valid!
     *
     * Equivalent to ClockSkin's cls
     */
    private String className;

    /**
     * Indicates the range (radius) for the invisible button
     * that this item represents.
     *
     * Only valid if type == 100, packageName is valid and className is valid!
     */
    private int range;

    public TapActionItem(int centerX, int centerY, ArrayList<Drawable> frames) {
        super(centerX, centerY, frames);
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public boolean onTapAction(int viewCenterX, int viewCenterY, float touchX, float touchY, Context context) {
        int x = viewCenterX + centerX;
        int y = viewCenterY + centerY;

        if (distance(touchX, touchY, x, y) <= range) {
            Intent i = new Intent();
            i.setComponent(new ComponentName(packageName, className));
            try {
                context.startActivity(i);
            } catch (ActivityNotFoundException e) {
                Log.d(TAG, "onTapAction: tried to open a non-existent activity: packageName = " + packageName + ", className = " + className);
            }

            return true;
        }

        return false;
    }

    private static int distance(float x1, float y1, float x2, float y2) {
        float x = x2 - x1;
        float y = y2 - y1;

        return (int) Math.round(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
    }
}
