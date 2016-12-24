package com.raxee.raxeeweather.manager;

import android.content.Context;
import android.graphics.Typeface;

public class FontManager {
    private static final String ROOT = "fonts/";

    public static final String WEATHERICONS = ROOT + "weathericons-regular-webfont.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }
}