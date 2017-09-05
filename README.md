sa.android-sdk
==============

sa.android is a system for easy development of applications that process and
analyze real-time streams of data in mobile Android devices. The streams are
usually produced by sensors on the mobile devices such as the GPS, microphone,
accelerometer, or the gyroscope. It is a component in the sa.engine toolbox for
analyzing streaming data from mobile devices on the internet.

To analyze streaming data sa.android allows for non-programmers to develop
filters and algorithms using a **query language** called OSQL (Object Stream
Query Language). With OSQL filters and algorithms over real-time streaming data
can be specified without detailed programming knowledge. You specify WHAT to do,
rather than detailed programs expressing HOW to do it and the system internally
generates and executes the programs to do the tasks.

A query over real-time data streams that continuously filter and transfrom data
is called a **continuous query** (CQ). sa.engine allows analysts to specify CQs
to anlyze measurements from devices in real-time.

To enable fast and flexible development of analyses over streaming data,
sa.engine provides a REPL (Read-Eval-Print-Loop) where OSQL queries and programs
can be immediately specified and tested under Windows (OSX coming soon).

Furthermore, sa.android provides a powerful Android SDK where Java application
can call OSQL queries. Using the SDK, the Android Studio development environment
can be used for developing complete mobile applications calling OSQL programs
developed through the REPL.

Data streams from many mobile devices can be combined and analyzed using OSQL
queries sent to an sa.engine **edge server** where mobile devices running
sa.android are registered.

The advantage of using sa.android over other ways of developing Android software
are:

-   Rather than the more conventional approach of uploading all data to a server
    for central processing, the sa.engine approach enables local **edge
    analytics** of data streams on the devices. This approach allows for drastic
    data reduction by filtering data streams already in the devices so that only
    combined analyses are made centrally. For example, when edge analyses in
    some devices detect strong vibrations or sound within certain frequencies,
    data containg the frequency spectrum of the signal and the geographical
    positions of the devices is sent to the server. Thus data is transmitted
    only when interesting measurements are observed by edge analyses.

-   The edge server approach allows for interactive processing of **fusion
    queries** to edge servers combining streams of edge analyses. For example,
    continuous queries may indicate that several devices in an area measure
    strong vibrations. When interesting edge analyses are made the ananlysts can
    interactively send new queries to affected devices to find out their causes.

-   Efficient maintenance and support is provided by allowing analyses to be
    dymaically distributed and remotely installed on all devices affected. For
    example, thresholds indicating strong vibrartions may be updated on devices
    of a particular brand.'

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
