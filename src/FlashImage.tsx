import React from 'react';
import { requireNativeComponent } from 'react-native';
import type { FlashImageProps, RequestHeaders } from './types';

const isFabricEnabled = global.nativeFabricUIManager != null;

const NativeFlashImageView = isFabricEnabled
  ? require('./FlashImageViewNativeComponent').default
  : requireNativeComponent('FlashImageView');

export const FlashImage = ({ source, ...rest }: FlashImageProps) => {
  const serializedSource = source.headers
    ? { ...source, headers: serializeHeaders(source.headers ?? {}) }
    : source;

  return <NativeFlashImageView source={serializedSource} {...rest} />;
};

const serializeHeaders = (headers: RequestHeaders) => {
  return Object.entries(headers).map(([header, value]) => `${header}=${value}`);
};
