package com.openwatchproject.watchface;

public class InvalidWatchFaceItemException extends Exception {
    public InvalidWatchFaceItemException() {
    }

    public InvalidWatchFaceItemException(String message) {
        super(message);
    }

    public InvalidWatchFaceItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidWatchFaceItemException(Throwable cause) {
        super(cause);
    }
}
