package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class SecondRotatableItem extends RotatableItem {
    public SecondRotatableItem(int centerX, int centerY, int direction, ArrayList<Drawable> frames, float angle, int rotationFactor) {
        super(centerX, centerY, frames, angle, rotationFactor, direction);
    }

    @Override
    float getAngle(Calendar calendar, DataRepository dataRepository) {
        float secondAngle;
        float analogSecond = (float) calendar.get(Calendar.SECOND)
                + ((float) calendar.get(Calendar.MILLISECOND) / 1000.0f);

        if (direction == OpenWatchWatchFaceConstants.DIRECTION_NORMAL) {
            secondAngle = angle + ((analogSecond / 60.0f) * 360.0f * ((float) rotationFactor));
        } else {
            secondAngle = angle - ((analogSecond / 60.0f) * 360.0f * ((float) rotationFactor));
        }

        return secondAngle;
    }
}
