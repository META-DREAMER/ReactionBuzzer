# ReactionBuzzer
A simple, attractive, and easy-to-use reaction timer and gameshow buzzer app for Android.

Demo Video: https://youtu.be/a1P84I9oSq4

APK file included in `/app/bin` folder, simply install on your device and run the app!


Build Instructions:

1. Clone this repo into a new directory
2. In the projects root folder, run "`chmod +x gradlew`" and then "`./gradlew assembleDebug`"
3. If you get a "SDK location not found" error, run `echo 'sdk.dir=<PATH TO ANDROID SDK>' >local.properties` where `<PATH TO ANDROID SDK>` is the location of your Android SDK (for example, `echo 'sdk.dir=/Users/hammadjutt/Library/Android/sdk' >local.properties`) and run "`./gradlew assembleDebug`" again
4. An APK file will be generated in the location `app/build/outputs/apk/app-debug.apk`

OR

Just open the project in Android Studio and run from there!
