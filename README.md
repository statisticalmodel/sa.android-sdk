Getting started with sa.android
===============================

First you should get aquantited with the [sa.engine system](https://docs.streamanalyze.com) and download to your desktop the [Visual Analyzer](https://docs.streamanalyze.com/visual_analyzer.html) and its tutorial to learn how to easily analyze data streams. At that point you will know how to filter and transform data streams using sa.engine's query language OSQL.

This git repository contains examples of stand-alone Android applications using the [sa.android SDK](https://docs.streamanalyze.com/sa-android-sdk.html) to call sa.engine from Java on Android devices. It is completely self contained so even if you are new to Android it should be easy for you to get started.

If you are a beginner to Android development we recommend starting with the [**Hello Streaming World**](https://github.com/streamanalyze/sa.android-sdk/wiki/Hello-Streaming-World---beginners-guide) in folder `samples/HelloStreamingWorld`. It is a tutorial showing how to develop a simplest possible streaming sa.android app and includes a small guide of how to set up an [Android Studio](https://developer.android.com/studio) project. You need no previous knowledge about Android Studio. 

The [**Band Pass Filter**](https://github.com/streamanalyze/sa.android-sdk/wiki/Band-Pass-Filter) stored in folder `samples/BandPassFilter` shows how to develop an app that filters away frequencies outside a frequency interval in a signal stream from the microphone on an Android device. It includes a simple OSQL model for a such a band pass filter in OSQL and Java code to use the model in an Android app.

SA plug-in to Android Studio
----------------------------

To simplify the development of data stream models used in Java apps there is a plugin for Android Studio called the **SA Plugin**. It helps you create new models according to the file structure required by Android Studio. The data stream models are developed and immediately tested on your PC using the Visual Analyzer. When the app is deployed from Android studio the developed models are automatically uploaded to the device. 

__Installing the SA plugin__

1. Download and install the Visual Analyzer following the instructions in [Visual Analyzer](https://docs.streamanalyze.com/visual_analyzer.html). The default install location is `C:\Program Files\streamanalyze\sa.engine`. 

2. Open Android Studio and select **File \> Settings... \> Plugins \> Install plugin from disk...** and choose the file `intellij-plugin\sa.android-plugin.jar` in the Visual Analyzer home folder (default `C:\Program Files\streamanalyze\sa.engine`).

3. Restart Android Studio

To get a menu of the main functionalities of the SA plugin, click **Tools \> sa.engine**. With the plugin you can start the Visual Analyzer for your project, edit existing OSQL models, and create new ones. The plugin assures that new model files are created in the folder required by sa.android. 

__The master file__

The file named `master.osql` defines the OSQL model used in the app. When an sa.android app is deployed the default behavior is that a new sa.engine database containing the model is created on the device. 

As an example, in the sample project `BandPassFilter` you can edit the master file by clicking **Tools \> sa.engine \> Open** and selecting the file `master.osql`. 

__Using the Visual Analyzer__

The Visual Analyzer running on the PC is started by clicking **Tools \> sa.engine \> Start Visual Analyzer**. During development of OSQL models it is often practical to cut-and-paste statements from model files into the Visual Analyzer for immediate evaluation and visualization.

You can load entire models into the Visual Analyzer by entering the command ´< 'master.osql'´. Then you can immediately state queries using the model. Usually complex models are modularized into several OSQL files loaded from the master model by `< 'filename';` statements.
 
