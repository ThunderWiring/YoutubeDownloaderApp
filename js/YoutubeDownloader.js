/**
 * @flow
 */

 'use strict';

 const React = require('React');
 const Image = require('Image');
 const nativeImageSource = require('nativeImageSource');
 const MainInterstitial = require('./MainInterstitial');
 const Text = require('Text');

 import {
   StyleSheet,
   View,
 } from 'react-native';

 type Props = {};

 class YoutubeDownloader extends React.Component {

   constructor() {
     super();
   }

   render() {
     return (
       <View style={styles.container}>
         <MainInterstitial />
       </View>
     );
   }
 }

 const styles = StyleSheet.create({
   container: {
     // position: 'absolute',
     left: 0,
     top: 0,
     opacity: 0.9,
   },
 });

module.exports = YoutubeDownloader;
