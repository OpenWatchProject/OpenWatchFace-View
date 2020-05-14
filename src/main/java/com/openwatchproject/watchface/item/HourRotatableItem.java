package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class HourRotatableItem extends RotatableItem {
    private final Calendar calendar;

    public HourRotatableItem(int centerX, int centerY, ArrayList<Drawable> frames, Calendar calendar) {
        super(centerX, centerY, frames);
        this.calendar = calendar;
    }

    @Override
    float getAngle() {
        float hourAngle;

        float analogHour = (float) calendar.get(Calendar.HOUR)
                + ((float) calendar.get(Calendar.MINUTE) / (float) 60)
                + ((float) calendar.get(Calendar.SECOND) / (float) 60 / (float) 60);
        if (direction == OpenWatchWatchFaceConstants.DIRECTION_REVERSE) {
            hourAngle = angle + (rotationFactor * ((float) analogHour * (float) 360 / (float) 12));
        } else {
            hourAngle = angle - (rotationFactor * ((float) analogHour * (float) 360 / (float) 12));
        }

        return hourAngle;
    }
}
