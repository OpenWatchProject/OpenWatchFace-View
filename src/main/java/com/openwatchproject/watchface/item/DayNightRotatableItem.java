package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class DayNightRotatableItem extends RotatableItem {
    public DayNightRotatableItem(int centerX, int centerY, int direction, ArrayList<Drawable> frames, float angle, int rotationFactor) {
        super(centerX, centerY, frames, angle, rotationFactor, direction);
    }

    @Override
    float getAngle(Calendar calendar, DataRepository dataRepository) {
        float hour24 = calendar.get(Calendar.HOUR_OF_DAY) + ((float) calendar.get(Calendar.MINUTE) / (float) 60) + ((float) calendar.get(Calendar.SECOND) / (float) 60 / (float) 60);
        if (direction == OpenWatchWatchFaceConstants.DIRECTION_REVERSE) {
            hour24 = -hour24;
        }
        float hour24Angle = angle + (rotationFactor * ((float) hour24 / (float) 24 * 360));
        return hour24Angle;
    }
}
