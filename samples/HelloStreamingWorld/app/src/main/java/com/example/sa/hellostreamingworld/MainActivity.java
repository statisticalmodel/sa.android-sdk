package com.example.sa.hellostreamingworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sa.engine.client.SaClient;
import com.sa.engine.client.consumers.GenericUiConsumer;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    SaClient saClient;
    TextView cqOutput;
    Future<Integer> cqId;
    Button stopCqButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cqOutput = (TextView)findViewById(R.id.cqOutput);
        saClient = new SaClient.Builder(getApplication()).build();
        cqId = saClient.postQuery("select 'Hello Streaming world ' + extract(heartbeat(1))",
                new GenericUiConsumer() {
                    @Override
                    public void onData(Object[] tuple) {
                        cqOutput.setText(tuple[0].toString());
                    }
                });
        stopCqButton = (Button)findViewById(R.id.stopCqButton);
        stopCqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saClient.stop(cqId);
            }
        });
    }
}
