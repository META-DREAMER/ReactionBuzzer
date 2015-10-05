# ReactionBuzzer
A simple, attractive, and easy-to-use reaction timer and gameshow buzzer app for Android.

Demo Video: https://youtu.be/a1P84I9oSq4

APK file included in `/app/bin` folder, simply install on your device and run the app!


Build Instructions:

1. Clone this repo into a new directory
2. In the root folder of the project, run "`chmod +x gradlew`" and then "`./gradlew assembleDebug`"
3. If you get a "SDK location not found" error, run `echo 'sdk.dir=<PATH TO ANDROID SDK>' >local.properties` where `<PATH TO ANDROID SDK>` is the location of your Android SDK (for example, `echo 'sdk.dir=/Users/hammadjutt/Library/Android/sdk' >local.properties`) and run "`./gradlew assembleDebug`" again
4. An APK file will be generated in the location `app/build/outputs/apk/app-debug.apk`

OR

Just open the project in Android Studio and run from there!


### Credits:

The following sources were used as reference for various parts of my code. Some code examples given were recreated and modified by me for use in my app. Majority of the code was written by me.

http://stackoverflow.com/questions/6173400/how-to-programmatically-hide-a-button-in-android-sdk
https://developer.android.com/tools/building/building-cmdline-ant.html
http://stackoverflow.com/questions/3320115/android-onclicklistener-identify-a-button
https://www.youtube.com/watch?v=5PPD0ncJU1g&feature=youtu.be (first 5 videos in series)
http://stackoverflow.com/questions/3775705/android-set-the-gravity-for-a-textview-programmatically
http://stackoverflow.com/questions/22412719/how-to-convert-data-into-2d-array-to-put-into-a-table
http://stackoverflow.com/questions/3327599/get-all-tablerows-in-a-tablelayout
http://stackoverflow.com/questions/25798958/iterate-through-2-dimensional-array
http://stackoverflow.com/questions/13954611/android-when-should-i-use-a-handler-and-when-should-i-use-a-thread
http://stackoverflow.com/questions/3733867/stop-watch-logic
http://stackoverflow.com/questions/11154898/android-programmatically-setting-button-text
http://stackoverflow.com/questions/2115758/how-to-display-alert-dialog-in-android
http://stackoverflow.com/questions/11955728/how-to-calculate-the-median-of-an-array
http://stackoverflow.com/questions/5271598/java-generate-random-number-between-two-given-values
http://stackoverflow.com/questions/1770010/how-do-i-measure-time-elapsed-in-java
https://sites.google.com/site/gson/gson-user-guide
https://github.com/joshua2ua/lonelyTwitter
Plus the android Documentation: https://developer.android.com/guide/index.html



