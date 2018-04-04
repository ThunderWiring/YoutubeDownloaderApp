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
         {/* <MainInterstitial /> */}
         <Image
           resizeMode={Image.resizeMode.contain}
           style={styles.image}
           source={nativeImageSource({
             android: 'ic_launcher',
             width: 100,
             height: 100
           })}
         />
       </View>
     );
   }
 }

 const styles = StyleSheet.create({
   container: {
     backgroundColor: 'green',
   },
   image: {
      justifyContent: "center",
      alignItems: "center",
    },
    instructions: {
      fontSize: 17,
      textAlign: "center",
      color: "#333333",
    },
 });

module.exports = YoutubeDownloader;
