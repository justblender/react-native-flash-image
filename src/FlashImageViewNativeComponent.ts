// @ts-ignore TODO: remove once there is a .d.ts file with definitions
import codegenNativeComponentUntyped from 'react-native/Libraries/Utilities/codegenNativeComponent';
import type { ViewProps, HostComponent } from 'react-native';

interface NativeProps extends ViewProps {
  source: string;
}

const codegenNativeComponent = codegenNativeComponentUntyped as <T extends {}>(
  name: string
) => HostComponent<T>;

export default codegenNativeComponent<NativeProps>('FlashImageView');
