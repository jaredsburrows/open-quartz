OpenGlass
=========

Open Source Google Glass Development


Google Glass Source Code
===========

Pull the existing APKs from your Android device:

    sh pullApks.sh

###Doing things by hand:

Decompile Applications by dumping the compiled *.java files (creates FILE.jar):

    sh d2j-dex2jar.sh FILE.apk (output FILE-dex2jar.jar)

View Compiled Byte Code as Java:
    
    ./jd-gui FILE.jar (just use jd-gui on any of the  FILE-dex2jar.jar files in /xe11-jars)

Sometimes JD-GUI doesn't like certain file extension and .gitignore automatically ignores all (.jars), so just rename:

    for file in *.jar; do mv ${file} ${file%.jar}.zip; done

View Resources of APK file:

	./apktool d FILE.apk

License
========

Copyright (C) 2014 OpenQuartz

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 
  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
