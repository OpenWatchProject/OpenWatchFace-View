package com.openwatchproject.watchface;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class OpenWatchWatchFaceFile {
    private static final String METADATA_JSON = "metadata.json";
    private static final String WATCHFACE_JSON = "watchface.json";

    private final File file;
    private OpenWatchWatchFaceMetadata metadata;
    private Bitmap preview;

    public OpenWatchWatchFaceFile(String filePath) {
        this.file = new File(filePath);
    }

    public OpenWatchWatchFaceFile(File file) {
        this.file = file;
    }

    public OpenWatchWatchFace getWatchFace() {
        try (InputStream jsonInput = getZippedFile(WATCHFACE_JSON)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(OpenWatchWatchFace.class, new OpenWatchFaceDeserializer(this))
                    .create();
            return gson.fromJson(new InputStreamReader(jsonInput), OpenWatchWatchFace.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private InputStream getZippedFile(@NonNull String path) throws IOException {
        try (ZipFile zipFile = new ZipFile(file)) {
            ZipEntry entry = zipFile.getEntry(path);
            if (entry == null) {
                throw new IOException("File not found in zip");
            }

            return zipFile.getInputStream(entry);
        }
    }

    public Bitmap getPreview() {
        return preview;
    }

    public OpenWatchWatchFaceMetadata getMetadata() {
        return metadata;
    }

    public boolean isValid() {
        return true;
    }
}
