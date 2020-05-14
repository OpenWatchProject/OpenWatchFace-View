package com.openwatchproject.watchface.item;

import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.Calendar;

public class DayNightRotatableItem extends RotatableItem {
    private final Calendar calendar;

    public DayNightRotatableItem(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    float getAngle() {
        float hour24 = calendar.get(Calendar.HOUR_OF_DAY) + ((float) calendar.get(Calendar.MINUTE) / (float) 60) + ((float) calendar.get(Calendar.SECOND) / (float) 60 / (float) 60);
        if (direction == OpenWatchWatchFaceConstants.DIRECTION_REVERSE) {
            hour24 = -hour24;
        }
        float hour24Angle = angle + (rotationFactor * ((float) hour24 / (float) 24 * 360));
        return hour24Angle;
    }
}