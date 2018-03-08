package com.example.sa.androidedgeclient;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sa.engine.client.SaClient;
import com.sa.engine.client.consumers.GenericUiConsumer;
import com.sa.engine.client.consumers.TypedUiConsumer;
import com.sa.engine.pump.Pump;
import com.sa.engine.pump.PumpFactory;
import com.sa.engine.pump.PumpReader;
import com.sa.engine.pump.impl.SensorPump;
import com.sa.engine.sensors.DeviceSensors;

import java.util.concurrent.Future;

import callin.Tuple;

public class MainActivity extends AppCompatActivity implements SaClient.Logger {

    private static final int AUDIO_PERMISSION_REQUEST_CODE = 1337;
    private EditText edgeNameEditText;
    private Button startStopButton;
    private TextView log;
    private SaClient saClient;
    private Future<Integer> qId;
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /********************************
         * Set up UI elements.
         ********************************/
        edgeNameEditText = (EditText) findViewById(R.id.edgeNameEditText);
        startStopButton = (Button) findViewById(R.id.startStopButton);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        log = (TextView) findViewById(R.id.log);

        /********************************
         * Init sa.engine
         ********************************/
        init();


    }

    private void init() {
        /********************************
         * To access the device sensors
         * we must tell sa.android how
         * to access them this is done
         * by registering a pump  called
         * "sensor".
         ********************************/
        PumpReader.registerPump("sensor", new PumpFactory() {
            @Override
            public Pump create(Tuple args, long... frameIds) {
                return new SensorPump(MainActivity.this, frameIds);
            }
        });

        saClient = new SaClient.Builder(getApplicationContext())
                .withConnectionListener(new SaClient.ConnectionListener() {
                    @Override
                    public void onConnected(SaClient client) {
                        /********************************
                         * Register this MainActivity to
                         * receive logging messages from
                         * sa.engine.
                         ********************************/
                        saClient.registerLogger(MainActivity.this);
                        /********************************
                         * Init start/stop button after
                         * sa.engine is initialized.
                         ********************************/
                        initButton();
                    }

                    @Override
                    public void onConnectionError(String message) {
                        finish();
                    }
                }).build();

    }

    private void initButton(){
        /********************************
         * Define behaviour of stop/start
         * button
         ********************************/
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qId == null && validateInput()) {
                    /**************************************************
                     * If there is no active query and the content of
                     * edgeNameEditText is valid, start edge listener.
                     **************************************************/
                    final String edgeName = edgeNameEditText.getText().toString();
                    qId = saClient.postQuery("edge_listener('" + edgeName + "')",
                            new TypedUiConsumer<String>() {
                                @Override
                                public void onData(String data) {
                                    addTextToLog(data);
                                }

                                @Override
                                public void error(String errorStr) {
                                    addTextToLog(errorStr);
                                }
                            });
                    edgeNameEditText.setVisibility(View.GONE);
                    titleTextView.setText(edgeName);
                    startStopButton.setText("STOP");

                } else {
                    // Stop edge mode.
                    saClient.stop(qId);
                    qId = null;
                    titleTextView.setText("sa.engine - edge client");
                    edgeNameEditText.setVisibility(View.VISIBLE);
                    startStopButton.setText("ACTIVATE");
                }

            }
        });
    }

    @Override
    public void log(final String line) {
        /********************************
         * Callback received from sa.engine
         * when a log message is received.
         * Add message to log.
         ********************************/
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addTextToLog(line);
            }
        });
    }

    private void addTextToLog(String text) {
        log.append(text + "\n");
    }

    private boolean validateInput() {
        /********************************
         * Check the the content of
         * edgeNameEditText is correctly
         * formatted
         ********************************/
        if (edgeNameEditText.getText().length() == 0
                || !edgeNameEditText.getText().toString().matches("[^@]+@[^@]+")) {
            edgeNameEditText.setError("Enter edge info as edgeName@serverAddr");
            return false;
        }
        return true;
    }
}
