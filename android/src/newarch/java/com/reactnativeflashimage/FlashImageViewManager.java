package com.reactnativeflashimage;

import android.graphics.Color;
import android.widget.ImageView;

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
public class FlashImageViewManager extends SimpleViewManager<ImageView>
        implements FlashImageViewManagerInterface<ImageView> {

    private final ViewManagerDelegate<ImageView> mDelegate;

    public FlashImageViewManager(ReactApplicationContext context) {
        mDelegate = new FlashImageViewManagerDelegate<>(this);
    }

    @Nullable
    @Override
    protected ViewManagerDelegate<ImageView> getDelegate() {
        return mDelegate;
    }

    @NonNull
    @Override
    public String getName() {
        return FlashImageViewManagerImpl.NAME;
    }

    @NonNull
    @Override
    protected ImageView createViewInstance(@NonNull ThemedReactContext context) {
        return FlashImageViewManagerImpl.createViewInstance(context);
    }

    @Override
    @ReactProp(name = "source")
    public void setSource(ImageView view, @Nullable String sourceUri) {
        FlashImageViewManagerImpl.setSource(view, sourceUri);
    }
}
