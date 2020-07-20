package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class MinuteRotatableItem extends RotatableItem {
    public MinuteRotatableItem(int centerX, int centerY, int direction, ArrayList<Drawable> frames, float angle, int rotationFactor) {
        super(centerX, centerY, frames, angle, rotationFactor, direction);
    }

    @Override
    float getAngle(Calendar calendar, DataRepository dataRepository) {
        float minuteAngle;
        float analogMinute = (float) calendar.get(Calendar.MINUTE)
                + ((float) calendar.get(Calendar.SECOND) / 60.0f);

        if (direction == OpenWatchWatchFaceConstants.DIRECTION_NORMAL) {
            minuteAngle = angle + ((analogMinute / 60.0f) * 360.0f * ((float) rotationFactor));
        } else {
            minuteAngle = angle - ((analogMinute / 60.0f) * 360.0f * ((float) rotationFactor));
        }

        return minuteAngle;
    }
}
