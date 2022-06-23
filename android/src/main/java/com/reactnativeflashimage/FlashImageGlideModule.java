package com.reactnativeflashimage;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

@GlideModule
public final class FlashImageGlideModule extends AppGlideModule {

  @Override
  public void registerComponents(
    @NonNull Context context,
    @NonNull Glide glide,
    @NonNull Registry registry
  ) {
    OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory();
    glide.getRegistry().replace(GlideUrl.class, InputStream.class, factory);
  }
}
