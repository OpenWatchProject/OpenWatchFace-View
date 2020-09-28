package com.openwatchproject.watchface;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.openwatchproject.watchface.item.AbstractItem;
import com.openwatchproject.watchface.item.BalanceRotatableItem;
import com.openwatchproject.watchface.item.BatteryRotatableItem;
import com.openwatchproject.watchface.item.DayItem;
import com.openwatchproject.watchface.item.DayRotatableItem;
import com.openwatchproject.watchface.item.DistanceRotatableItem;
import com.openwatchproject.watchface.item.KCalRotatableItem;
import com.openwatchproject.watchface.item.MoonPhaseItem;
import com.openwatchproject.watchface.item.Hour24RotatableItem;
import com.openwatchproject.watchface.item.DayOfWeekItem;
import com.openwatchproject.watchface.item.StepRotatableItem;
import com.openwatchproject.watchface.item.WeatherItem;
import com.openwatchproject.watchface.item.WeekdayRotatableItem;
import com.openwatchproject.watchface.item.HourItem;
import com.openwatchproject.watchface.item.HourMinuteItem;
import com.openwatchproject.watchface.item.HourRotatableItem;
import com.openwatchproject.watchface.item.HourShadowRotatableItem;
import com.openwatchproject.watchface.item.MinuteItem;
import com.openwatchproject.watchface.item.MinuteRotatableItem;
import com.openwatchproject.watchface.item.MinuteShadowRotatableItem;
import com.openwatchproject.watchface.item.MonthDayItem;
import com.openwatchproject.watchface.item.MonthItem;
import com.openwatchproject.watchface.item.MonthRotatableItem;
import com.openwatchproject.watchface.item.RotatableItem;
import com.openwatchproject.watchface.item.SecondItem;
import com.openwatchproject.watchface.item.SecondRotatableItem;
import com.openwatchproject.watchface.item.SecondShadowRotatableItem;
import com.openwatchproject.watchface.item.SpecialSecondItem;
import com.openwatchproject.watchface.item.StaticItem;
import com.openwatchproject.watchface.item.TapActionItem;
import com.openwatchproject.watchface.item.YearItem;
import com.openwatchproject.watchface.item.YearMonthDayItem;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OpenWatchFaceDeserializer {
    private static final String TAG = "OpenWatchFaceDeserializ";

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

        watchFace = new OpenWatchWatchFace(json.get("width").getAsInt(), json.get("height").getAsInt());
        watchFace.setAbsolutePath(watchFaceFile.getFile().getUri());

        JsonArray items = json.get("items").getAsJsonArray();
        for (JsonElement itemElement : items) {
            JsonObject itemObject = itemElement.getAsJsonObject();
            try {
                AbstractItem item = parseItem(itemObject);
                watchFace.addItem(item);
                if (item instanceof TapActionItem) {
                    watchFace.addTapActionItem((TapActionItem) item);
                }
            } catch (InvalidWatchFaceItemException e) {
                e.printStackTrace();
            }
        }

        return watchFace;
    }

    private AbstractItem parseItem(JsonObject json) throws InvalidWatchFaceItemException {
        AbstractItem item;
        int type;
        int centerX;
        int centerY;
        ArrayList<Drawable> frames = parseFrames(json);
        JsonElement tmp;

        tmp = json.get("type");
        if (tmp != null) {
            type = tmp.getAsInt();
        } else {
            throw new InvalidWatchFaceItemException();
        }

        tmp = json.get("center_x");
        if (tmp != null) {
            centerX = tmp.getAsInt();
        } else {
            throw new InvalidWatchFaceItemException();
        }

        tmp = json.get("center_y");
        if (tmp != null) {
            centerY = tmp.getAsInt();
        } else {
            throw new InvalidWatchFaceItemException();
        }

        switch (type) {
            case OpenWatchWatchFaceConstants.TYPE_STATIC:
                item = new StaticItem(centerX, centerY, frames);
                parseStaticItem((StaticItem) item, json);
                break;
            case OpenWatchWatchFaceConstants.TYPE_ROTATABLE:
                item = parseRotatableItem(centerX, centerY, frames, json);
                break;
            case OpenWatchWatchFaceConstants.TYPE_YEAR_MONTH_DAY:
                item = new YearMonthDayItem(centerX, centerY, frames);
                break;
            case OpenWatchWatchFaceConstants.TYPE_MONTH_DAY:
                item = new MonthDayItem(centerX, centerY, frames);
                break;
            case OpenWatchWatchFaceConstants.TYPE_MONTH:
                item = new MonthItem(centerX, centerY, frames);
                break;
            case OpenWatchWatchFaceConstants.TYPE_DAY:
                item = new DayItem(centerX, centerY, frames);
                break;
            case OpenWatchWatchFaceConstants.TYPE_WEEKDAY:
                item = new DayOfWeekItem(centerX, centerY, frames);
                break;
            case OpenWatchWatchFaceConstants.TYPE_HOUR_MINUTE:
                item = new HourMinuteItem(centerX, centerY, frames);
                break;
            case OpenWatchWatchFaceConstants.TYPE_HOUR:
                item = new HourItem(centerX, centerY, frames);
                break;
            case OpenWatchWatchFaceConstants.TYPE_MINUTE:
                item = new MinuteItem(centerX, centerY, frames);
                break;
            case OpenWatchWatchFaceConstants.TYPE_SECOND:
                item = new SecondItem(centerX, centerY, frames);
                break;
            case OpenWatchWatchFaceConstants.TYPE_WEATHER:
                item = new WeatherItem(centerX, centerY, frames);
                break;
            case OpenWatchWatchFaceConstants.TYPE_TEMPERATURE:
                Log.d(TAG, "parseItem: TYPE_TEMPERATURE requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_STEPS:
                Log.d(TAG, "parseItem: TYPE_STEPS requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_HEART_RATE:
                Log.d(TAG, "parseItem: TYPE_HEART_RATE requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_BATTERY:
                Log.d(TAG, "parseItem: TYPE_BATTERY requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_SPECIAL_SECOND:
                item = new SpecialSecondItem(centerX, centerY, frames);
                break;
            case OpenWatchWatchFaceConstants.TYPE_YEAR:
                item = new YearItem(centerX, centerY, frames);
                break;
            case OpenWatchWatchFaceConstants.TYPE_BATTERY_PICTURE_CIRCLE:
                Log.d(TAG, "parseItem: TYPE_BATTERY_PICTURE_CIRCLE requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_STEPS_PICTURE_CIRCLE:
                Log.d(TAG, "parseItem: TYPE_STEPS_PICTURE_CIRCLE requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_MOON_PHASE:
                item = new MoonPhaseItem(centerX, centerY, frames);
                break;
            case OpenWatchWatchFaceConstants.TYPE_YEAR_2:
                Log.d(TAG, "parseItem: TYPE_YEAR_2 requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_MISSED_CALLS:
                Log.d(TAG, "parseItem: TYPE_MISSED_CALLS requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_MISSED_SMS:
                Log.d(TAG, "parseItem: TYPE_MISSED_SMS requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_BATTERY_CIRCLE:
                Log.d(TAG, "parseItem: TYPE_BATTERY_CIRCLE requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_STEPS_PICTURE_WITH_CIRCLE_2:
                Log.d(TAG, "parseItem: TYPE_STEPS_PICTURE_WITH_CIRCLE_2 requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_KCAL:
                Log.d(TAG, "parseItem: TYPE_KCAL requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_MISSED_CALLS_SMS:
                Log.d(TAG, "parseItem: TYPE_MISSED_CALLS_SMS requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_STEPS_CIRCLE:
                Log.d(TAG, "parseItem: TYPE_STEPS_CIRCLE requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_KCAL_CIRCLE:
                Log.d(TAG, "parseItem: TYPE_KCAL_CIRCLE requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_POWER_CIRCLE:
                Log.d(TAG, "parseItem: TYPE_POWER_CIRCLE requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_DISTANCE_CIRCLE:
                Log.d(TAG, "parseItem: TYPE_DISTANCE_CIRCLE requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_DISTANCE:
                Log.d(TAG, "parseItem: TYPE_DISTANCE requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_BATTERY_IMAGE:
                Log.d(TAG, "parseItem: TYPE_BATTERY_IMAGE requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_UNKNOWN_1:
                Log.d(TAG, "parseItem: TYPE_UNKNOWN_1 requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_BATTERY_IMAGE_CHARGING:
                Log.d(TAG, "parseItem: TYPE_BATTERY_IMAGE_CHARGING requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_TEXT_PEDOMETER:
                Log.d(TAG, "parseItem: TYPE_TEXT_PEDOMETER requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_TEXT_HEARTRATE:
                Log.d(TAG, "parseItem: TYPE_TEXT_HEARTRATE requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_CHARGING:
                Log.d(TAG, "parseItem: TYPE_CHARGING requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            case OpenWatchWatchFaceConstants.TYPE_TAP_ACTION:
                item = new TapActionItem(centerX, centerY, frames);
                parseTapActionItem((TapActionItem) item, json);
                Log.d(TAG, "parseItem: touch parsed");
                break;
            case OpenWatchWatchFaceConstants.TYPE_YEAR_MONTH_DAY_2:
                Log.d(TAG, "parseItem: TYPE_YEAR_MONTH_DAY_2 requested, but not implemented!");
                throw new InvalidWatchFaceItemException();
            default:
                throw new InvalidWatchFaceItemException();
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

    private void parseTapActionItem(TapActionItem item, JsonObject jsonObject) {
        parseStaticItem(item, jsonObject);
        item.setPackageName(jsonObject.get("packageName").getAsString());
        item.setClassName(jsonObject.get("className").getAsString());
        item.setRange(jsonObject.get("range").getAsInt());
    }

    private RotatableItem parseRotatableItem(int centerX, int centerY, List<Drawable> frames, JsonObject json) throws InvalidWatchFaceItemException {
        RotatableItem item;
        int rotatableType;
        float startAngle;
        float maxAngle;
        int direction;
        JsonElement tmp;

        tmp = json.get("rotatable_type");
        if (tmp != null) {
            rotatableType = tmp.getAsInt();
        } else {
            throw new InvalidWatchFaceItemException();
        }

        tmp = json.get("start_angle");
        if (tmp != null) {
            startAngle = tmp.getAsFloat();
        } else {
            startAngle = 0;
        }

        tmp = json.get("max_angle");
        if (tmp != null) {
            maxAngle = tmp.getAsFloat();
        } else {
            maxAngle = 360;
        }

        tmp = json.get("direction");
        if (tmp != null) {
            direction = tmp.getAsInt();
        } else {
            direction = OpenWatchWatchFaceConstants.DIRECTION_CLOCKWISE;
        }

        switch (rotatableType) {
            case OpenWatchWatchFaceConstants.ROTATABLE_HOUR:
                item = new HourRotatableItem(centerX, centerY, frames, startAngle, maxAngle, direction);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_MINUTE:
                item = new MinuteRotatableItem(centerX, centerY, frames, startAngle, maxAngle, direction);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_SECOND:
                item = new SecondRotatableItem(centerX, centerY, frames, startAngle, maxAngle, direction);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_MONTH:
                item = new MonthRotatableItem(centerX, centerY, frames, startAngle, maxAngle, direction);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_WEEKDAY:
                item = new WeekdayRotatableItem(centerX, centerY, frames, startAngle, maxAngle, direction);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_BATTERY:
                item = new BatteryRotatableItem(centerX, centerY, frames, startAngle, maxAngle, direction);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_HOUR_24:
                item = new Hour24RotatableItem(centerX, centerY, frames, startAngle, maxAngle, direction);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_HOUR_SHADOW:
                item = new HourShadowRotatableItem(centerX, centerY, frames, startAngle, maxAngle, direction);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_MINUTE_SHADOW:
                item = new MinuteShadowRotatableItem(centerX, centerY, frames, startAngle, maxAngle, direction);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_SECOND_SHADOW:
                item = new SecondShadowRotatableItem(centerX, centerY, frames, startAngle, maxAngle, direction);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_DAY:
                item = new DayRotatableItem(centerX, centerY, frames, startAngle, maxAngle, direction);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_BALANCE:
                item = new BalanceRotatableItem(centerX, centerY, frames, startAngle, maxAngle, direction);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_STEP:
                item = new StepRotatableItem(centerX, centerY, frames, startAngle, maxAngle, direction);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_KCAL:
                item = new KCalRotatableItem(centerX, centerY, frames, startAngle, maxAngle, direction);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_DISTANCE:
                item = new DistanceRotatableItem(centerX, centerY, frames, startAngle, maxAngle, direction);
                break;
            default:
                throw new InvalidWatchFaceItemException();
        }

        return item;
    }
}
