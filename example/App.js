import * as React from 'react';

import {Image, ScrollView, StyleSheet} from 'react-native';
import {
  CacheControl,
  FlashImage,
  RequestPriority,
} from 'react-native-flash-image';
import {dataSourceUri} from './cat-base64';

const {uri: localSourceUri} = Image.resolveAssetSource(require('./cat.jpeg'));

export default function App() {
  return (
    <ScrollView style={styles.container}>
      <FlashImage source={{uri: localSourceUri}} style={styles.image} />
      <FlashImage source={{uri: dataSourceUri}} style={styles.image} />
      <FlashImage
        source={{
          uri: getRandomImageUrl(),
          priority: RequestPriority.Low,
          cache: CacheControl.Default,
        }}
        style={styles.image}
      />
      <FlashImage
        source={{
          uri: getRandomImageUrl(),
          priority: RequestPriority.Normal,
          cache: CacheControl.IgnoreCache,
        }}
        style={styles.image}
      />
      <FlashImage
        source={{
          uri: getRandomImageUrl(),
          priority: RequestPriority.High,
          cache: CacheControl.OnlyIfCached,
        }}
        style={styles.image}
      />
    </ScrollView>
  );
}

const getRandomImageUrl = (random = true) => {
  return (
    'https://loremflickr.com/1200/1200' +
    (random ? `?${Math.floor(Math.random() * 100)}` : '')
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#e2e8f0',
  },
  image: {
    width: 150,
    height: 150,
  },
});
