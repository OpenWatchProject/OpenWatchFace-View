package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekdayRotatableItem extends RotatableItem {
    public WeekdayRotatableItem(int centerX, int centerY, int direction, ArrayList<Drawable> frames, float angle, int rotationFactor) {
        super(centerX, centerY, frames, angle, rotationFactor, direction);
    }

    @Override
    float getAngle(Calendar calendar, DataRepository dataRepository) {
        float weekdayAngle;
        int analogWeekday = calendar.get(Calendar.DAY_OF_WEEK) - 2; // Sunday = -2, Monday = -1, Tuesday = 0, etc

        if (direction == OpenWatchWatchFaceConstants.DIRECTION_NORMAL) {
            weekdayAngle = angle + ((((float) analogWeekday) / 7.0f) * 360.0f * ((float) rotationFactor));
        } else {
            weekdayAngle = angle - ((((float) analogWeekday) / 7.0f) * 360.0f * ((float) rotationFactor));
        }

        return weekdayAngle;
    }
}
