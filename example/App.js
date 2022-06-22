import * as React from 'react';

import {Image, ScrollView, StyleSheet} from 'react-native';
import {FlashImage} from 'react-native-flash-image';
import {dataSourceUri} from './cat-base64';

const {uri: localSourceUri} = Image.resolveAssetSource(require('./cat.jpeg'));

export default function App() {
  // appending a random number to use a different image on every render
  const networkSourceUri = `https://loremflickr.com/320/240?${Math.floor(
    Math.random() * 100,
  )}`;

  return (
    <ScrollView style={styles.container}>
      <FlashImage source={localSourceUri} style={styles.image} />
      <FlashImage source={dataSourceUri} style={styles.image} />
      <FlashImage source={networkSourceUri} style={styles.image} />
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#e2e8f0',
  },
  image: {
    width: 320,
    height: 240,
  },
});
