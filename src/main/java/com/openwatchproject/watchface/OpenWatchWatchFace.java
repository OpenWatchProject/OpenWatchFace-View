package com.openwatchproject.watchface;

import android.graphics.Canvas;

import com.openwatchproject.watchface.item.AbstractItem;
import com.openwatchproject.watchface.item.TapActionItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class OpenWatchWatchFace {
    private final Calendar calendar;

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

    public OpenWatchWatchFace() {
        this.calendar = Calendar.getInstance();
        this.width = 400;
        this.height = 400;
        this.items = new ArrayList<>();
        this.tapActionItems = new ArrayList<>();
    }

    public void setCalendarTime(long millis) {
        calendar.setTimeInMillis(millis);
    }

    public void setCalendarTimezone(TimeZone timezone) {
        calendar.setTimeZone(timezone);
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void addItem(AbstractItem item) {
        items.add(item);
    }

    public ArrayList<AbstractItem> getItems() {
        return items;
    }

    public void addTapActionItem(TapActionItem item) {
        tapActionItems.add(item);
    }

    public ArrayList<TapActionItem> getTapActionItems() {
        return tapActionItems;
    }

    public void draw(int viewCenterX, int viewCenterY, Canvas canvas) {
        for (AbstractItem i : items) {
            i.draw(viewCenterX, viewCenterY, canvas);
        }
    }
}
