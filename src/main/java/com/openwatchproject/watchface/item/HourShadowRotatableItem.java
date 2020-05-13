package com.openwatchproject.watchface.item;

import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.Calendar;

public class HourShadowRotatableItem extends RotatableItem {
    private final Calendar calendar;

    public HourShadowRotatableItem(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    float getAngle() {
        float analogHourShadow = (float) calendar.get(Calendar.HOUR) + ((float) calendar.get(Calendar.MINUTE) / (float) 60) + ((float) calendar.get(Calendar.SECOND) / (float) 60 / (float) 60);
        float hourShadowAngle = angle + (rotationFactor * ((float) analogHourShadow * (float) 360 / (float) 12));
        return hourShadowAngle;
    }
}
