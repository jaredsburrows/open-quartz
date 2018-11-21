OpenQuartz
=========

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Status](https://travis-ci.org/jaredsburrows/open-quartz.svg?branch=master)](https://travis-ci.org/jaredsburrows/open-quartz)
[![Twitter Follow](https://img.shields.io/twitter/follow/jaredsburrows.svg?style=social)](https://twitter.com/jaredsburrows)

**Open Source Google Glass Development**

#### Google Glass Example GDK Applications:
 - [Compass](https://www.github.com/googleglass/apk-compass-sample)
 - [Level](https://www.github.com/googleglass/apk-level-sample)
 - [Stopwatch](https://www.github.com/googleglass/apk-stopwatch-sample)
 - [Waveform](https://www.github.com/googleglass/apk-waveform-sample)

### Important Libraries:
- Google
  - [Android Glass GDK](https://developers.google.com/glass/develop/gdk/)
  - [Android SDK](http://developer.android.com/sdk/index.html)
  - [Android NDK](http://developer.android.com/tools/sdk/ndk/index.html)
- Misc
  - [OpenCV(OpenCV for Android)](http://opencv.org/platforms/android.html)


### Basic ADB Usage(For Terminal or CMD Prompt):
Information for side loading Android applications.

 - Keep Your Google Glass On while charging/developing:
   - `adb shell svc power stayon true | false | usb | ac`
 - Turn off Wifi and only use Bluetooth
   - `adb shell svc wifi enable | disable`
 - Installing/Uninstall Applications(.apks):
   - `adb install -r FILE.apk`
   - `adb uninstall FILE.apk`
 - Running the Application:
   - `adb shell am start -n PACKAGE.NAME/.MAIN.ACTIVITY.NAME`
 - List all Packages on your Android Device:
   - `adb shell pm list packages -f` 
 - List all Relative Information about your Android Device:
   - `adb shell dumpsys`
     - `adb shell dumpsys battery`
     - `adb shell dumpsys wifi`
     - `adb shell dumpsys cpuinfo`
     - `adb shell dumpsys meminfo`
       - `adb shell dumpsys meminfo PACKAGE.NAME`
   - `adb shell cat "/system/build.prop" | grep "product"`
 - Screenshots from Commandline
   - `adb shell /system/bin/screencap -p /sdcard/screenshot.png`
   - `adb pull /sdcard/screenshot.png screenshot.png`

Read more: 
 - http://developer.android.com/tools/help/adb.html
 - http://stackoverflow.com/questions/11201659/whats-android-adb-shell-dumpsys-tool-and-its-benefits

### Example Applications for Google Glass:
 - GDK
   - [Camera App](gdk/camera-app)
     - Basic Camera application with Camera preview - with "Hotfix" - post-XE11
   - [Memo App](gdk/glass-memo) - *Andre Compagno* [(@acompagno)](https://github.com/acompagno)
     - Voice Memo Application
   - [Hello Glass](gdk/hello-glass)
     - Basic "HelloWorld"
   - [Location Example](gdk/location)
     - Basic Location on Live Cards
   - [Voice Example](gdk/voice-example)
     - Voice Recognition Example
 - OpenCV or Android SDK
   - [Glass Preview](sdk/camera-preview)
     - "Hotfix" for Google Glass camera preview - post-XE11
   - [Face Detection](sdk/ocv-face-detection)
     - "Hotfix" for Google Glass camera preview - post-XE11
     - Optimization coming soon
   - [Image Manipulation](sdk/ocv-image-manipulation)
     - Mixed Processing and Camera Control Tutorials
     - Canny, Sobel, RGBA, Gray, Feature Detection, etc
     
### Third Party Applications([/third-party](third-party)):
Here are helpful applications to install on your Glass in order to start testing and developing.
- Android Applications
  - API Demos
  - Barcode Scanner
  - Capture Activity
  - Dev Tools
  - Launcher
    - Regular ICS Launcher
  - [Launchy (update to XE11 first)](https://github.com/kaze0/launchy)
  - [OpenCV for Android](https://play.google.com/store/apps/details?id=org.opencv.engine)
  - Settings(Settings.apk)
  - [Settings for Glass(Setttings_Full.apk)](http://forum.xda-developers.com/showthread.php?t=2576224)
  - [Terminal Emulator](https://play.google.com/store/apps/details?id=jackpal.androidterm)
- Helpful Tools
  - [Android Screen Monitor](https://code.google.com/p/android-screen-monitor/)

### Google Glass Resources:
- [Overview](https://developers.google.com/glass/)
- [Basic Setup](https://glass.google.com/u/0/setup)
- [Technical Specifications](https://support.google.com/glass/answer/3064128)
- [Developer Guidelines](https://developers.google.com/glass/guidelines)
- [GDK](https://developers.google.com/glass/develop/gdk/)
- [Github](https://github.com/googleglass)
- [Boot Images and Kernels](https://developers.google.com/glass/downloads/system)
- [Glass-Apps: Developers, Blogs and News](http://glass-apps.org/)

## License

    Copyright (C) 2013 Jared Burrows and Andre Compagno

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
