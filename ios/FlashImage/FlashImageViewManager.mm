#import <React/RCTViewManager.h>
#import "RNFlashImage-Swift.h"

@interface FlashImageViewManager : RCTViewManager
@end

@implementation FlashImageViewManager

RCT_EXPORT_MODULE(FlashImageView)

- (UIImageView *)view
{
  return [[UIImageView alloc] init];
}

 RCT_CUSTOM_VIEW_PROPERTY(source, NSString, UIImageView)
 {
   [FlashImageViewManagerImpl loadImageFromUri:view uri:json];
   NSLog(@"setting source (old arch)");
 }

@end
