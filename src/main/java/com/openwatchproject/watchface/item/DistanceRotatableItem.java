package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;

import java.util.Calendar;
import java.util.List;

public class DistanceRotatableItem extends RotatableItem {
    public DistanceRotatableItem(int centerX, int centerY, List<Drawable> frames, float startAngle, float maxAngle, int direction) {
        super(centerX, centerY, frames, startAngle, maxAngle, direction);
    }

    @Override
    float getProgress(Calendar calendar, DataRepository dataRepository) {
        float analogDistance = dataRepository.getDistance();

        return Math.min(analogDistance / (float) dataRepository.getTargetDistance(), 1.0f);
    }
}
