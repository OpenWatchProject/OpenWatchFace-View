package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthRotatableItem extends RotatableItem {
    private final Calendar calendar;

    public MonthRotatableItem(int centerX, int centerY, ArrayList<Drawable> frames, Calendar calendar) {
        super(centerX, centerY, frames);
        this.calendar = calendar;
    }

    @Override
    float getAngle() {
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH starts at 0
        if (direction == OpenWatchWatchFaceConstants.DIRECTION_REVERSE) {
            month = -month;
        }
        float monthAngle = angle + (rotationFactor * ((float) month / (float) 12 * 360));
        return monthAngle;
    }
}
