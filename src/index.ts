import { requireNativeComponent } from 'react-native';

declare global {
  var nativeFabricUIManager: any;
}

const isFabricEnabled = global.nativeFabricUIManager != null;

export const FlashImage = isFabricEnabled
  ? require('./FlashImageViewNativeComponent').default
  : requireNativeComponent('FlashImageView');
