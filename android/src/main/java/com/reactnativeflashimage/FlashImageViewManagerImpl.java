package com.reactnativeflashimage;

import androidx.annotation.Nullable;
import com.facebook.react.uimanager.ThemedReactContext;
import android.graphics.Color;

public class FlashImageViewManagerImpl {

    public static final String NAME = "FlashImageView";

    public static FlashImageView createViewInstance(ThemedReactContext context) {
        return new FlashImageView(context);
    }

    public static void setColor(FlashImageView view, String color) {
        view.setBackgroundColor(Color.parseColor(color));
    }

}
