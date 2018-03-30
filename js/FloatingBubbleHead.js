/**
 * @flow
 */

import React, { Component } from 'react'
import { presets, spring, StaggeredMotion } from 'react-motion'
import _ from 'lodash'
import { Dimensions, PanResponder, View } from 'react-native'
import { StyleSheet } from 'react-native';

const {height: screenHeight, width: screenWidth} = Dimensions.get('window')
const colors = ['#F44336', '#9C27B0', '#2196F3', '#009688', '#FF9800', '#607D8B']

class FloatingBubbleHead extends Component {
  state = {
    x: 100,
    y: 100
  }

  panResponder = PanResponder.create({
    onMoveShouldSetPanResponderCapture: () => true,
    onPanResponderMove: (event) => {
      this.setState({x: event.nativeEvent.pageX, y: event.nativeEvent.pageY})
    },
  })

  render () {
    return <View {...this.panResponder.panHandlers}
      style={{width: screenWidth, height: screenHeight}}>
      <StaggeredMotion defaultStyles={_.range(6).map(() => ({x: 0, y: 0}))}
        styles={(prevStyles) => prevStyles.map((a, i) => {
          return i === 0 ? this.state : {
            x: spring(prevStyles[i - 1].x, presets.gentle),
            y: spring(prevStyles[i - 1].y, presets.gentle),
          }
        })}>
        {styles =>
          <View>
            {styles.slice().reverse().map(({x, y}, i) => {
                const index = styles.length - i - 1
              return <View key={index}
                style={{
                  width: 70,
                  borderRadius: 35,
                  height: 70,
                  position: 'absolute',
                  left: x + 3 * index,
                  top: y + 3 * index,
                  backgroundColor: colors[index],
                }}/>
            }
            )}
          </View>
        }
      </StaggeredMotion>
    </View>
  }
}

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
    left: 0,
    top: 0,
    opacity: 0.5,
    backgroundColor: 'transparent',
  },
});

module.exports = FloatingBubbleHead;
