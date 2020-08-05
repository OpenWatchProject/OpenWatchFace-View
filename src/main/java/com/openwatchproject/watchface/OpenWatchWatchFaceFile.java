package com.openwatchproject.watchface;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;

import com.google.gson.Gson;

import org.w3c.dom.Document;

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

    private Context context;
    private DocumentFile file;
    private ZipFile zipFile;

    private OpenWatchWatchFaceMetadata metadata;
    private Bitmap preview;

    public OpenWatchWatchFaceFile(Context context, Uri uri) {
        this.context = context;
        this.file = DocumentFile.fromSingleUri(context, uri);
    }

    public OpenWatchWatchFaceFile(Context context, DocumentFile file) {
        this.context = context;
        this.file = file;
    }

    public OpenWatchWatchFace getWatchFace(Resources resources) {
        return new OpenWatchFaceDeserializer(this, resources).deserialize();
    }

    public InputStream getWatchFaceJson() throws IOException {
        return getZippedFile(WATCHFACE_JSON);
    }

    public DocumentFile getFile() {
        return file;
    }

    public InputStream getZippedFile(@NonNull String path) throws IOException {
        if (zipFile == null) {
            ParcelFileDescriptor pfd = context.getContentResolver()
                    .openFileDescriptor(file.getUri(), "r");

            if (pfd != null) {
                String fdPath = "/proc/self/fd/" + pfd.getFd();
                zipFile = new ZipFile(fdPath);
                pfd.close();
            } else {
                throw new IOException("Watchface folder not accessible!");
            }
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
