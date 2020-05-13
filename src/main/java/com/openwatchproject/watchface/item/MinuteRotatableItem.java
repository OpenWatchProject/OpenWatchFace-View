package com.openwatchproject.watchface.item;

import java.util.Calendar;

public class MinuteRotatableItem extends RotatableItem {
    private final Calendar calendar;

    public MinuteRotatableItem(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    float getAngle() {
        float minuteAngle;
        float analogMinute = (float) calendar.get(Calendar.MINUTE) + ((float) calendar.get(Calendar.SECOND) / (float) 60);

        minuteAngle = angle + (rotationFactor * ((float) analogMinute / (float) 60 * 360));

        return minuteAngle;
    }
}
