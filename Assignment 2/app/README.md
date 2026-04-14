# Overview

## This Android application estimates the phone’s orientation in real time using accelerometer and gyroscope sensor data. It displays both raw sensor values and a simple orientation interpretation.

### Features
-Reads live accelerometer data (x, y, z)
-Reads live gyroscope data (x, y, z)
-Displays sensor values in real time
-Estimates orientation using pitch and roll
-Outputs orientation as text (e.g., Face Up, Tilted Left, Tilted Right)

### Implementation
-Built using Java in Android Studio
-Uses SensorManager and SensorEventListener for sensor access
-Registers sensors in onResume() and unregisters in onPause() to ensure proper lifecycle handling
-Orientation is calculated using accelerometer-based pitch and roll formulas

### Orientation Logic
-Pitch and roll are derived from accelerometer values
-Thresholds are used to classify orientation:
-Face Up / Face Down
-Tilted Left / Tilted Right
-Tilted Up / Tilted Down
