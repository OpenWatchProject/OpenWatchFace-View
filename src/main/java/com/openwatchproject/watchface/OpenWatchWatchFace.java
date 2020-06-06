package com.openwatchproject.watchface;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.openwatchproject.watchface.item.AbstractItem;
import com.openwatchproject.watchface.item.TapActionItem;

import java.util.ArrayList;
import java.util.Calendar;

public class OpenWatchWatchFace {
    /**
     * The OpenWatch WatchFace format version
     */
    private static final int VERSION = 1;

    /**
     * The width in pixels for which this WatchFace was designed for.
     */
    private int width;

    /**
     * The height in pixels for which this WatchFace was designed for.
     */
    private int height;

    /**
     * The items that form this WatchFace.
     */
    private ArrayList<AbstractItem> items;

    /**
     * The items that can start an action
     */
    private ArrayList<TapActionItem> tapActionItems;

    private String absolutePath;

    public OpenWatchWatchFace(int width, int height) {
        this.width = width;
        this.height = height;
        this.items = new ArrayList<>();
        this.tapActionItems = new ArrayList<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void addItem(AbstractItem item) {
        items.add(item);
    }

    public void addTapActionItem(TapActionItem item) {
        tapActionItems.add(item);
    }

    public void onTapAction(int viewCenterX, int viewCenterY, float touchX, float touchY, Context context) {
        for (TapActionItem item : tapActionItems) {
            if (item.onTapAction(viewCenterX, viewCenterY, touchX, touchY, context))
                break;
        }
    }

    public void draw(int viewCenterX, int viewCenterY, Canvas canvas, Calendar calendar, DataRepository dataRepository) {
        for (AbstractItem i : items) {
            if (i.isDrawable()) {
                i.draw(viewCenterX, viewCenterY, canvas, calendar, dataRepository);
            }
        }
    }
}
