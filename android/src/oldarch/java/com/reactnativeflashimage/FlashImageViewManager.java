package com.reactnativeflashimage;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.bridge.ReactApplicationContext;

public class FlashImageViewManager extends SimpleViewManager<ImageView> {

    ReactApplicationContext mCallerContext;

    public FlashImageViewManager(ReactApplicationContext reactContext) {
        mCallerContext = reactContext;
    }

    @Override
    public String getName() {
        return FlashImageViewManagerImpl.NAME;
    }

    @Override
    protected ImageView createViewInstance(@NonNull ThemedReactContext context) {
        return FlashImageViewManagerImpl.createViewInstance(context);
    }

    @ReactProp(name = "color")
    public void setSource(ImageView view, @Nullable String sourceUri) {
        FlashImageViewManagerImpl.setSource(view, sourceUri);
    }

}
