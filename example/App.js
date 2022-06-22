import * as React from 'react';

import {StyleSheet, View} from 'react-native';
import {FlashImage} from 'react-native-flash-image';

export default function App() {
  const sourceUri = `https://loremflickr.com/320/240?${Math.floor(
    Math.random() * 100,
  )}`;

  return (
    <View style={styles.container}>
      <FlashImage source={sourceUri} style={styles.box} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#e2e8f0',
  },
  box: {
    width: 320,
    height: 240,
  },
});
