sa.android-sdk
==============

sa.android is a system for easy development of applications that process and
analyze real-time streams of data in mobile Android devices. The streams are
usually produced by sensors on the mobile devices such as the GPS, microphone,
accelerometer, or the gyroscope. It is a component in the **sa.engine** toolbox
for analyzing streaming data from mobile devices on the internet.

If you are new to android development the wiki contains a [beginners guide](https://github.com/streamanalyze/sa.android-sdk/wiki/Getting-started---beginners-guide_tr) that requires no previous knowledge of android development.

If you're a more seasoned android developer add your maven repository to the project level `build.gradle`:
```gradle
allprojects {
    repositories {
        jcenter()
        maven {
            url "http://repository.streamanalyze.com/"
        }
    }
}
```

Then add `com.sa.engine:android:x.y.z` where x.y.z is the version you want to use to the dependencies of your module `build.gradle`
```gradle
compile 'com.sa.engine:android:x.y.z'
```
Check the tags or releases folder to see what versions are published
 
 

clone sample repo:

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
git clone https://github.com/streamanalyze/sa.android-sdk.git
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Check out our [wiki](https://github.com/streamanalyze/sa.android-sdk/wiki) for
detailed documentation and user guides.