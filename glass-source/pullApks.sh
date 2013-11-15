adb shell pm list packages -f | while read file; do adb pull `echo "$file" | grep -o "/.*apk"`; done
