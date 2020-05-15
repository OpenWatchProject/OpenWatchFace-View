package com.openwatchproject.watchface.item;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class StaticItem extends AbstractItem {
    public StaticItem(int centerX, int centerY, ArrayList<Drawable> frames) {
        super(centerX, centerY, frames);
    }

    @Override
    public void draw(int viewCenterX, int viewCenterY, Canvas canvas) {
        Drawable drawable = getFrame();
        int centerX = viewCenterX + this.centerX;
        int centerY = viewCenterY + this.centerY;
        int halfWidth = drawable.getIntrinsicWidth() / 2;
        int halfHeight = drawable.getIntrinsicHeight() / 2;

        drawable.setBounds(centerX - halfWidth, centerY - halfHeight, centerX + halfWidth, centerY + halfHeight);
        drawable.draw(canvas);
    }
}
