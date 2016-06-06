OpenGlass
=========

Open Source Google Glass Development


Standalone Face Detection(post-XE10 hotfix)
===========
**Required:**
 - [Android SDK](http://developer.android.com/sdk/index.html)
 - [Android NDK](http://developer.android.com/tools/sdk/ndk/index.html)
 - [OpenCV for Android SDK](http://opencv.org/platforms/android.html)

**Build Standalone Library for OpenCV:**

This will bring "libopencv_java.so" from the OpenCV SDK for Android, this way you won't have to use the OpenCV Manager

After importing this project into Eclipse, run this command to build the shared library:


    ndk-build clean all && ndk-build


Read more:

http://docs.opencv.org/trunk/doc/tutorials/introduction/android_binary_package/dev_with_OCV_on_Android.html

License
========

Copyright (C) 2014 OpenQuartz

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 
  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
