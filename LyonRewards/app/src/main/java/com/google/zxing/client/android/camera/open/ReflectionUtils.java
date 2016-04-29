package com.google.zxing.client.android.camera.open;

import android.util.Log;

import java.lang.reflect.Field;

/**
 * Class used to inject a custom value for the autofocus interval used in the QR scanner
 * Created by Jonathan on 29/04/2016.
 */
public class ReflectionUtils {

    public static void performInjection() {
        injectClassField(AutoFocusManager.class, "AUTO_FOCUS_INTERVAL_MS", 1000L);
    }

    private static void injectClassField(Class clazz, String fieldStr, long newValue) {
        try {
            final Field field = clazz.getDeclaredField(fieldStr);
            if (field != null) {
                field.setAccessible(true);
                Object obj = null;
                field.set(obj, newValue);
            }
        } catch (Exception e) {
            Log.d("REFLECTION", clazz.getSimpleName() + " injection failed for field: " + fieldStr);
        }
    }
}
