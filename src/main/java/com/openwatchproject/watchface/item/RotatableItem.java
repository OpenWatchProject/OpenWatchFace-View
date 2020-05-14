package com.openwatchproject.watchface.item;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public abstract class RotatableItem extends AbstractItem {

    /**
     * Indicates the factor for which the degrees will be multiplied/divided.
     * A value greater than 0 (rotationFactor > 0) will be multiplied.
     * A value less than 0 (rotationFactor < 0) will be divided.
     * A value equal to 0 will be ignored.
     *
     * Equivalent to ClockSkin's mulrotate
     *
     * Only valid if type == 0
     */
    int rotationFactor;

    public RotatableItem(int centerX, int centerY, ArrayList<Drawable> frames) {
        super(centerX, centerY, frames);
    }

    public void setRotationFactor(int rotationFactor) {
        this.rotationFactor = rotationFactor;
    }

    /**
     * @return float The angle that the frame should be rotated to
     */
    abstract float getAngle();

    @Override
    public void draw(int viewCenterX, int viewCenterY, Canvas canvas) {
        Drawable drawable = getFrame();
        int centerX = viewCenterX + this.centerX;
        int centerY = viewCenterY + this.centerY;
        int halfWidth = drawable.getIntrinsicWidth() / 2;
        int halfHeight = drawable.getIntrinsicHeight() / 2;

        drawable.setBounds(centerX - halfWidth, centerY - halfHeight, centerX + halfWidth, centerY + halfHeight);
        canvas.save();
        canvas.rotate(getAngle(), (float) centerX, (float) centerY);
        drawable.draw(canvas);
        canvas.restore();
    }
}
