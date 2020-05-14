package com.openwatchproject.watchface.item;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.Calendar;
import java.util.List;

public class MonthDayItem extends AbstractItem {
    private final Calendar calendar;

    public MonthDayItem(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public void draw(int viewCenterX, int viewCenterY, Canvas canvas) {
        List<Drawable> drawables = getFrames();
        int month = calendar.get(Calendar.MONTH) + 1;
        Drawable m10 = drawables.get(month / 10);
        Drawable m1 = drawables.get(month % 10);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Drawable d10 = drawables.get(day / 10);
        Drawable d1 = drawables.get(day % 10);
        Drawable separator = drawables.get(10);

        int centerX = viewCenterX + this.centerX;
        int centerY = viewCenterY + this.centerY;
        int numberWidth = m10.getIntrinsicWidth();
        int numberHalfHeight = m10.getIntrinsicHeight() / 2;
        int top = centerY - numberHalfHeight;
        int bottom = centerY + numberHalfHeight;
        int separatorHalfWidth = separator.getIntrinsicWidth() / 2;
        m10.setBounds((centerX - separatorHalfWidth) - (numberWidth * 2), top, (centerX - separatorHalfWidth) - numberWidth, bottom);
        m10.draw(canvas);
        m1.setBounds((centerX - separatorHalfWidth) - numberWidth, top, (centerX - separatorHalfWidth), bottom);
        m1.draw(canvas);
        separator.setBounds(centerX - separatorHalfWidth, top, centerX + separatorHalfWidth, bottom);
        separator.draw(canvas);
        d10.setBounds((centerX + separatorHalfWidth), top, (centerX + separatorHalfWidth) + numberWidth, bottom);
        d10.draw(canvas);
        d1.setBounds((centerX + separatorHalfWidth) + numberWidth, top, (centerX + separatorHalfWidth) + (numberWidth * 2), bottom);
        d1.draw(canvas);
    }
}
