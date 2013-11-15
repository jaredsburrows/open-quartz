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
