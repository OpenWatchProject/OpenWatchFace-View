package com.openwatchproject.watchface.item;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;

import java.util.ArrayList;
import java.util.Calendar;

public abstract class RotatableItem extends AbstractItem {

    float angle;

    /**
     * Indicates the factor for which the degrees will be multiplied/divided.
     * A value greater than 0 (rotationFactor > 0) will be multiplied.
     * A value less than 0 (rotationFactor < 0) will be divided.
     * A value equal to 0 will be ignored.
     *
     * Equivalent to ClockSkin's mulrotate
     */
    int rotationFactor;

    /**
     * Indicates the direction for the analog hand.
     * 0 = Clockwise
     * 1 = Anti-Clockwise
     */
    int direction;

    public RotatableItem(int centerX, int centerY, ArrayList<Drawable> frames, float angle, int rotationFactor, int direction) {
        super(centerX, centerY, frames);
        this.angle = angle;
        this.rotationFactor = rotationFactor;
        this.direction = direction;
    }

    /**
     * @return float The angle that the frame should be rotated to
     * @param calendar
     * @param dataRepository
     */
    abstract float getAngle(Calendar calendar, DataRepository dataRepository);

    @Override
    public void draw(int viewCenterX, int viewCenterY, Canvas canvas, Calendar calendar, DataRepository dataRepository) {
        Drawable drawable = getFrame();
        int centerX = viewCenterX + this.centerX;
        int centerY = viewCenterY + this.centerY;
        int halfWidth = drawable.getIntrinsicWidth() / 2;
        int halfHeight = drawable.getIntrinsicHeight() / 2;

        drawable.setBounds(centerX - halfWidth, centerY - halfHeight, centerX + halfWidth, centerY + halfHeight);
        canvas.save();
        canvas.rotate(getAngle(calendar, dataRepository), (float) centerX, (float) centerY);
        drawable.draw(canvas);
        canvas.restore();
    }
}
