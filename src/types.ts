import type { ViewProps } from 'react-native';

export type RequestHeaders = {
  [key: string]: string;
};

export enum RequestPriority {
  VeryLow = 0,
  Low = 1,
  Normal = 2,
  High = 3,
  VeryHigh = 4,
}

export enum CacheControl {
  Default = 'default',
  IgnoreCache = 'ignore-cache',
  OnlyIfCached = 'only-if-cached',
}

export type SourceUriProp = {
  uri: string;
  headers?: Headers;
  priority?: RequestPriority;
  cache?: CacheControl;
};

export type FlashImageProps = ViewProps & {
  source: SourceUriProp;
};

declare global {
  var nativeFabricUIManager: any;
}
