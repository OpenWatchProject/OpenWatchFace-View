package com.openwatchproject.watchface;

import android.graphics.Canvas;

import com.openwatchproject.watchface.item.AbstractItem;

import java.util.ArrayList;
import java.util.Calendar;

public class OpenWatchWatchFace {
    private final Calendar calendar;

    /**
     * The width in pixels for which this WatchFace was designed for.
     */
    private int width = 400;

    /**
     * The height in pixels for which this WatchFace was designed for.
     */
    private int height = 400;

    /**
     * The items that form this WatchFace.
     */
    private ArrayList<AbstractItem> items;

    public OpenWatchWatchFace() {
        this.calendar = Calendar.getInstance();
        this.items = new ArrayList<>();
    }

    public void setCalendarTime(long millis) {
        calendar.setTimeInMillis(millis);
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

    public void draw(int viewCenterX, int viewCenterY, Canvas canvas) {
        for (AbstractItem i : items) {
            i.draw(viewCenterX, viewCenterY, canvas);
        }
    }
}
