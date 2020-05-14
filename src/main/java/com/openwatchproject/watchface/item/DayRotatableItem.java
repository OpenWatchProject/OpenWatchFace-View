package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class DayRotatableItem extends RotatableItem {
    private final Calendar calendar;

    public DayRotatableItem(int centerX, int centerY, ArrayList<Drawable> frames, Calendar calendar) {
        super(centerX, centerY, frames);
        this.calendar = calendar;
    }

    @Override
    float getAngle() {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (direction == OpenWatchWatchFaceConstants.DIRECTION_REVERSE) {
            day = -day;
        }
        float dayAngle = angle + (rotationFactor * ((float) day / (float) 31 * 360));
        return dayAngle;
    }
}
