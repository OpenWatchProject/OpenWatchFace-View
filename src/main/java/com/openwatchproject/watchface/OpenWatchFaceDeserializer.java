package com.openwatchproject.watchface;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.openwatchproject.watchface.item.AbstractItem;
import com.openwatchproject.watchface.item.RotatableItem;
import com.openwatchproject.watchface.item.StaticItem;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class OpenWatchFaceDeserializer {
    private final OpenWatchWatchFaceFile watchFaceFile;
    private OpenWatchWatchFace watchFace;

    public OpenWatchFaceDeserializer(OpenWatchWatchFaceFile watchFaceFile) {
        this.watchFaceFile = watchFaceFile;
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

        switch (type) {
            case OpenWatchWatchFaceConstants.TYPE_STATIC:
                item = new StaticItem(centerX, centerY, frames);
                parseStaticItem((StaticItem) item, json);
                break;
            case OpenWatchWatchFaceConstants.TYPE_ROTATABLE:
                item = parseRotatableItem(centerX, centerY, frames, json);
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
                frames.add(new BitmapDrawable(BitmapFactory.decodeStream(is)));
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
