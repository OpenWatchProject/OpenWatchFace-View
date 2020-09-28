package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;

import java.util.List;
import java.util.Calendar;

public class SecondRotatableItem extends RotatableItem {
    public SecondRotatableItem(int centerX, int centerY, List<Drawable> frames, float startAngle, float maxAngle, int direction) {
        super(centerX, centerY, frames, startAngle, maxAngle, direction);
    }

    @Override
    float getProgress(Calendar calendar, DataRepository dataRepository) {
        float analogSecond = (float) calendar.get(Calendar.SECOND)
                + ((float) calendar.get(Calendar.MILLISECOND) / 1000.0f);

        return analogSecond / 60.0f;
    }
}
