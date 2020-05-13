package com.openwatchproject.watchface.item;

public class StepsTargetRotatableItem extends RotatableItem {
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
