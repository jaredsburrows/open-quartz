OpenGlass
=========

Open Source Google Glass Development

###Third Party Applications(/thirdparty folder):
 - Barcode Scanner
 - Capture Activity
 - Launcher
  - Regular ICS Launcher
 - Launchy (update to XE11 first) 
  - https://github.com/kaze0/launchy
 - OpenCV for Android
 - Terminal Emulator
  - https://play.google.com/store/apps/details?id=jackpal.androidterm

###Basic ADB Usage(From Terminal or CMD Prompt):
 - Installing Applications(.apks):
  - adb install -r FILE.apk
 - Running the Application:
  - adb shell am start -n PACKAGE.NAME/.MAIN.ACTIVITY.NAME
 - List all Packages on your Android Device:
  - adb shell pm list packages -f - List All Packages

###Current Open Source Projects:
 - OpenShades: WearScript
  - https://github.com/OpenShades/wearscript
 - Example of Decompiled Resources:
  - https://github.com/zhuowei/Xenologer-src-glasshome

###Pre-GDK Glass Applications:
 - Compass
  - https://www.github.com/googleglass/apk-compass-sample
 - Level
  - https://www.github.com/googleglass/apk-level-sample
 - Stopwatch
  - https://www.github.com/googleglass/apk-stopwatch-sample
 - Waveform
  - https://www.github.com/googleglass/apk-waveform-sample

###Important Libraries:
 - OpenCV(OpenCV for Android)
  - http://sourceforge.net/projects/opencvlibrary/files/opencv-android/2.4.6/OpenCV-2.4.6-android-sdk-r2.zip/download

###Google Glass Resources:
 - Overview
  - https://developers.google.com/glass/
 - Basic Setup
  - https://glass.google.com/u/0/setup
 - Technical Specifications
  - https://support.google.com/glass/answer/3064128
 - Developer Guidelines
  - https://developers.google.com/glass/guidelines
 - GDK
  - https://developers.google.com/glass/gdk
 - Github
  - https://github.com/googleglass
 - Boot Images and Kernels
  - https://developers.google.com/glass/downloads/system