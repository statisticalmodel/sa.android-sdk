package com.example.sa.androidedgeclient;

/****************************************************
 * This code implements the sa.edge app for Android *
 ***************************************************/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sa.engine.client.SaClient;
import com.sa.engine.client.consumers.TypedUiConsumer;

import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity // Main class of app
        implements SaClient.Logger {
    // Define widget variables defined in layout res/layout/MainActivity.xml:
    private TextView titleTextView;
    private EditText connectionEditText;
    private Button startStopButton;
    private Button saveButton;
    private TextView log;

    // Define sa.engine interface variables:
    private SaClient saClient;              // Interface to sa.engine
    private Future<Integer> edgeListenerID; // Handle to the edge listener

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Callback when main activity is created
        super.onCreate(savedInstanceState);              // Call OnCreate in superclass first
        setContentView(R.layout.activity_main);          // Load layout for main activity

        //Retrieve widgets and bind to corresponding widget variables
        connectionEditText = (EditText) findViewById(R.id.edgeNameEditText);
        startStopButton = (Button) findViewById(R.id.startStopButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        log = (TextView) findViewById(R.id.log);

        /********************************
         * Initialize sa.engine
         ********************************/
        saClient = new SaClient.Builder(getApplicationContext())
                // Create a sa.engine interface for this app
                .withPersistenceEnabled()
                .withConnectionListener(new SaClient.ConnectionListener()
                { // The ConnectionListener is called when sa.engine has been initialized
                    @Override
                    public void onConnected(SaClient client) {
                        // Register the handler of log messages from sa.engine:
                        saClient.registerLogger(MainActivity.this);

                        // Define behavior of start/stop button:
                        initButton();
                    }
                    // Stop the program if initialization failed:
                    @Override
                    public void onConnectionError(String message) {
                        finish();
                    }
                }).build();
    }

    private void initButton() {
         // Define behaviour of stop/start button clicks
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pick up the text in widget connectionEditText:
                final String edgeSpec = connectionEditText.getText().toString();
                if (edgeListenerID == null)
                { // When the edge listener is inactive (button shows "Activate"):

                    // Check that the edge specification is correct:
                    if(!SaClient.validateEdgeSpec(edgeSpec)) {
                        connectionEditText.setError("Enter edge info as edgeName@serverAddr");
                        return;
                    }
                    /*****************************************************************************
                     * Start the edge listener by calling OSQL function edge_listener(edgeSpec): *
                     ****************************************************************************/
                    edgeListenerID = saClient.postCall("edge_listener", SaClient.argl(edgeSpec),
                            new TypedUiConsumer<String>()
                            { // OSQL functions return streams of data and errors as call-backs
                                @Override
                                public void onData(String data) {
                                    addTextToLog(data);
                                }

                                @Override
                                public void error(String errorStr) {
                                    addTextToLog(errorStr);
                                }
                            });
                    // Change state of layout to active mode
                    connectionEditText.setVisibility(View.GONE);
                    saveButton.setVisibility(View.GONE);
                    titleTextView.setText(edgeSpec);
                    startStopButton.setText("STOP");
                } else {// Stop active edge listener:
                    saClient.stop(edgeListenerID); // Send stop signal to the edge listener
                    edgeListenerID = null;         // Indicate that the edge listener is inactive
                    // Change the state of the layout to inactive mode
                    titleTextView.setText("sa.edge analyzer");
                    startStopButton.setText("ACTIVATE");
                    connectionEditText.setVisibility(View.VISIBLE);
                    saveButton.setVisibility(View.VISIBLE);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saClient.save(); // Save image for sa.engine.
                // Show message on screen that image is save:
                Toast.makeText(MainActivity.this,"saved!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void log(final String line) {
        // sa.engine calls this method when log message is to be emitted
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addTextToLog(line);
            }
        }); // Post a log message on the UI thread
    }

    private void addTextToLog(String text) {
        log.append(text + "\n");
    } // Convenience method
}
