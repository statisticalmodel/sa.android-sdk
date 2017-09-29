Getting started with sa.android
===============================

First you should get aquantited with the [sa.engine system](https://docs.streamanalyze.com) and download to your desktop the [Visual Analyzer](https://docs.streamanalyze.com/visual_analyzer.html) and its tutorial to learn how to easily analyze data streams. At that point you will know how to filter and transform data streams using sa.engine's query language OSQL.

This git repository contains examples of stand-alone Android applications using the [sa.android SDK](https://docs.streamanalyze.com/sa-android-sdk.html) to call sa.engine from Java on Android devices. It is completely self contained so even if you are new to Android it should be easy for you to get started.

If you are a beginner to Android development we recommend starting with the [**Hello Streaming World**](https://github.com/streamanalyze/sa.android-sdk/wiki/Hello-Streaming-World---beginners-guide) in `samples/HelloStreamingWorld`. It is tutorial showing how to develop a simplest possible streaming sa.android app and includes a small guide of how to set up an [Android Studio](https://developer.android.com/studio) project. You need no previous knowledge about Android Studio. 

The [**Band Pass Filter**](https://github.com/streamanalyze/sa.android-sdk/wiki/Band-Pass-Filter) stored in `BandPassFilter` shows how to develop an app that filters away frequencies outside a frequency interval in a signal stream from the microphone on an Android device. It includes a simple mathematical model for a such a band pass filter in OSQL and Java code to use the model in an Android app.

