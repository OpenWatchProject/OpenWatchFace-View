package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;

import java.util.ArrayList;
import java.util.Calendar;

public class MinuteShadowRotatableItem extends RotatableItem {
    public MinuteShadowRotatableItem(int centerX, int centerY, ArrayList<Drawable> frames, float startAngle, float maxAngle, int direction) {
        super(centerX, centerY, frames, startAngle, maxAngle, direction);
    }

    @Override
    float getProgress(Calendar calendar, DataRepository dataRepository) {
        float analogMinuteShadow = (float) calendar.get(Calendar.MINUTE)
                + ((float) calendar.get(Calendar.SECOND) / 60.0f);

        return analogMinuteShadow / 60.0f;
    }
}
