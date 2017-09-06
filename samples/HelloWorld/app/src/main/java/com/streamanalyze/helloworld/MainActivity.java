package com.streamanalyze.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sa.engine.client.SaClient;
import com.sa.engine.client.consumers.GenericUiConsumer;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView cqOutput = (TextView) findViewById(R.id.cqOutput);
        Button stopCqButton = (Button) findViewById(R.id.stopCqButton);

        final SaClient saClient = new SaClient.Builder(getApplication()).build();
        final Future<Integer> cqId = saClient.postQuery("select 'Hello Streaming world ' + extract(heartbeat(1))",
                new GenericUiConsumer() {
                    @Override
                    public void onData(final Object[] objects) {
                        cqOutput.setText(objects[0].toString());
                    }
                });

        stopCqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saClient.stop(cqId);
            }
        });
    }
}
