package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;

import java.util.ArrayList;
import java.util.Calendar;

public class StepsTargetRotatableItem extends RotatableItem {
    public StepsTargetRotatableItem(int centerX, int centerY, ArrayList<Drawable> frames, float startAngle, float maxAngle, int direction) {
        super(centerX, centerY, frames, startAngle, maxAngle, direction);
    }

    @Override
    float getProgress(Calendar calendar, DataRepository dataRepository) {
        /*if (dataRepository == null) break;
        int steps = dataRepository.getSteps();
        int targetSteps = dataRepository.getTargetSteps();
        if (direction == OpenWatchWatchFaceConstants.DIRECTION_REVERSE) {
            steps = -steps;
        }
        float stepsAngle = angle + (rotationFactor * ((float) steps / (float) targetSteps * 360));
        return stepsAngle;*/
        return 0;
    }
}
