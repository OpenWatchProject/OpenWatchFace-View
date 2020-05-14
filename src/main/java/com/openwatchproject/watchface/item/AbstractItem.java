package com.openwatchproject.watchface.item;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractItem {
    /**
     * Indicates the (width) center for the item in pixels
     */
    int centerX;

    /**
     * Indicates the (height) center for the item in pixels
     */
    int centerY;

    /**
     * Indicates the direction for the analog hand.
     * 0 = Clockwise
     * 1 = Anti-Clockwise
     *
     * Only valid if type == 0!
     */
    int direction;

    /**
     * Indicates the package containing the activity to be launched.
     *
     * Only valid if type == 100!
     *
     * Equivalent to ClockSkin's pkg
     */
    String packageName;

    /**
     * Indicates the class for the activity to be launched.
     *
     * Only valid if type == 100 and packageName is valid!
     *
     * Equivalent to ClockSkin's cls
     */
    String className;

    /**
     * Indicates the range (radius) for the invisible button
     * that this item represents.
     *
     * Only valid if type == 100, packageName is valid and className is valid!
     */
    int range;

    /**
     * An array of frames that need to be displayed
     * Frame count must be at least 1. If it's greater than 1, it's an animation.
     */
    private ArrayList<Drawable> frames;

    int color;
    String colorArray;
    String drawable;
    float offsetAngle;
    int rotate;
    int startAngle;
    int textSize;
    int duration;
    int count;
    String valueType;
    float progressDiliverArc;
    int progressdiliverCount;
    int progressRadius;
    String progressStroken;
    String pictureShadow;
    int frameDuration;
    String childFolder;
    List<String> drawables;
    float angle;
    int width;
    int radius;
    int repeat;

    public AbstractItem(int centerX, int centerY, ArrayList<Drawable> frames) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.frames = frames;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void addFrame(Drawable frame) {
        this.frames.add(frame);
    }

    public void addAllFrames(List<Drawable> frames) {
        this.frames.addAll(frames);
    }

    public Drawable getFrame() {
        // TODO: Implement animations
        return frames.get(0);
    }

    public ArrayList<Drawable> getFrames() {
        return frames;
    }

    public abstract void draw(int viewCenterX, int viewCenterY, Canvas canvas);
}
