OpenGlass
=========

Open Source Google Glass Development

### Table of Contents  
 - [Example Applications](#example-apps)  
 - [Google Glass Application Sources](#glass-source)  
 - [Third Party Applications](#third-party)  

<a name="example-apps"/>
### Example Applications for Google Glass([/example-apps](../master/example-apps))
 - [HelloGlass](../master/example-apps/HelloGlass)
  - Basic "HelloWorld"

<a name="glass-source"/>
### Google Glass Application Source Code([/glass-source](../master/glass-source)):

#### UPDATE(11/19/13): GDK is now out! You can still easily decompile applications using this method.

~~Since the GDK is not yet released, we can look around how the current Google Glass Android applications were compile by breaking them down. The decompiled Google Glass applications are included.~~ Here are a list of tools to decompile the native APKs:
 - Decompiling APKs 
   - Dex2Jar
     - https://code.google.com/p/dex2jar
 - Decompiling compiled Java (.class) files
   - Jad 
     - http://www.varaneckas.com/jad
 - View decopmiled JAR files from Dex2Jar
   - JD-GUI
     - http://jd.benow.ca/
 - Dumping APK Resources
   - Android APKtool
     - https://code.google.com/p/android-apktool
 - All in One Tool
   - APK Inspector
     - https://code.google.com/p/apkinspector/ (old link)
     - https://github.com/honeynet/apkinspector/
   - http://www.decompileandroid.com/

Read more: 
 - http://stackoverflow.com/questions/3122635/is-it-possible-to-decompile-an-android-apk-file
 - http://blog.burrowsapps.com/2012/02/hacking-facebook-for-android.html
 - http://blog.burrowsapps.com/2012/05/how-to-reverse-engineer-android-malware.html

<a name="third-party"/>
### Third Party Applications([/third-party](../master/third-party)):
Here are helpful applications to install on your Glass in order to start testing and developing.
- Android Applications
 - API Demos
 - Barcode Scanner
 - Capture Activity
 - Dev Tools
 - Launcher
   - Regular ICS Launcher
 - Launchy (update to XE11 first) 
   - https://github.com/kaze0/launchy
 - OpenCV for Android
   - https://play.google.com/store/apps/details?id=org.opencv.engine
 - Settings
 - Terminal Emulator
   - https://play.google.com/store/apps/details?id=jackpal.androidterm 
- Helpful Tools
 - Android Screen Monitor
   - https://code.google.com/p/android-screen-monitor/

### Basic ADB Usage(From Terminal or CMD Prompt):
Since there is no "Google Play" Android Market for the Glass yet, we have to side load Android applications for now. 
 - Installing Applications(.apks):
   - adb install -r FILE.apk
 - Running the Application:
   - adb shell am start -n PACKAGE.NAME/.MAIN.ACTIVITY.NAME
 - List all Packages on your Android Device:
   - adb shell pm list packages -f 
 - List all Relative Information about your Android Device:
   - adb shell dumpsys
     - adb shell dumpsys battery
     - adb shell dumpsys wifi
     - adb shell dumpsys cpuinfo
     - adb shell dumpsys meminfo
       - adb shell dumpsys meminfo PACKAGE.NAME
   - adb shell cat "/system/build.prop" | grep "product"
 - Show the AndroidManifest
   - aapt dump xmltree FILE.apk AndroidManifest.xml

Read more: 
 - http://developer.android.com/tools/help/adb.html
 - http://stackoverflow.com/questions/11201659/whats-android-adb-shell-dumpsys-tool-and-its-benefits

### Current Open Source Projects:
 - OpenShades: WearScript
   - https://github.com/OpenShades/wearscript
 - Example of Decompiled Resources:
   - https://github.com/zhuowei/Xenologer-src-glasshome

### Pre-GDK Glass Applications:
 - Compass
   - https://www.github.com/googleglass/apk-compass-sample
 - Level
   - https://www.github.com/googleglass/apk-level-sample
 - Stopwatch
   - https://www.github.com/googleglass/apk-stopwatch-sample
 - Waveform
   - https://www.github.com/googleglass/apk-waveform-sample

### Important Libraries:
 - OpenCV(OpenCV for Android)
   - http://opencv.org/platforms/android.html

### Google Glass Resources:
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


License
========

Copyright (C) 2013 OpenGlass

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 
  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.