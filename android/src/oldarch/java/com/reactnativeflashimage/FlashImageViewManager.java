package com.reactnativeflashimage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class FlashImageViewManager extends SimpleViewManager<FlashImageView> {

  public static final String NAME = "FlashImageView";

  ReactApplicationContext mCallerContext;

  public FlashImageViewManager(ReactApplicationContext reactContext) {
    mCallerContext = reactContext;
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  protected FlashImageView createViewInstance(@NonNull ThemedReactContext context) {
    return new FlashImageView(context);
  }

  @ReactProp(name = "source")
  public void setSource(@NonNull FlashImageView view, @Nullable ReadableMap sourceProp) {
    view.setSource(sourceProp);
  }
}
