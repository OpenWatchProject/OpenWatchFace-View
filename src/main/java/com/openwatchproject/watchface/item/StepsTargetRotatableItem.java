package com.openwatchproject.watchface.item;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class StepsTargetRotatableItem extends RotatableItem {
    public StepsTargetRotatableItem(int centerX, int centerY, ArrayList<Drawable> frames) {
        super(centerX, centerY, frames);
    }

    @Override
    float getAngle() {
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
