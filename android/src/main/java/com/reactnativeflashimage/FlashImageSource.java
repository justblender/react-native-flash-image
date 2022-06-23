package com.reactnativeflashimage;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.facebook.react.views.imagehelper.ImageSource;

import java.util.Objects;

public class FlashImageSource extends ImageSource {

  private final Headers headers;
  private final Uri uri;

  public FlashImageSource(@NonNull Context context, @NonNull String source) {
    this(context, source, null);
  }

  public FlashImageSource(
    @NonNull Context context,
    @NonNull String source,
    @Nullable Headers headers
  ) {
    this(context, source, 0.0d, 0.0d, headers);
  }

  public FlashImageSource(
    @NonNull Context context,
    @NonNull String source,
    double width,
    double height,
    @Nullable Headers headers
  ) {
    super(context, source, width, height);

    this.headers = headers == null ? Headers.DEFAULT : headers;

    final Uri superUri = super.getUri();
    if (UriSchemes.LOCAL_RESOURCE.conformsUri(superUri)) {
      this.uri = Uri.parse(superUri.toString().replace("res:/", String.format("%s://%s/",
        UriSchemes.ANDROID_RESOURCE.getScheme(),
        context.getPackageName()
      )));
    } else {
      this.uri = superUri;
    }
  }

  public Object getSourceForGlide() {
    if (UriSchemes.ANDROID_CONTENT.conformsUri(uri) || UriSchemes.DATA_SCHEME.conformsUri(uri)) {
      return getSource();
    } else if (UriSchemes.ANDROID_RESOURCE.conformsUri(uri) || UriSchemes.LOCAL_FILE.conformsUri(uri)) {
      return getUri().toString();
    } else {
      return new GlideUrl(getUri().toString(), getHeaders());
    }
  }

  @Override
  public Uri getUri() {
    return uri;
  }

  public Headers getHeaders() {
    return headers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    FlashImageSource that = (FlashImageSource) o;
    return Objects.equals(headers, that.headers) && Objects.equals(uri, that.uri);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), headers, uri);
  }

  private enum UriSchemes {
    DATA_SCHEME("data"),
    LOCAL_RESOURCE("res"),
    ANDROID_RESOURCE("android.resource"),
    ANDROID_CONTENT("content"),
    LOCAL_FILE("file");

    private final String scheme;

    UriSchemes(String scheme) {
      this.scheme = scheme;
    }

    String getScheme() {
      return scheme;
    }

    boolean conformsUri(@Nullable Uri uri) {
      return uri != null && uri.getScheme().equals(this.scheme);
    }
  }
}
