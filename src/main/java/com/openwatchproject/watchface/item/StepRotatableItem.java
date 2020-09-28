package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;

import java.util.Calendar;
import java.util.List;

public class StepRotatableItem extends RotatableItem {
    public StepRotatableItem(int centerX, int centerY, List<Drawable> frames, float startAngle, float maxAngle, int direction) {
        super(centerX, centerY, frames, startAngle, maxAngle, direction);
    }

    @Override
    float getProgress(Calendar calendar, DataRepository dataRepository) {
        float analogStep = dataRepository.getSteps();

        return Math.min(analogStep / (float) dataRepository.getTargetSteps(), 1.0f);
    }
}
