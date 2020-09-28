package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;

import java.util.Calendar;
import java.util.List;

public class KCalRotatableItem extends RotatableItem {
    public KCalRotatableItem(int centerX, int centerY, List<Drawable> frames, float startAngle, float maxAngle, int direction) {
        super(centerX, centerY, frames, startAngle, maxAngle, direction);
    }

    @Override
    float getProgress(Calendar calendar, DataRepository dataRepository) {
        float analogKCal = dataRepository.getKCal();

        return Math.min(analogKCal / (float) dataRepository.getTargetKCal(), 1.0f);
    }
}
