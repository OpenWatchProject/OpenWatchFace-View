package com.openwatchproject.watchface.item;

import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.Calendar;

public class DayOfWeekRotatableItem extends RotatableItem {
    private final Calendar calendar;

    public DayOfWeekRotatableItem(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    float getAngle() {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 2; // Sunday = -2, Monday = -1, Tuesday = 0, etc
        if (direction == OpenWatchWatchFaceConstants.DIRECTION_REVERSE) {
            dayOfWeek = -dayOfWeek;
        }
        float dayOfWeekAngle = angle + (rotationFactor * ((float) dayOfWeek / (float) 7 * 360));
        return dayOfWeekAngle;
    }
}
