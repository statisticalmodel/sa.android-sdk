package com.example.sa.bandpassfilter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sa.engine.client.SaClient;
import com.sa.engine.client.consumers.GenericDataConsumer;

import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {
    //Used when requesting permission in from android
    private final int AUDIO_PERMISSION_REQUEST_CODE = 1000;
    private PlotCanvas plotCanvas;
    private EditText minHzEditText;
    private EditText maxHzEditText;
    private Button startStop;
    /***********************************************************************************************
     * Application declarations to reference sa.android kernel and instance variable queryID
     * to keep a handle to our CQ
     **********************************************************************************************/
    private SaClient saClient;
    private Future<Integer> queryId;
    /**********************************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind views in layout activity_main.xml
        this.plotCanvas = (PlotCanvas) findViewById(R.id.plotCanvas);
        this.minHzEditText = (EditText) findViewById(R.id.minHz);
        this.maxHzEditText = (EditText) findViewById(R.id.maxHz);
        this.startStop = (Button) findViewById(R.id.startStopButton);

        /*******************************************************************************************
         * Initialize sa.android with reInstallInDebug flag set. This allows us to change
         * The files in assets/SaEngine/models and see the results with every run.
         * SaClient.Builder.build must be called to obtain a reference to sa.android.
         ******************************************************************************************/
        this.saClient = new SaClient.Builder(getApplicationContext()).reInstallInDebug().build();

         // Android above API 22 requires us to ask the user
         // for permission when recording audio. Check if we have
         // it and ask if we don't.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            // Ask the user for permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    AUDIO_PERMISSION_REQUEST_CODE);
        } else {
            // We already have permission, continue to init method.
            init();
        }
    }

    /**********************************************************
     *  When the user has either granted or denied us access
     *  to the microphone, this method is called.
     **********************************************************/
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case AUDIO_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // We got the permission continue to the init method.
                    init();
                } else {
                    // The user denied us access to the microphone. Act accordingly. In this demo we
                    // simply exit the application.
                    finish();
                }
            }
        }
    }

    private void init() {
        // Set up toggle of start and stop button in main_activity.xml
        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (queryId == null) {
                    startStop.setText("Stop");
                    // Since the edit text in xml has number format we can assume that Integer.parseInt
                    // will not throw an exception.
                    int minHz = Integer.parseInt(minHzEditText.getText().toString());
                    int maxHz = Integer.parseInt(maxHzEditText.getText().toString());
                    /*******************************************************************************
                     * Start our CQ and save a handle to it in queryId so that we can cancel it
                     ******************************************************************************/
                    queryId = startQuery(minHz, maxHz);
                } else {
                    /*******************************************************************************
                     * Stop our CQ
                     ******************************************************************************/
                    saClient.stop(queryId);
                    startStop.setText("Start");
                    queryId = null;
                }
            }
        });
    }

    /***********************************************************************************************
     * This is the the main sa.android application code to call the audio_band_filter function
     * in our OSQL model defined in assets/SaEngine/models/master.osql.
     * For efficiency, GenericDataConsumer is used to run the callbacks on a thread managed by
     * sa.android. If GenericUiConsumer had been used it would slow down the UI thread with
     * for every updated datapoint. Our PlotCanvas implementation computes display data in the
     * background and the redrawings in the UI thread is always based on the lasted data.
     **********************************************************************************************/
    public Future<Integer> startQuery(int minHz, int maxHz) {
        return this.saClient.postCall("audio_band_filter", SaClient.argl(minHz, maxHz),
                new GenericDataConsumer() {
                    @Override
                    public void onData(Object[] data) {
                        plotCanvas.putPoints((Object[]) data[0]);
                    }
                });
    }
}
