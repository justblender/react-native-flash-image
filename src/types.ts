import type { NativeSyntheticEvent, ViewProps } from 'react-native';

export type RequestHeaders = [string, string][];

export enum RequestPriority {
  VeryLow = 0,
  Low = 1,
  Normal = 2,
  High = 3,
  VeryHigh = 4,
}

export enum CachePolicy {
  Default = 'default',
  IgnoreCache = 'ignore-cache',
  OnlyIfCached = 'only-if-cached',
}

export type SourceUriProp = {
  uri: string;
  headers?: RequestHeaders;
  priority?: RequestPriority;
  cache?: CachePolicy;
};

export type OnProgressEvent = NativeSyntheticEvent<{
  bytesWritten: number;
  bytesExpected: number;
}>;

// export type OnErrorEvent = NativeSyntheticEvent<{
//   message: string;
// }>;

// export type OnLoadEndEvent = NativeSyntheticEvent<{
//   width: number;
//   height: number;
// }>;

export type FlashImageProps = ViewProps & {
  source: SourceUriProp;
  // onLoadStart?: () => void;
  onProgress?: (event: OnProgressEvent) => void;
  // onError?: (event: OnErrorEvent) => void;
  // onLoadEnd?: (event: OnLoadEndEvent) => void;
};

declare global {
  var nativeFabricUIManager: any;
}
