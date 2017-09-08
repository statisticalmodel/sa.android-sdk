sa.android-sdk
==============

sa.android is a system for easy development of applications that process and
analyze real-time streams of data in mobile Android devices. The streams are
usually produced by sensors on the mobile devices such as the GPS, microphone,
accelerometer, or the gyroscope. It is a component in the **sa.engine** toolbox
for analyzing streaming data from mobile devices on the internet.

If you are new to android development the wiki contains a [beginners guide](https://github.com/streamanalyze/sa.android-sdk/wiki/Getting-started---beginners-guide_tr) that requires no previous knowledge of android development.

Check out our [wiki](https://github.com/streamanalyze/sa.android-sdk/wiki) for
detailed documentation and user guides.

## Quick reference.


Add `http://repository.streamanalyze.com` to repositories in project `build.gradle`:
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
Check out the [Releases page](https://github.com/streamanalyze/sa.android-sdk/releases) to look up the latest version.

To execute your first continuous query: 

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

Under the samples folder you can find several sample applications. To test them out clone the sample repository:

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
git clone https://github.com/streamanalyze/sa.android-sdk.git
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
