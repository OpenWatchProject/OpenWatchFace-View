package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;

import java.util.ArrayList;
import java.util.Calendar;

public class MinuteShadowRotatableItem extends RotatableItem {
    public MinuteShadowRotatableItem(int centerX, int centerY, int direction, ArrayList<Drawable> frames, float angle, int rotationFactor) {
        super(centerX, centerY, frames, angle, rotationFactor, direction);
    }

    @Override
    float getAngle(Calendar calendar, DataRepository dataRepository) {
        float analogMinuteShadow = (float) calendar.get(Calendar.MINUTE) + ((float) calendar.get(Calendar.SECOND) / (float) 60);
        float minuteShadowAngle = angle + (rotationFactor * ((float) analogMinuteShadow / (float) 60 * 360));
        return minuteShadowAngle;
    }
}
