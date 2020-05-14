package com.openwatchproject.watchface.item;

import android.graphics.Canvas;

import java.util.Calendar;

public class WeatherItem extends AbstractItem {
    private final Calendar calendar;

    public WeatherItem(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public void draw(int viewCenterX, int viewCenterY, Canvas canvas) {

    }
}
