package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class SecondShadowRotatableItem extends RotatableItem {
    private final Calendar calendar;

    public SecondShadowRotatableItem(int centerX, int centerY, int direction, ArrayList<Drawable> frames, float angle, int rotationFactor, Calendar calendar) {
        super(centerX, centerY, frames, angle, rotationFactor, direction);
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
