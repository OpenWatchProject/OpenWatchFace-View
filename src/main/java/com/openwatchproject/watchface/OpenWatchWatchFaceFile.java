package com.openwatchproject.watchface;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class OpenWatchWatchFaceFile implements Closeable {
    private static final String METADATA_JSON = "metadata.json";
    private static final String WATCHFACE_JSON = "watchface.json";
    private static final String WATCHFACE_PREVIEW = "preview.png";

    private File file;
    private ZipFile zipFile;

    private OpenWatchWatchFaceMetadata metadata;
    private Bitmap preview;

    public OpenWatchWatchFaceFile(String filePath) {
        init(new File(filePath));
    }

    public OpenWatchWatchFaceFile(File file) {
        init(file);
    }

    private void init(File file) {
        this.file = file;
    }

    public OpenWatchWatchFace getWatchFace(Resources resources) {
        return new OpenWatchFaceDeserializer(this, resources).deserialize();
    }

    public InputStream getWatchFaceJson() throws IOException {
        return getZippedFile(WATCHFACE_JSON);
    }

    public File getFile() {
        return file;
    }

    public InputStream getZippedFile(@NonNull String path) throws IOException {
        if (zipFile == null) {
            zipFile = new ZipFile(file);
        }

        ZipEntry entry = zipFile.getEntry(path);
        if (entry == null) {
            throw new IOException("File not found in zip");
        }

        return zipFile.getInputStream(entry);
    }

    public Bitmap getPreview() {
        if (preview == null) {
            try (InputStream is = getZippedFile(WATCHFACE_PREVIEW)) {
                preview = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return preview;
    }

    public OpenWatchWatchFaceMetadata getMetadata() {
        if (metadata == null) {
            try (InputStreamReader isr = new InputStreamReader(getZippedFile(METADATA_JSON))) {
                metadata = new Gson().fromJson(isr, OpenWatchWatchFaceMetadata.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return metadata;
    }

    public boolean isValid() {
        return getPreview() != null && getMetadata() != null;
    }

    @Override
    public void close() {
        if (zipFile != null) {
            try {
                zipFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
