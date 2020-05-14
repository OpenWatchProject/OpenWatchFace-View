package com.openwatchproject.watchface;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.openwatchproject.watchface.item.AbstractItem;
import com.openwatchproject.watchface.item.DayItem;
import com.openwatchproject.watchface.item.DayOfWeekItem;
import com.openwatchproject.watchface.item.HourItem;
import com.openwatchproject.watchface.item.HourMinuteItem;
import com.openwatchproject.watchface.item.MinuteItem;
import com.openwatchproject.watchface.item.MonthDayItem;
import com.openwatchproject.watchface.item.MonthItem;
import com.openwatchproject.watchface.item.RotatableItem;
import com.openwatchproject.watchface.item.SecondItem;
import com.openwatchproject.watchface.item.StaticItem;
import com.openwatchproject.watchface.item.YearMonthDayItem;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

public class OpenWatchFaceDeserializer {
    private final OpenWatchWatchFaceFile watchFaceFile;
    private final Resources resources;
    private OpenWatchWatchFace watchFace;

    public OpenWatchFaceDeserializer(OpenWatchWatchFaceFile watchFaceFile, Resources resources) {
        this.watchFaceFile = watchFaceFile;
        this.resources = resources;
    }

    public OpenWatchWatchFace deserialize() {
        JsonObject json;
        try (InputStreamReader isr = new InputStreamReader(watchFaceFile.getWatchFaceJson())) {
            json = JsonParser.parseReader(isr).getAsJsonObject();
        } catch (IOException e) {
            return null;
        }

        watchFace = new OpenWatchWatchFace();
        watchFace.setWidth(json.get("width").getAsInt());
        watchFace.setHeight(json.get("height").getAsInt());

        JsonArray items = json.get("items").getAsJsonArray();
        for (JsonElement itemElement : items) {
            JsonObject itemObject = itemElement.getAsJsonObject();
            AbstractItem item = parseItem(itemObject);
            if (item != null) {
                watchFace.addItem(item);
            }
        }

        return watchFace;
    }

    private AbstractItem parseItem(JsonObject json) {
        AbstractItem item = null;
        int type = json.get("type").getAsInt();
        int centerX = json.get("centerX").getAsInt();
        int centerY = json.get("centerY").getAsInt();
        ArrayList<Drawable> frames = parseFrames(json);
        Calendar calendar = watchFace.getCalendar();

        switch (type) {
            case OpenWatchWatchFaceConstants.TYPE_STATIC:
                item = new StaticItem(centerX, centerY, frames);
                parseStaticItem((StaticItem) item, json);
                break;
            case OpenWatchWatchFaceConstants.TYPE_ROTATABLE:
                item = parseRotatableItem(centerX, centerY, frames, json);
                break;
            case OpenWatchWatchFaceConstants.TYPE_YEAR_MONTH_DAY:
                item = new YearMonthDayItem(centerX, centerY, frames, calendar);
                break;
            case OpenWatchWatchFaceConstants.TYPE_MONTH_DAY:
                item = new MonthDayItem(centerX, centerY, frames, calendar);
                break;
            case OpenWatchWatchFaceConstants.TYPE_MONTH:
                item = new MonthItem(centerX, centerY, frames, calendar);
                break;
            case OpenWatchWatchFaceConstants.TYPE_DAY:
                item = new DayItem(centerX, centerY, frames, calendar);
                break;
            case OpenWatchWatchFaceConstants.TYPE_WEEKDAY:
                item = new DayOfWeekItem(centerX, centerY, frames, calendar);
                break;
            case OpenWatchWatchFaceConstants.TYPE_HOUR_MINUTE:
                item = new HourMinuteItem(centerX, centerY, frames, calendar);
                break;
            case OpenWatchWatchFaceConstants.TYPE_HOUR:
                item = new HourItem(centerX, centerY, frames, calendar);
                break;
            case OpenWatchWatchFaceConstants.TYPE_MINUTE:
                item = new MinuteItem(centerX, centerY, frames, calendar);
                break;
            case OpenWatchWatchFaceConstants.TYPE_SECOND:
                item = new SecondItem(centerX, centerY, frames, calendar);
                break;
            case OpenWatchWatchFaceConstants.TYPE_WEATHER:
                break;
            case OpenWatchWatchFaceConstants.TYPE_TEMPERATURE:
                break;
            case OpenWatchWatchFaceConstants.TYPE_STEPS:
                break;
            case OpenWatchWatchFaceConstants.TYPE_HEART_RATE:
                break;
            case OpenWatchWatchFaceConstants.TYPE_BATTERY:
                break;
            case OpenWatchWatchFaceConstants.TYPE_SPECIAL_SECOND:
                break;
            case OpenWatchWatchFaceConstants.TYPE_YEAR:
                break;
            case OpenWatchWatchFaceConstants.TYPE_BATTERY_CIRCLE:
                break;
            case OpenWatchWatchFaceConstants.TYPE_STEPS_CIRCLE:
                break;
            case OpenWatchWatchFaceConstants.TYPE_MOON_PHASE:
                break;
            case OpenWatchWatchFaceConstants.TYPE_AM_PM:
                break;
            case OpenWatchWatchFaceConstants.TYPE_FRAME_ANIMATION:
                break;
            case OpenWatchWatchFaceConstants.TYPE_ROTATE_ANIMATION:
                break;
            case OpenWatchWatchFaceConstants.TYPE_SNOW_ANIMATION:
                break;
            case OpenWatchWatchFaceConstants.TYPE_BATTERY_CIRCLE_PIC:
                break;
            case OpenWatchWatchFaceConstants.TYPE_PICTURE_HOUR:
                break;
            case OpenWatchWatchFaceConstants.TYPE_PICTURE_MINUTER:
                break;
        }

        return item;
    }

    private ArrayList<Drawable> parseFrames(JsonObject json) {
        ArrayList<Drawable> frames = new ArrayList<>();
        JsonArray frameArray = json.get("frames").getAsJsonArray();

        for (JsonElement je : frameArray) {
            String frame = je.getAsString();
            try (InputStream is = watchFaceFile.getZippedFile(frame)) {
                frames.add(new BitmapDrawable(resources, BitmapFactory.decodeStream(is)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return frames;
    }

    private void parseStaticItem(StaticItem item, JsonObject json) {

    }

    private RotatableItem parseRotatableItem(int centerX, int centerY, ArrayList<Drawable> frames, JsonObject itemObject) {
        RotatableItem item = null;
        int rotatableType = itemObject.get("rotatableType").getAsInt();

        switch (rotatableType) {
            case OpenWatchWatchFaceConstants.ROTATABLE_HOUR:

                break;
        }

        return item;
    }
}
