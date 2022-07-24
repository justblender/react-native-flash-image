package com.reactnativeflashimage;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.facebook.common.internal.ImmutableList;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressLint("ViewConstructor")
public class FlashImageView extends AppCompatImageView {

  private static final Drawable TRANSPARENT_DRAWABLE = new ColorDrawable(Color.TRANSPARENT);

  private static final DrawableCrossFadeFactory CROSS_FADE_FACTORY =
    new DrawableCrossFadeFactory.Builder(100).setCrossFadeEnabled(true).build();

  private FlashImageSource currentImageSource = null;

  public FlashImageView(@NonNull ThemedReactContext context) {
    super(context);
  }

  @Override
  protected void onDetachedFromWindow() {
    this.disposeRequest();
    super.onDetachedFromWindow();
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  public void setSource(@Nullable ReadableMap sourceProp) {
    final String sourceUri = sourceProp != null
      ? sourceProp.getString("uri")
      : null;

    if (sourceProp == null || sourceUri == null || sourceUri.isEmpty()) {
      this.setImageDrawable(null);
      this.disposeRequest();
      return;
    }

    LazyHeaders.Builder headersBuilder = new LazyHeaders.Builder();

    if (sourceProp.hasKey("headers")) {
      final ReadableArray headersObjectArray = sourceProp.getArray("headers");
      if (headersObjectArray != null && headersObjectArray.size() > 0) {
        final List<String> requestHeaderStrings = headersObjectArray
          .toArrayList()
          .stream()
          .map(Objects::toString)
          .collect(Collectors.toList());

        final List<List<String>> requestHeaderChunks = chopped(requestHeaderStrings, 2)
          .stream()
          .filter(value -> value.size() == 2)
          .collect(Collectors.toList());

        for (List<String> requestHeader : requestHeaderChunks) {
          if (requestHeader.size() == 2) {
            headersBuilder = headersBuilder
              .addHeader(requestHeader.get(0), requestHeader.get(1));
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
      // don't need to start image loading again if the source is already the same
      return;
    }

    this.currentImageSource = flashImageSource;

    RequestBuilder<Drawable> requestBuilder = Glide
      .with(this.getContext())
      .load(flashImageSource.getSourceForGlide());

    if (sourceProp.hasKey("priority")) {
      final int requestPriorityRaw = sourceProp.getInt("priority");
      // lol sorry for this
      requestBuilder = requestBuilder.priority(
        requestPriorityRaw >= 0 && requestPriorityRaw < 2 ? Priority.LOW
          : requestPriorityRaw == 3 ? Priority.HIGH
          : requestPriorityRaw >= 4 ? Priority.IMMEDIATE
          : Priority.NORMAL
      );
    }

    if (sourceProp.hasKey("cache")) {
      final String cachePolicy = sourceProp.getString("cache");

      if ("ignore-cache".equals(cachePolicy)) {
        requestBuilder = requestBuilder
          .skipMemoryCache(true)
          .diskCacheStrategy(DiskCacheStrategy.NONE);
      } else if ("only-if-cached".equals(cachePolicy)) {
        requestBuilder = requestBuilder
          .onlyRetrieveFromCache(true);
      }
    }

    requestBuilder
      .placeholder(TRANSPARENT_DRAWABLE)
      .transition(DrawableTransitionOptions.withCrossFade(CROSS_FADE_FACTORY))
      .into(this);
  }

  private static <T> List<List<T>> chopped(List<T> list, final int L) {
    final List<List<T>> parts = new ArrayList<>();
    final int N = list.size();
    for (int i = 0; i < N; i += L) {
      parts.add(new ArrayList<T>(
        list.subList(i, Math.min(N, i + L)))
      );
    }
    return parts;
  }

  private void disposeRequest() {
    Glide.with(getContext()).clear(this);
  }
}
