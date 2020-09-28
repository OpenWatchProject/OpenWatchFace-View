package com.openwatchproject.watchface.item;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;

import java.util.List;
import java.util.Calendar;
import java.util.List;

public class MonthItem extends AbstractItem {
    public MonthItem(int centerX, int centerY, List<Drawable> frames) {
        super(centerX, centerY, frames);
    }

    @Override
    public void draw(int viewCenterX, int viewCenterY, Canvas canvas, Calendar calendar, DataRepository dataRepository) {
        List<Drawable> drawables = getFrames();
        int month = calendar.get(Calendar.MONTH);
        Drawable drawable = drawables.get(month);

        int centerX = viewCenterX + this.centerX;
        int centerY = viewCenterY + this.centerY;
        int halfWidth = drawable.getIntrinsicWidth() / 2;
        int halfHeight = drawable.getIntrinsicHeight() / 2;
        int top = centerY - halfHeight;
        int bottom = centerY + halfHeight;
        drawable.setBounds(centerX - halfWidth, top, centerX + halfWidth, bottom);
        drawable.draw(canvas);
    }
}
