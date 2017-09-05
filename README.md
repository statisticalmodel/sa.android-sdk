sa.android-sdk
==============

sa.android is a system for easy development of applications that process and
analyze real-time streams of data in mobile Android devices. The streams are
usually produced by sensors on the mobile devices such as the GPS, microphone,
accelerometer, or the gyroscope. It is a component in the **sa.engine** toolbox
for analyzing streaming data from mobile devices on the internet.

To analyze streaming data sa.android allows for non-programmers to develop
filters and algorithms using a **query language** called OSQL (Object Stream
Query Language). With OSQL filters and algorithms over real-time streaming data
can be specified without detailed programming knowledge. You specify **what** to
do, rather than writing detailed programs expressing **how** to do it and the
system internally generates and executes programs to do the tasks.

A query over real-time data streams that continuously filter and transform data
is called a **continuous query** (CQ). sa.engine allows analysts to
interactively specify CQs to analyze measurements from devices and other systems
in real-time. The result of a CQ is a real-time stream of processed data values.

To enable fast and flexible development of analyses over streaming data,
sa.engine provides a **REPL** (Read-Eval-Print-Loop) where OSQL queries and
programs can be interactively specified and tested under Windows (OSX coming
soon).

To develop complete mobile applications on Android for analyzing streams,
sa.android provides a powerful **Android SDK** where Java application can call
OSQL queries and process results from CQs. The SDK together with the REPL and
Android Studio is a powerful development environment for mobile real-time stream
analytics.

Rather than the more conventional approach of uploading all data from devices to
a server for central processing, the sa.engine approach enables local **edge
analytics** of data streams directly on the devices. This approach allows for
drastic data reduction by filtering data streams already in the devices so that
only combined analyses are made centrally. For example, when edge analyses in
some devices detect strong vibrations or sound within certain frequencies, data
containing the frequency spectrum of the signal and the geographical positions
of the devices is sent to a server. Thus data is transmitted to a server only
when interesting measurements are observed by edge analyses.

To enable distributed data analytics over large collections of mobile devices,
devices running sa.android can be registered with sa.engine **edge servers**.
The edge server approach allows for interactive processing of **fusion queries**
that combine streams from edge analyses on registered devices. For example,
continuous queries may indicate that several devices in an area measure strong
vibrations. When interesting edge analyses are made the analysts can
interactively send new queries to affected devices to find out their causes.

Efficient maintenance and support is provided by allowing analyses to be
dynamically distributed and remotely installed and updated on all devices
affected. For example, to tune installed edge analyses at run-time, thresholds
indicating strong vibrations may be updated on running devices of a particular
brand.

Check out our [wiki](https://github.com/streamanalyze/sa.android-sdk/wiki) for
detailed documentation and user guides.

clone sample repo:

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
git clone https://github.com/streamanalyze/sa.android-sdk.git
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

clone wiki repo:

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
git clone https://github.com/streamanalyze/sa.android-sdk.wiki.git
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
