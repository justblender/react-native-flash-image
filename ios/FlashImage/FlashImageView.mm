// This guard prevent the code from being compiled in the old architecture
#ifdef RCT_NEW_ARCH_ENABLED
#import "FlashImageView.h"

#import <react/renderer/components/RNFlashImageSpec/ComponentDescriptors.h>
#import <react/renderer/components/RNFlashImageSpec/EventEmitters.h>
#import <react/renderer/components/RNFlashImageSpec/Props.h>
#import <react/renderer/components/RNFlashImageSpec/RCTComponentViewHelpers.h>

#import "RCTFabricComponentsPlugins.h"
#import "RNFlashImage-Swift.h"

using namespace facebook::react;

@interface FlashImageView () <RCTFlashImageViewViewProtocol>

@end

@implementation FlashImageView {
  FlashImageNativeView * _view;
}

+ (ComponentDescriptorProvider)componentDescriptorProvider
{
  return concreteComponentDescriptorProvider<FlashImageViewComponentDescriptor>();
}

- (instancetype)initWithFrame:(CGRect)frame
{
  if (self = [super initWithFrame:frame]) {
    static const auto defaultProps = std::make_shared<const FlashImageViewProps>();
    _props = defaultProps;
    _view = [[FlashImageNativeView alloc] init];
    self.contentView = _view;
  }
  
  return self;
}

- (void)updateProps:(Props::Shared const &)props oldProps:(Props::Shared const &)oldProps
{
  const auto &oldViewProps = *std::static_pointer_cast<FlashImageViewProps const>(_props);
  const auto &newViewProps = *std::static_pointer_cast<FlashImageViewProps const>(props);
   
  bool shouldUpdateSource = NO;
  
  // todo: clean this up
  if (oldViewProps.source.uri != newViewProps.source.uri) {
    shouldUpdateSource = YES;
  }
  
  if (shouldUpdateSource) {
    const auto sourceProp = newViewProps.source;
    const auto sourceUri = sourceProp.uri;
    
    if (!sourceUri.empty()) {
      const auto requestHeaders = [NSMutableArray arrayWithCapacity:sourceProp.headers.size()];
      for (const auto &header : sourceProp.headers) {
        [requestHeaders addObject:[[NSString alloc] initWithUTF8String:header.c_str()]];
      }
      
      const auto progressBlock = ^(double bytesWritten, double bytesExpected) {
        if (const auto eventEmitter = self.getEventEmitter) {
          eventEmitter->onProgress(FlashImageViewEventEmitter::OnProgress{
            .bytesWritten = bytesWritten,
            .bytesExpected = bytesExpected
          });
        }
      };
          
      const auto completionBlock = ^(int32_t width, int32_t height, NSString *error) {
        // if (const auto eventEmitter = self.getEventEmitter) {
        //   if (error) {
        //     eventEmitter->onError(FlashImageViewEventEmitter::OnError{
        //       .message = std::string([error UTF8String])
        //     });
        //   } else {
        //     eventEmitter->onLoadEnd(FlashImageViewEventEmitter::OnLoadEnd{
        //       .width = width,
        //       .height = height
        //     });
        //   }
        // }
      };

      [_view setSource:[[NSString alloc] initWithUTF8String:sourceUri.c_str()]
        requestHeaders:[NSArray arrayWithArray:requestHeaders]
       requestPriority:sourceProp.priority
    requestCachePolicy:[[NSString alloc] initWithUTF8String:toString(sourceProp.cache).c_str()]
              progress:progressBlock
            completion:completionBlock];
    } else {
      _view.image = nil;
    }
  }

  [super updateProps:props oldProps:oldProps];
}

- (std::shared_ptr<const FlashImageViewEventEmitter>)getEventEmitter
{
  if (!self->_eventEmitter) {
    return nullptr;
  }

  assert(std::dynamic_pointer_cast<FlashImageViewEventEmitter const>(self->_eventEmitter));
  return std::static_pointer_cast<FlashImageViewEventEmitter const>(self->_eventEmitter);
}

Class<RCTComponentViewProtocol> FlashImageViewCls(void)
{
  return FlashImageView.class;
}

@end
#endif
