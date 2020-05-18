package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;
import com.openwatchproject.watchface.OpenWatchWatchFaceConstants;

import java.util.ArrayList;
import java.util.Calendar;

public class SecondShadowRotatableItem extends RotatableItem {
    public SecondShadowRotatableItem(int centerX, int centerY, int direction, ArrayList<Drawable> frames, float angle, int rotationFactor) {
        super(centerX, centerY, frames, angle, rotationFactor, direction);
    }

    @Override
    float getAngle(Calendar calendar, DataRepository dataRepository) {
        float analogSecondShadow = (float) calendar.get(Calendar.SECOND) + ((float) calendar.get(Calendar.MILLISECOND) / 1000);
        if (direction == OpenWatchWatchFaceConstants.DIRECTION_REVERSE) {
            analogSecondShadow = -analogSecondShadow;
        }
        float secondShadowAngle = angle + (rotationFactor * ((float) analogSecondShadow / (float) 60 * 360));
        return secondShadowAngle;
    }
}
