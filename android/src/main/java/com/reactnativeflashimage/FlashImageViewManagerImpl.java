package com.reactnativeflashimage;

import com.facebook.react.uimanager.ThemedReactContext;
import android.widget.ImageView;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;

public class FlashImageViewManagerImpl {

    public static final String NAME = "FlashImageView";

    public static ImageView createViewInstance(ThemedReactContext context) {
        return new ImageView(context);
    }

    public static void setSource(ImageView view, String sourceUri) {
      final ImageLoader imageLoader = Coil.imageLoader(view.getContext());
      imageLoader.enqueue(
        new ImageRequest.Builder(view.getContext())
          .data(sourceUri)
          .crossfade(100)
          .target(view)
          .build()
      );
    }

}
