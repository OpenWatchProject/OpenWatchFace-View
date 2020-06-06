package com.openwatchproject.watchface.item;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.openwatchproject.watchface.DataRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MoonPhaseItem extends AbstractItem {
    public MoonPhaseItem(int centerX, int centerY, ArrayList<Drawable> frames) {
        super(centerX, centerY, frames);
    }

    @Override
    public void draw(int viewCenterX, int viewCenterY, Canvas canvas, Calendar calendar, DataRepository dataRepository) {
        List<Drawable> drawables = getFrames();
        int moonPhase = getMoonPhaseAngle(calendar);
        int drawableCount = drawables.size();
        Drawable mp = drawables.get(Math.round((float) moonPhase / (360.0f / (float) drawableCount)));

        int centerX = viewCenterX + this.centerX;
        int centerY = viewCenterY + this.centerY;
        int halfWidth = mp.getIntrinsicWidth() / 2;
        int halfHeight = mp.getIntrinsicHeight() / 2;

        mp.setBounds(centerX - halfWidth, centerY - halfHeight, centerX + halfWidth, centerY + halfHeight);
        mp.draw(canvas);
    }

    private static int getMoonPhaseAngle(Calendar calendar) {
        // TODO: Improve this. Changes every hour, no need to calculate every time it's drawn
        return (int) (360.0d * ((((217 + (((calendar.get(Calendar.YEAR) % 100) - 15) * 365) + calendar.get(Calendar.DAY_OF_YEAR) + calendar.get(Calendar.HOUR_OF_DAY) - 0.569444d) * 1.000663562d) / 29.53059d) % 1));
    }
}
