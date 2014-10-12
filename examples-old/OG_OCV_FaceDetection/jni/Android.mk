LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

# Don't import Camera modules - native camera
OPENCV_CAMERA_MODULES:=off

# Automatically install libs in libs/
OPENCV_INSTALL_MODULES:=on

# Library build type - brings in libopencv_java.so
OPENCV_LIB_TYPE:=SHARED

# Path to Android SDK
include ../../Downloads/OpenCV-2.4.6-android-sdk/sdk/native/jni/OpenCV.mk

# For custom OpenCV modules
#LOCAL_SRC_FILES  := DetectionBasedTracker_jni.cpp
#LOCAL_C_INCLUDES += $(LOCAL_PATH)
#LOCAL_LDLIBS     += -llog -ldl

#LOCAL_MODULE     := detection_based_tracker

#include $(BUILD_SHARED_LIBRARY)