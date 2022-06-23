// @ts-ignore TODO: remove once there is a .d.ts file with definitions
import codegenNativeComponentUntyped from 'react-native/Libraries/Utilities/codegenNativeComponent';
import type { ViewProps, HostComponent } from 'react-native';
import type {
  Int32,
  WithDefault,
  // @ts-ignore TODO: remove once there is a .d.ts file with definitions
} from 'react-native/Libraries/Types/CodegenTypes';

interface NativeProps extends ViewProps {
  source: Readonly<{
    uri: string;
    headers: ReadonlyArray<string>;
    priority?: WithDefault<Int32, 0>;
    cache?: WithDefault<
      'default' | 'ignore-cache' | 'only-if-cached',
      'default'
    >;
  }>;
}

const codegenNativeComponent = codegenNativeComponentUntyped as <T extends {}>(
  name: string
) => HostComponent<T>;

export default codegenNativeComponent<NativeProps>('FlashImageView');
