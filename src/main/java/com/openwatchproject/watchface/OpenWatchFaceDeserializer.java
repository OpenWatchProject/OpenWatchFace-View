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
import com.openwatchproject.watchface.item.Battery180AngleRotatableItem;
import com.openwatchproject.watchface.item.BatteryCustomAngleRotatableItem;
import com.openwatchproject.watchface.item.DayItem;
import com.openwatchproject.watchface.item.DayRotatableItem;
import com.openwatchproject.watchface.item.TwentyFourHoursRotatableItem;
import com.openwatchproject.watchface.item.DayOfWeekItem;
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

        watchFace = new OpenWatchWatchFace(json.get("width").getAsInt(), json.get("height").getAsInt());
        watchFace.setAbsolutePath(watchFaceFile.getFile().getAbsolutePath());

        JsonArray items = json.get("items").getAsJsonArray();
        for (JsonElement itemElement : items) {
            JsonObject itemObject = itemElement.getAsJsonObject();
            AbstractItem item = parseItem(itemObject);
            if (item != null) {
                watchFace.addItem(item);
                if (item instanceof TapActionItem) {
                    watchFace.addTapActionItem((TapActionItem) item);
                }
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

                break;
            case OpenWatchWatchFaceConstants.TYPE_STEPS:
                break;
            case OpenWatchWatchFaceConstants.TYPE_HEART_RATE:
                break;
            case OpenWatchWatchFaceConstants.TYPE_BATTERY:
                break;
            case OpenWatchWatchFaceConstants.TYPE_SPECIAL_SECOND:
                item = new SpecialSecondItem(centerX, centerY, frames);
                break;
            case OpenWatchWatchFaceConstants.TYPE_YEAR:
                item = new YearItem(centerX, centerY, frames);
                break;
            case OpenWatchWatchFaceConstants.TYPE_BATTERY_PICTURE_CIRCLE:
                break;
            case OpenWatchWatchFaceConstants.TYPE_STEPS_PICTURE_CIRCLE:
                break;
            case OpenWatchWatchFaceConstants.TYPE_MOON_PHASE:
                break;
            case OpenWatchWatchFaceConstants.TYPE_YEAR_2:
                break;
            case OpenWatchWatchFaceConstants.TYPE_MISSED_CALLS:
                break;
            case OpenWatchWatchFaceConstants.TYPE_MISSED_SMS:
                break;
            case OpenWatchWatchFaceConstants.TYPE_BATTERY_CIRCLE:
                break;
            case OpenWatchWatchFaceConstants.TYPE_STEPS_PICTURE_WITH_CIRCLE_2:
                break;
            case OpenWatchWatchFaceConstants.TYPE_KCAL:
                break;
            case OpenWatchWatchFaceConstants.TYPE_MISSED_CALLS_SMS:
                break;
            case OpenWatchWatchFaceConstants.TYPE_STEPS_CIRCLE:
                break;
            case OpenWatchWatchFaceConstants.TYPE_KCAL_CIRCLE:
                break;
            case OpenWatchWatchFaceConstants.TYPE_POWER_CIRCLE:
                break;
            case OpenWatchWatchFaceConstants.TYPE_DISTANCE_CIRCLE:
                break;
            case OpenWatchWatchFaceConstants.TYPE_DISTANCE:
                break;
            case OpenWatchWatchFaceConstants.TYPE_BATTERY_IMAGE:
                break;
            case OpenWatchWatchFaceConstants.TYPE_UNKNOWN_1:
                break;
            case OpenWatchWatchFaceConstants.TYPE_BATTERY_IMAGE_CHARGING:
                break;
            case OpenWatchWatchFaceConstants.TYPE_TEXT_PEDOMETER:
                break;
            case OpenWatchWatchFaceConstants.TYPE_TEXT_HEARTRATE:
                break;
            case OpenWatchWatchFaceConstants.TYPE_CHARGING:
                break;
            case OpenWatchWatchFaceConstants.TYPE_TAP_ACTION:
                item = new TapActionItem(centerX, centerY, frames);
                parseTapActionItem((TapActionItem) item, json);
                Log.d("OpenWatchFaceDeserial", "parseItem: touch parsed");
                break;
            case OpenWatchWatchFaceConstants.TYPE_YEAR_MONTH_DAY_2:
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

    private void parseTapActionItem(TapActionItem item, JsonObject jsonObject) {
        parseStaticItem(item, jsonObject);
        item.setPackageName(jsonObject.get("packageName").getAsString());
        item.setClassName(jsonObject.get("className").getAsString());
        item.setRange(jsonObject.get("range").getAsInt());
    }

    private RotatableItem parseRotatableItem(int centerX, int centerY, ArrayList<Drawable> frames, JsonObject json) {
        RotatableItem item = null;
        int rotatableType = json.get("rotatableType").getAsInt();
        float angle = json.get("angle").getAsFloat();
        int rotationFactor = json.get("rotationFactor").getAsInt();
        int direction = json.get("direction").getAsInt();

        switch (rotatableType) {
            case OpenWatchWatchFaceConstants.ROTATABLE_HOUR:
                item = new HourRotatableItem(centerX, centerY, direction, frames, angle, rotationFactor);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_MINUTE:
                item = new MinuteRotatableItem(centerX, centerY, direction, frames, angle, rotationFactor);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_SECOND:
                item = new SecondRotatableItem(centerX, centerY, direction, frames, angle, rotationFactor);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_MONTH:
                item = new MonthRotatableItem(centerX, centerY, direction, frames, angle, rotationFactor);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_WEEKDAY:
                item = new WeekdayRotatableItem(centerX, centerY, direction, frames, angle, rotationFactor);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_BATTERY_180_ANGLE:
                item = new Battery180AngleRotatableItem(centerX, centerY, direction, frames, angle, rotationFactor);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_24_HOURS:
                item = new TwentyFourHoursRotatableItem(centerX, centerY, direction, frames, angle, rotationFactor);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_HOUR_SHADOW:
                item = new HourShadowRotatableItem(centerX, centerY, direction, frames, angle, rotationFactor);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_MINUTE_SHADOW:
                item = new MinuteShadowRotatableItem(centerX, centerY, direction, frames, angle, rotationFactor);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_SECOND_SHADOW:
                item = new SecondShadowRotatableItem(centerX, centerY, direction, frames, angle, rotationFactor);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_DAY:
                item = new DayRotatableItem(centerX, centerY, direction, frames, angle, rotationFactor);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_BATTERY_CUSTOM_ANGLE:
                //item = new BatteryCustomAngleRotatableItem(centerX, centerY, direction, frames, angle, rotationFactor);
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_ROTATE_MODE:
                //item = parseRotatableRotateModeItem();
                break;
            case OpenWatchWatchFaceConstants.ROTATABLE_BALANCE:
                item = new BalanceRotatableItem(centerX, centerY, direction, frames, angle, rotationFactor);
                break;
        }

        return item;
    }

    private RotatableItem parseRotatableRotateModeItem() {
        return null;
    }
}
