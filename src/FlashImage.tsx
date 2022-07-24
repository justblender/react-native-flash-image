import React from 'react';
import { requireNativeComponent } from 'react-native';
import type { FlashImageProps } from './types';

const isFabricEnabled = global.nativeFabricUIManager != null;

const NativeFlashImageView = isFabricEnabled
  ? require('./FlashImageViewNativeComponent').default
  : requireNativeComponent('FlashImageView');

export const FlashImage = ({ source, ...rest }: FlashImageProps) => {
  const serializedSource = source?.headers
    ? { ...source, headers: source.headers.flatMap((a) => a) }
    : source;

  return <NativeFlashImageView source={serializedSource} {...rest} />;
};
