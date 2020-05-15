package com.openwatchproject.watchface.item;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class TapActionItem extends StaticItem {
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

    public String getPackageName() {
        return packageName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getRange() {
        return range;
    }
}
