find . -type f | while read file; do d2j-dex2jar.sh "$file"; done
