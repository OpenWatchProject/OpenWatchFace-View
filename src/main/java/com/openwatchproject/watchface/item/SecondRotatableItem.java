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
