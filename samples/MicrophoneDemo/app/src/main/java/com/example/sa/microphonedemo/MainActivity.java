package com.example.sa.microphonedemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sa.engine.client.SaClient;
import com.sa.engine.client.consumers.GenericUiConsumer;

import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {
    //Used when requesting permission in from android
    private final int AUDIO_PERMISSION_REQUEST_CODE = 1000;
    private SaClient saClient;
    private TextView cqOutput;
    private EditText minHzEditText;
    private EditText maxHzEditText;
    private Button startStop;
    private Future<Integer> queryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cqOutput = (TextView) findViewById(R.id.cqOutput);
        minHzEditText = (EditText) findViewById(R.id.minHz);
        maxHzEditText = (EditText) findViewById(R.id.maxHz);
        startStop = (Button) findViewById(R.id.startStopButton);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    AUDIO_PERMISSION_REQUEST_CODE);
        } else {
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case AUDIO_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
        this.saClient = new SaClient.Builder(getApplicationContext()).build();

        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (queryId == null) {
                    // Since the edit text in xml has number format we can assume that Integer.parseInt
                    // will not throw an exception.
                    int minHz = Integer.parseInt(minHzEditText.getText().toString());
                    int maxHz = Integer.parseInt(maxHzEditText.getText().toString());
                    queryId = startQuery(minHz, maxHz);
                    startStop.setText("Stop");
                } else {
                    saClient.stop(queryId);
                    startStop.setText("Start");
                    queryId = null;
                }
            }
        });
    }

    public Future<Integer> startQuery(int minHz, int maxHz) {

      return this.saClient.postQuery("select round(hz)\n" +
                        "      from Number hz, Number index, Number max, Number windowSize, Number sampleRate,\n" +
                        "           Vector of Number window\n" +
                        "     where window in extract(rfft(audio(windowSize,sampleRate)))\n" +
                        "       and windowSize = 256\n" +
                        "       and sampleRate = 16000\n" +
                        "       and max = vmax(window)\n" +
                        "       and max > 0.001\n" +
                        "       and window[index] = max\n" +
                        "       and hz = index * sampleRate/windowSize  * 0.5\n" +
                        "       and hz > " + minHz +
                        "       and hz < " + maxHz,
                new GenericUiConsumer() {
                    @Override
                    public void onData(Object[] data) {
                        super.onData(data);
                        cqOutput.setText(data[0].toString());
                    }
                });
    }
}
