package com.openwatchproject.watchface.item;

import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.Calendar;

public class SecondRotatableItem extends RotatableItem {
    private final Calendar calendar;

    public SecondRotatableItem(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    float getAngle() {
        float secondAngle;
        float analogSecond = (float) calendar.get(Calendar.SECOND) + ((float) calendar.get(Calendar.MILLISECOND) / (float) 1000);
        if (rotationFactor > 0) {
            //analogSecond *= mulRotate;
        } else if (rotationFactor < 0) {
            //analogSecond /= (float) mulRotate;
        }
        if (direction == OpenWatchWatchFaceConstants.DIRECTION_REVERSE) {
            analogSecond = -analogSecond;
        }
        secondAngle = angle + (rotationFactor * ((float) analogSecond / (float) 60 * 360));
        return secondAngle;
    }
}
