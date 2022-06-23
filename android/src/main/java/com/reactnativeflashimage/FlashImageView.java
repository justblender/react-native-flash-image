package com.reactnativeflashimage;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;

@SuppressLint("ViewConstructor")
public class FlashImageView extends AppCompatImageView {

  private static final Drawable TRANSPARENT_DRAWABLE = new ColorDrawable(Color.TRANSPARENT);

  private static final DrawableCrossFadeFactory CROSS_FADE_FACTORY =
    new DrawableCrossFadeFactory.Builder(100).setCrossFadeEnabled(true).build();

  private FlashImageSource currentImageSource = null;

  public FlashImageView(@NonNull ThemedReactContext context) {
    super(context);
  }

  public void setSource(@Nullable ReadableMap sourceProp) {
    final String sourceUri = sourceProp != null
      ? sourceProp.getString("uri")
      : null;

    if (sourceProp == null || sourceUri == null || sourceUri.isEmpty()) {
      this.setImageDrawable(null);
      this.disposeRequest();
      return;
    }

    final LazyHeaders.Builder headersBuilder = new LazyHeaders.Builder();
    if (sourceProp.hasKey("headers")) {
      final ReadableArray requestHeaders = sourceProp.getArray("headers");
      if (requestHeaders != null && requestHeaders.size() > 0) {
        for (int i = 0; i < requestHeaders.size(); i++) {
          final String serializedRequestHeader = requestHeaders.getString(i);
          final String[] requestHeader = serializedRequestHeader.split("=");
          if (requestHeader.length == 2) {
            headersBuilder.addHeader(requestHeader[0], requestHeader[1]);
          }
        }
      }
    }

    final FlashImageSource flashImageSource = new FlashImageSource(
      this.getContext(), sourceUri, headersBuilder.build()
    );

    if (!flashImageSource.equals(this.currentImageSource)) {
      this.disposeRequest();
    } else {
      return;
    }

    this.currentImageSource = flashImageSource;

    RequestBuilder<Drawable> requestManager = Glide
      .with(this.getContext())
      .load(flashImageSource.getSourceForGlide());

    if (sourceProp.hasKey("priority")) {
      final int requestPriorityRaw = sourceProp.getInt("priority");
      // lol sorry for this
      requestManager = requestManager.priority(
        requestPriorityRaw >= 0 && requestPriorityRaw < 2 ? Priority.LOW
          : requestPriorityRaw == 3 ? Priority.HIGH
          : requestPriorityRaw >= 4 ? Priority.IMMEDIATE
          : Priority.NORMAL
      );
    }

    if (sourceProp.hasKey("cache")) {
      final String cachePolicy = sourceProp.getString("cache");
      if (cachePolicy != null && cachePolicy.length() > 0) {
        switch (cachePolicy) {
          case "ignore-cache":
            requestManager = requestManager
              .skipMemoryCache(true)
              .diskCacheStrategy(DiskCacheStrategy.NONE);
            break;

          case "only-if-cached":
            requestManager = requestManager
              .onlyRetrieveFromCache(true);
            break;
        }
      }
    }

    requestManager
      .placeholder(TRANSPARENT_DRAWABLE)
      .transition(DrawableTransitionOptions.withCrossFade(CROSS_FADE_FACTORY))
      .into(this);
  }

  private void disposeRequest() {
    Glide.with(getContext()).clear(this);
  }

  @Override
  protected void onDetachedFromWindow() {
    this.disposeRequest();
    super.onDetachedFromWindow();
  }
}
