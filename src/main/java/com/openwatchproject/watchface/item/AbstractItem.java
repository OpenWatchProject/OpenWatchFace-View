package com.openwatchproject.watchface.item;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.openwatchproject.watchface.DataRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class AbstractItem {
    private static final String TAG = "AbstractItem";

    /**
     * Indicates the (width) center for the item in pixels
     */
    final int centerX;

    /**
     * Indicates the (height) center for the item in pixels
     */
    final int centerY;

    /**
     * An array of frames that need to be displayed.
     * Frame count must be at least 1. If it's greater than 1, it's an animation.
     */
    private final ArrayList<Drawable> frames;

    public AbstractItem(int centerX, int centerY, ArrayList<Drawable> frames) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.frames = frames;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void addFrame(Drawable frame) {
        this.frames.add(frame);
    }

    public void addAllFrames(List<Drawable> frames) {
        this.frames.addAll(frames);
    }

    public Drawable getFrame() {
        if (frames.size() == 1) {
            return frames.get(0);
        }
        // TODO: Implement animations
        Log.d(TAG, "getFrame: Missing animation functionality!");
        return frames.get(0);
    }

    public boolean isDrawable() {
        return frames.size() > 0;
    }

    public ArrayList<Drawable> getFrames() {
        return frames;
    }

    public abstract void draw(int viewCenterX, int viewCenterY, Canvas canvas, Calendar calendar, DataRepository dataRepository);
}
