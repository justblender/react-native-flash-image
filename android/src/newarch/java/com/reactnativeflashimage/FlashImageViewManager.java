package com.reactnativeflashimage;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.viewmanagers.FlashImageViewManagerDelegate;
import com.facebook.react.viewmanagers.FlashImageViewManagerInterface;

@ReactModule(name = FlashImageViewManagerImpl.NAME)
public class FlashImageViewManager extends SimpleViewManager<FlashImageView>
        implements FlashImageViewManagerInterface<FlashImageView> {

    private final ViewManagerDelegate<FlashImageView> mDelegate;

    public FlashImageViewManager(ReactApplicationContext context) {
        mDelegate = new FlashImageViewManagerDelegate<>(this);
    }

    @Nullable
    @Override
    protected ViewManagerDelegate<FlashImageView> getDelegate() {
        return mDelegate;
    }

    @NonNull
    @Override
    public String getName() {
        return FlashImageViewManagerImpl.NAME;
    }

    @NonNull
    @Override
    protected FlashImageView createViewInstance(@NonNull ThemedReactContext context) {
        return FlashImageViewManagerImpl.createViewInstance(context);
    }

    @Override
    @ReactProp(name = "color")
    public void setColor(FlashImageView view, @Nullable String color) {
        FlashImageViewManagerImpl.setColor(view, color);
    }
}
