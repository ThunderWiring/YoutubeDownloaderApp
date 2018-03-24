/**
 * @flow
 */

 const React = require('React');
 const FloatingBubbleHead = require('./FloatingBubbleHead');

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
         <FloatingBubbleHead />
       </View>
     );
   }
 }

 const styles = StyleSheet.create({
   container: {
     position: 'absolute',
     left: 0,
     top: 0,
     opacity: 0.9,
     backgroundColor: 'transparent',
   },
 });

module.exports = YoutubeDownloader;
