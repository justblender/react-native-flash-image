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
  UIImageView * _view;
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

    _view = [[UIImageView alloc] init];

    self.contentView = _view;
  }

  return self;
}

- (void)updateProps:(Props::Shared const &)props oldProps:(Props::Shared const &)oldProps
{
  const auto &oldViewProps = *std::static_pointer_cast<FlashImageViewProps const>(_props);
  const auto &newViewProps = *std::static_pointer_cast<FlashImageViewProps const>(props);
  
  std::string sourceUri = oldViewProps.source;
  bool shouldUpdateSource = NO;
  
  if (oldViewProps.source != newViewProps.source) {
    sourceUri = newViewProps.source;
    shouldUpdateSource = YES;
  }
  
  if (shouldUpdateSource) {
    auto convertedSourceUri = [[NSString alloc] initWithUTF8String:sourceUri.c_str()];
    [FlashImageViewManagerImpl loadImageFromUri:_view uri:convertedSourceUri];
  }

  [super updateProps:props oldProps:oldProps];
}

Class<RCTComponentViewProtocol> FlashImageViewCls(void)
{
  return FlashImageView.class;
}

@end
#endif
