package com.reactnativeflashimage;

import androidx.annotation.Nullable;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.bridge.ReactApplicationContext;
import android.graphics.Color;
import java.util.Map;
import java.util.HashMap;

public class FlashImageViewManager extends SimpleViewManager<FlashImageView> {

    ReactApplicationContext mCallerContext;

    public FlashImageViewManager(ReactApplicationContext reactContext) {
        mCallerContext = reactContext;
    }

    @Override
    public String getName() {
        return FlashImageViewManagerImpl.NAME;
    }

    @Override
    protected FlashImageView createViewInstance(@NonNull ThemedReactContext context) {
        return FlashImageViewManagerImpl.createViewInstance(context);
    }

    @ReactProp(name = "color")
    public void setColor(FlashImageView view, @Nullable String color) {
        FlashImageViewManagerImpl.setColor(view, color);
    }

}
