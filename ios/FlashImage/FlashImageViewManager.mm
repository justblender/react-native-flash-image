#import <React/RCTViewManager.h>
#import "RNFlashImage-Swift.h"

@interface FlashImageViewManager : RCTViewManager
@end

@implementation FlashImageViewManager

RCT_EXPORT_MODULE(FlashImageView)

- (FlashImageNativeView *)view
{
  return [[FlashImageNativeView alloc] init];
}

RCT_CUSTOM_VIEW_PROPERTY(source, NSDictionary, FlashImageNativeView)
{
  // noop for now
}

// RCT_EXPORT_VIEW_PROPERTY(onLoadStart, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onProgress, RCTDirectEventBlock)
// RCT_EXPORT_VIEW_PROPERTY(onError, RCTDirectEventBlock)
// RCT_EXPORT_VIEW_PROPERTY(onLoadEnd, RCTDirectEventBlock)

@end
