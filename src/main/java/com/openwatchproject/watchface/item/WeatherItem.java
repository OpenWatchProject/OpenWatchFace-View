package com.openwatchproject.watchface.item;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceView;

import java.util.ArrayList;
import java.util.Calendar;

public class WeatherItem extends AbstractItem {
    private final Calendar calendar;

    public WeatherItem(int centerX, int centerY, ArrayList<Drawable> frames, Calendar calendar) {
        super(centerX, centerY, frames);
        this.calendar = calendar;
    }

    @Override
    public void draw(int viewCenterX, int viewCenterY, Canvas canvas, Calendar calendar, DataRepository dataRepository) {

    }
}
