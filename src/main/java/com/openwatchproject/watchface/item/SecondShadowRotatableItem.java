package com.openwatchproject.watchface.item;

import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.Calendar;

public class SecondShadowRotatableItem extends RotatableItem {
    private final Calendar calendar;

    public SecondShadowRotatableItem(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    float getAngle() {
        float analogSecondShadow = (float) calendar.get(Calendar.SECOND) + ((float) calendar.get(Calendar.MILLISECOND) / 1000);
        if (direction == OpenWatchWatchFaceConstants.DIRECTION_REVERSE) {
            analogSecondShadow = -analogSecondShadow;
        }
        float secondShadowAngle = angle + (rotationFactor * ((float) analogSecondShadow / (float) 60 * 360));
        return secondShadowAngle;
    }
}
