sa.android-sdk
==============

**sa.android** is a system for easy development of applications that process and analyze real-time streams of data in Android devices. The streams are usually produced by sensors on the mobile devices such as the GPS, microphone, accelerometer, or the gyroscope. It is a component in the **sa.engine** toolbox for analyzing streaming data from mobile devices on the internet.

Check out our [wiki](https://github.com/streamanalyze/sa.android-sdk/wiki) for detailed documentation and user guides.

If you are new to Android development the wiki contains a [beginners guide](https://github.com/streamanalyze/sa.android-sdk/wiki/Getting-started---beginners-guide_tr) that requires no previous knowledge of android development.


## Quick reference


First add `http://repository.streamanalyze.com` to the project file `build.gradle` of your repository:
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

Then add `com.sa.engine:android:x.y.z`, where x.y.z is the system version you want to use, to module file `build.gradle`.
```gradle
compile 'com.sa.engine:android:x.y.z'
```
Read the [Releases page](https://github.com/streamanalyze/sa.android-sdk/releases) to find the latest version.

This is a complete example of an Android app calling a continuous query:

```java
import com.sa.engine.client.SaClient;
import com.sa.engine.client.consumers.GenericUiConsumer;
public class ExampleActivity extends AppCompatActivity {
    Future<Integer> cqId;
    SaClient saClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saClient = new SaClient.Builder(getApplication()).build();
        cqId = saClient.postQuery("select 'Hello Streaming world ' + extract(heartbeat(1))",
                new GenericUiConsumer() {
                    @Override
                    public void onData(Object[] tuple) {
                        System.out.println(tuple[0]);
                    }
                });
        //Stop the query with saClient.stop(cqId);
    }
}
```

Under the **samples** folder you can find several other example apps. Test them out by cloning the sample repository:

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
git clone https://github.com/streamanalyze/sa.android-sdk.git
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
