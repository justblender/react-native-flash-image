package com.reactnativeflashimage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.viewmanagers.FlashImageViewManagerDelegate;
import com.facebook.react.viewmanagers.FlashImageViewManagerInterface;

@ReactModule(name = FlashImageViewManager.NAME)
public class FlashImageViewManager extends SimpleViewManager<FlashImageView>
  implements FlashImageViewManagerInterface<FlashImageView> {

  public static final String NAME = "FlashImageView";

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
    return NAME;
  }

  @NonNull
  @Override
  protected FlashImageView createViewInstance(@NonNull ThemedReactContext context) {
    return new FlashImageView(context);
  }

  @Override
  @ReactProp(name = "source")
  public void setSource(@NonNull FlashImageView view, @Nullable ReadableMap sourceProp) {
    view.setSource(sourceProp);
  }

  @Nullable
  @Override
  public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.of(
      "topProgress", MapBuilder.of("registrationName", "onProgress")
    );
  }
}
