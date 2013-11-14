find . | while read file; do adb install -r "$file"; done
