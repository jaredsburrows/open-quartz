LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

# Path to OpenCV Android SDK
include ~/Downloads/OpenCV-2.4.8-android-sdk/sdk/native/jni/OpenCV.mk

# For custom OpenCV modules
LOCAL_MODULE    := mixed_sample
LOCAL_SRC_FILES := jni_part.cpp
LOCAL_LDLIBS +=  -llog -ldl

include $(BUILD_SHARED_LIBRARY)
