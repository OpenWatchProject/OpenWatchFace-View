package com.openwatchproject.watchface;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.openwatchproject.watchface.item.AbstractItem;

import java.lang.reflect.Type;

public class OpenWatchFaceDeserializer implements JsonDeserializer<OpenWatchWatchFace> {
    private final OpenWatchWatchFaceFile watchFaceFile;

    public OpenWatchFaceDeserializer(OpenWatchWatchFaceFile watchFaceFile) {
        this.watchFaceFile = watchFaceFile;
    }

    @Override
    public OpenWatchWatchFace deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        OpenWatchWatchFace watchFace = new OpenWatchWatchFace();
        watchFace.setWidth(jsonObject.get("width").getAsInt());
        watchFace.setHeight(jsonObject.get("height").getAsInt());

        JsonArray items = jsonObject.get("items").getAsJsonArray();
        for (JsonElement itemElement : items) {
            JsonObject itemObject = itemElement.getAsJsonObject();
            watchFace.addItem(parseItem(itemObject));
        }

        return watchFace;
    }

    private AbstractItem parseItem(JsonObject itemObject) {
        AbstractItem item = null;
        int type = itemObject.get("type").getAsInt();
        int centerX = itemObject.get("centerX").getAsInt();
        int centerY = itemObject.get("centerY").getAsInt();

        switch (type) {

        }

        return item;
    }
}
